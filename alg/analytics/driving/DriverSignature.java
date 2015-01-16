package alg.analytics.driving;

import alg.machlearn.genetic.Gene;

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

    private static Double [] defaultWeights = new Double[] {1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d, 1d};

    public DriverSignature () {
        super(null, defaultWeights); // TODO
    }

    public DriverSignature (Double [] traits, Double [] weights) {
        super(traits, weights);
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

    /**
     * System.out.println("Max speed: " + metersToKmh(maxSpeed) + "  Avg speed: " + metersToKmh(averageSpeed) + "  Max accel: " +
     metersPerSecSec(maxAccel) + "  Min accel (not stopped): " + metersPerSecSec(minAccel) +
     "  Avg accel: " + metersPerSecSec(averageAccel) + "  Max decel: " + metersPerSecSec(maxDecel) + "  Avg decel: " + metersPerSecSec(averageDecel) +
     "  Trip length: " + metersToKm(tripLength) + "  Minutes stopped: " + toMinutes(stopped) + "  Minutes total: " + toMinutes(trip.length));
     */
}
