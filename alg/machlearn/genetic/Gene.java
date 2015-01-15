package alg.machlearn.genetic;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gene {

    private double []   traits;
    private double []   weights;

    public Gene (double [] traits, double [] weights) {
        this.traits = traits;
        this.weights = weights;
    }

    public double[] getTraits() {
        return traits;
    }

    public void setTraits(double[] traits) {
        this.traits = traits;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
