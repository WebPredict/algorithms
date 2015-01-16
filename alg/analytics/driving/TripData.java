package alg.analytics.driving;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/16/15
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class TripData {

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
