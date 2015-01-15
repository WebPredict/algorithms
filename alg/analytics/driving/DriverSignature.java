package alg.analytics.driving;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DriverSignature {

    private double [][][] tripsData;

    // Computed data from analysis:
    private double [][] speedData;
    private double [][] accelData;

    private double maxSpeed;
    private double maxAccel;
    private double minAccel;
    private double averageSpeed;
    private double averageAccel;

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
    public DriverSignature (double [][][] tripsData) {
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
         */
    }

    /**
     * Returns probability that tripData could represent a trip by this driver.
     * @param tripData
     * @return
     */
    public double isTripByThisDriver (double [][] tripData) {
        return (0); // TODO
    }
}
