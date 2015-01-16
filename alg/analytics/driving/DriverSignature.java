package alg.analytics.driving;

import alg.machlearn.genetic.Gene;
import alg.math.MathUtils;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DriverSignature extends Gene {

    // Computed data from analysis:
    private Double maxSpeed;
    private Double maxAccel;
    private Double minAccel;
    private Double averageSpeed;
    private Double averageAccel;
    private Double maxDecel;
    private Double averageDecel;
    private int tripLengthSeconds;
    private int stoppedSeconds;
    private int numDistinctStops;
    private int discontinuities;

    private static Double [] defaultWeights = new Double[] {1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d};

    public DriverSignature () {
        super(null, defaultWeights); // TODO
    }

    public DriverSignature (Double [] traits, Double [] weights) {
        super(traits, weights);
    }

//    public Double computeTotalWeightsAndValues () {
//        return (maxSpeed * weights [0] + maxAccel * weights [1] + minAccel * weights [2] + averageSpeed * weights [3] +
//                averageAccel * weights [4] + maxDecel * weights [5] + averageDecel * weights [6] + tripLengthSeconds * weights [7] +
//                stoppedSeconds * weights [8] + numDistinctStops * weights [9]);
//    }

    public Double computeTotalWeightsAndValues (TripData data) {
        return (data.getMaxSpeed() * weights [0] + data.getMaxAccel() * weights [1] + data.getMinAccel() * weights [2] + data.getAverageSpeed() * weights [3] +
                data.getAverageAccel() * weights [4] + data.getMaxDecel() * weights [5] + data.getAverageDecel() * weights [6] + data.getTripLengthSeconds() * weights [7] +
                data.getStoppedSeconds() * weights [8] + data.getNumDistinctStops() * weights [9] + data.getDiscontinuities() * weights [10]);
    }

    public static DriverSignature   randomSignature () {
        DriverSignature ret = new DriverSignature();

        Random random = new Random();
        for (int i = 0; i < ret.getWeights().length; i++) {
            ret.weights [i] = random.nextDouble();
        }
        return (ret);
    }

    public int getDiscontinuities() {
        return discontinuities;
    }

    public void setDiscontinuities(int discontinuities) {
        this.discontinuities = discontinuities;
    }

    public Double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Double getMaxAccel() {
        return maxAccel;
    }

    public void setMaxAccel(Double maxAccel) {
        this.maxAccel = maxAccel;
    }

    public Double getMinAccel() {
        return minAccel;
    }

    public void setMinAccel(Double minAccel) {
        this.minAccel = minAccel;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Double getAverageAccel() {
        return averageAccel;
    }

    public void setAverageAccel(Double averageAccel) {
        this.averageAccel = averageAccel;
    }

    public Double getMaxDecel() {
        return maxDecel;
    }

    public void setMaxDecel(Double maxDecel) {
        this.maxDecel = maxDecel;
    }

    public Double getAverageDecel() {
        return averageDecel;
    }

    public void setAverageDecel(Double averageDecel) {
        this.averageDecel = averageDecel;
    }

    public int getTripLengthSeconds() {
        return tripLengthSeconds;
    }

    public void setTripLengthSeconds(int tripLengthSeconds) {
        this.tripLengthSeconds = tripLengthSeconds;
    }

    public int getStoppedSeconds() {
        return stoppedSeconds;
    }

    public void setStoppedSeconds(int stoppedSeconds) {
        this.stoppedSeconds = stoppedSeconds;
    }

    public int getNumDistinctStops() {
        return numDistinctStops;
    }

    public void setNumDistinctStops(int numDistinctStops) {
        this.numDistinctStops = numDistinctStops;
    }
}