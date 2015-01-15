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
//    private double [] speedData;
//    private double [] accelData;
//
//    private double maxSpeed;
//    private double maxAccel;
//    private double minAccel;
//    private double averageSpeed;
//    private double averageAccel;

    public DriverSignature (double [] traits, double [] weights) {
        super(traits, weights);
    }
}
