package alg.analytics.driving;

import alg.graphics.util.BasicDataDisplayer;
import alg.io.FileUtils;
import alg.machlearn.genetic.FitnessEvaluator;
import alg.machlearn.genetic.Gene;
import alg.math.MathUtils;

import javax.swing.*;
import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DriverSignatureComputer implements FitnessEvaluator {

    private Double [][][] tripsData;

    // Computed data from analysis:
    private double [][] speedData;
    private double [][] accelData;

    private double []maxSpeed;
    private double []maxAccel;
    private double []minAccel;
    private double []averageSpeed;
    private double []averageAccel;

    private DriverSignature bestSignature;
    private Double threshholdOfSimilarity;
    private Double expectedSignatureTotal;

    /**
    * Goal: from list of X,Y position data for N trips, build up a 'driver signature' such that the question:
    * Is trip X from this driver or not? can be answered correctly with high probability.
    */

    /**
     *
     * @param tripsData  snapshot X,Y data (in meters from origin) for N trips, taken 1 second apart between snapshots.
     *                   Greater than 0 but Less than N/2 will be from some other driver! This should appear as distinct from the rest of the routes
     *                   during analysis.
     */
    public DriverSignatureComputer(Double[][][] tripsData) {
        this.tripsData = tripsData;
    }

    public void doAnalysis() {
        /**
         * Info we will need:
         * speed
         * acceleration
         * stops
         * duration of stops?
         * max speed
         * max accel, min accel
         * averages
         *
         * some measure of how constant their speed is once they are up to the max road speed
         * some measure of how long it takes them to arrive at an apparent roughly constant speed
         *
         * create a weighted signature of factors, then see how well it divides things into two groups
         * try a GA approach evolving weights for successive generations.
         *
         * ANOTHER issue: how to deal with discontinuities? Take note of them, as they could represent going
         * through tunnels or something. Afterwards, may want to do some smoothing?
         *
         * Will probably want some runs with variable amount of smoothing, to see how those results are. Perhaps amount of smoothing is a
         * gene factor as well to evolve
         */

        int numGenerations = 10;
        int numGenes = 10;
        double mutationRate = .01d;

        DriverSignature [] signatures = new DriverSignature[numGenes];
        for (int i = 0; i < signatures.length; i++) {
            signatures [i] = DriverSignature.randomSignature();
        }

        TripData [] tripsDataAnalyzed = new TripData[tripsData.length];
        for (int i = 0; i < tripsData.length; i++) {
            tripsDataAnalyzed [i] = analyzeTrip(tripsData [i]);
        }

        Double [] totals = new Double[tripsData.length];
        for (int i = 0; i < signatures.length; i++) {
            for (int j = 0; j < tripsData.length; j++) {
                Double total = signatures [i].computeTotalWeightsAndValues(tripsDataAnalyzed [j]);
                totals [i] = total;
            }

            // TODO: sort totals, then try to find two distinct groups, one of which has more similar trips
            // score the gene based on how well this has happened
            // breed genes, then adjust with mutations, then do next generation
        }
    }


    public static Double MAX_POSSIBLE_SPEED = 200000d / 3600d; // 200km/h
    public static Double MAX_POSSIBLE_ACCEL = 9.8 * 5d; // 5gs seems reasonable!

    public TripData analyzeTrip (Double [][] trip) {
        if (trip.length == 0)
            return (new TripData()); // TODO what are we doing with this case?

        // TODO:
        double [] speedData = new double [trip.length - 1];
        double [] accelData = new double [trip.length == 1 ? 0 : trip.length - 2];

        Double maxSpeed = null;
        Double maxAccel = null;
        Double minAccel = null;
        Double averageSpeed = null;
        Double averageAccel = null;
        Double averageDecel = null;
        Double maxDecel = null;
        Double minDecel = null;
        Double tripLength = null;

        Double lastX = null;
        Double lastY = null;
        Double lastSpeed = null;
        Double accel = null;
        Double decel = null;
        Double totalAccel = null;
        Double totalDecel = null;

        int stopped = 0;
        int distinctStops = 0;
        int discontinuities = 0;
        Double previousDistance = null;

        for (int i = 0; i < trip.length; i++) {
            double x = trip [i][0];
            double y = trip [i][1];
            Double speed = null;
            if (lastX != null) {
                double distance = MathUtils.distance(x, y, lastX, lastY);
                speed = distance; // m/s

                if (speed > MAX_POSSIBLE_SPEED) {
                    System.out.println("truncating excessive speed: " + metersToKmh(speed) + " for point " + i);
                    speed = lastSpeed;
                    discontinuities++;
                }

                if (distance == 0) {
                    stopped++;
                    if (previousDistance != null && previousDistance > 0)
                        distinctStops++;
                }

                if (lastSpeed != null) {
                    Double curAccel = speed - lastSpeed;
                    if (curAccel >= 0) {
                        if (curAccel < MAX_POSSIBLE_ACCEL)
                            accel = curAccel;
                    }
                    else {
                        if (-curAccel < MAX_POSSIBLE_ACCEL)
                            decel = -curAccel;
                    }

                    if (curAccel > MAX_POSSIBLE_ACCEL) {
                        System.out.println("truncating excessive accel: " + curAccel + " for point " + i);
                        //accel = MAX_POSSIBLE_ACCEL;
                    }
                    else if (-curAccel > MAX_POSSIBLE_ACCEL) {
                        System.out.println("truncating excessive decel: " + (curAccel) + " for point " + i);
                        //decel = MAX_POSSIBLE_ACCEL;
                    }

                    if (curAccel >= 0) {
                        if (maxAccel == null)
                            maxAccel = accel;
                        else if (maxAccel < accel)
                            maxAccel = accel;

                        if (minAccel == null && accel > 0)
                            minAccel = accel;
                        else if (minAccel != null && minAccel > accel && accel > 0)
                            minAccel = accel;

                        if (totalAccel == null)
                            totalAccel = accel;
                        else
                            totalAccel += accel;
                    }
                    else {
                        if (maxDecel == null)
                            maxDecel = decel;
                        else if (maxDecel < decel)
                            maxDecel = decel;

                        if (minDecel == null && decel > 0)
                            minDecel = decel;
                        else if (minDecel != null && minDecel > decel && decel > 0)
                            minDecel = decel;

                        if (totalDecel == null)
                            totalDecel = decel;
                        else
                            totalDecel += decel;
                    }

                    if (maxSpeed == null)
                        maxSpeed = speed;
                    else if (maxSpeed < speed)
                        maxSpeed = speed;

                    if (tripLength == null)
                        tripLength = distance;
                    else
                        tripLength += distance;

                }

                previousDistance = distance;
                lastSpeed = speed;
            }

            lastX = x;
            lastY = y;
            lastSpeed = speed;
        }

        averageSpeed = tripLength / trip.length;
        averageAccel = totalAccel / trip.length;
        averageDecel = totalDecel / trip.length;

        System.out.println("Max speed: " + metersToKmh(maxSpeed) + "  Avg speed: " + metersToKmh(averageSpeed) + "  Max accel: " +
                metersPerSecSec(maxAccel) + "  Min accel (not stopped): " + metersPerSecSec(minAccel) +
                "  Avg accel: " + metersPerSecSec(averageAccel) + "  Max decel: " + metersPerSecSec(maxDecel) + "  Avg decel: " + metersPerSecSec(averageDecel) +
                "  Trip length: " + metersToKm(tripLength) + "  Num discontinuities: " + discontinuities + "  Num distinct stops: " + distinctStops + "  Minutes stopped: " + toMinutes(stopped) + "  Minutes total: " + toMinutes(trip.length));

        TripData ret = new TripData();
        ret.setAverageAccel(averageAccel);
        ret.setAverageDecel(averageDecel);
        ret.setMaxAccel(maxAccel);
        ret.setMaxDecel(maxDecel);
        ret.setMinAccel(minAccel);
        ret.setMaxSpeed(maxSpeed);
        ret.setAverageSpeed(averageSpeed);
        ret.setStoppedSeconds(stopped);
        ret.setNumDistinctStops(distinctStops);
        ret.setDiscontinuities(discontinuities);
        ret.setTripLengthSeconds(trip.length);

        return (ret);
    }

    public static DecimalFormat FORMATTER = new DecimalFormat("#.###");

    public static String    metersToKm (double meters) {
        return (FORMATTER.format(meters / 1000d) + " km");
    }

    public static String    metersToKmh (double metersPerSec) {
        return (FORMATTER.format(3600d * metersPerSec / 1000d) + " km/h");
    }

    public static String    metersPerSecSec (double meters) {
        return (FORMATTER.format(meters) + " m/s^2");
    }

    public static String    metersPerSec (double meters) {
        return (FORMATTER.format(meters) + " m/s");
    }

    public static String    toMinutes (int seconds) {
        return (FORMATTER.format((double)seconds / 60d) + " min");
    }

    /**
     * Returns probability that tripData could represent a trip by this driver.
     * @param rawTripData
     * @return
     */
    public boolean isTripByThisDriver (Double [][] rawTripData) {
        if (expectedSignatureTotal == null)
            throw new RuntimeException("Need to run analysis first");
        else {
            //Double expected = bestSignature.computeTotalWeightsAndValues(); // TODO cache this
            TripData tripData = analyzeTrip(rawTripData);
            Double thisTrip = bestSignature.computeTotalWeightsAndValues(tripData);

            return (Math.abs(thisTrip - expectedSignatureTotal) < threshholdOfSimilarity);
        }
    }

    @Override
    public double evaluate(Gene gene) {

        // TODO: evaluate this gene by determining how well the factors help divide the list of trips into
        // two distinct groups: "this driver" and "other drivers"
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main (String [] args) throws Exception {

        int numToLoad = 200;
        final Double [][][] allData = new Double[numToLoad][][];

        for (int i = 1; i <= numToLoad; i++) {
            allData [i - 1] =  FileUtils.readNumericCSV("C:/Users/jsanchez/Downloads/drivers/2/" + i + ".csv");
        }

        DriverSignatureComputer computer = new DriverSignatureComputer(allData);
        computer.doAnalysis();

        final double zoom = 1d;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BasicDataDisplayer(allData, 800, 800, zoom).setVisible(true);
            }
        });
    }
}
