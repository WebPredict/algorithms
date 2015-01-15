package alg.analytics.driving;

import alg.machlearn.genetic.FitnessEvaluator;
import alg.machlearn.genetic.Gene;
import alg.math.MathUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DriverSignatureComputer implements FitnessEvaluator {

    private double [][][] tripsData;

    // Computed data from analysis:
    private double [][] speedData;
    private double [][] accelData;

    private double []maxSpeed;
    private double []maxAccel;
    private double []minAccel;
    private double []averageSpeed;
    private double []averageAccel;

    private DriverSignature bestSignature;

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
    public DriverSignatureComputer(double[][][] tripsData) {
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
         */
    }


    private void analyzeTrip (double [][] trip) {

        // TODO:
        double [] speedData = new double [trip.length];
        double [] accelData = new double [trip.length];

        Double maxSpeed = null;
        Double maxAccel = null;
        Double minAccel = null;
        Double averageSpeed = null;
        Double averageAccel = null;

        Double lastX = null;
        Double lastY = null;
        Double lastSpeed = null;
        Double accel = null;
        for (int i = 0; i < trip.length; i++) {
            double x = trip [i][0];
            double y = trip [i][1];
            Double speed = null;
            if (lastX != null) {
                double distance = MathUtils.distance(x, y, lastX, lastY);
                speed = distance; // m/s

                if (lastSpeed != null) {
                    accel = speed - lastSpeed;
                    if (maxAccel == null)
                        maxAccel = accel;
                    else if (maxAccel < accel)
                        maxAccel = accel;

                    if (minAccel == null)
                        minAccel = accel;
                    else if (minAccel > accel)
                        minAccel = accel;
                }

                lastSpeed = speed;
            }

            lastX = x;
            lastY = y;
            lastSpeed = speed;
        }
    }

    /**
     * Returns probability that tripData could represent a trip by this driver.
     * @param tripData
     * @return
     */
    public double isTripByThisDriver (double [][] tripData) {
        if (bestSignature == null)
            throw new RuntimeException("Need to run analysis first");
        else {
            // TODO
        }
        return (0); // TODO
    }

    @Override
    public double evaluate(Gene gene) {

        // TODO: evaluate this gene by determining how well the factors help divide the list of trips into
        // two distinct groups: "this driver" and "other drivers"
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
