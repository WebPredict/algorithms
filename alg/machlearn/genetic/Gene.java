package alg.machlearn.genetic;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gene {

    private Double []   traits;
    private Double []   weights;

    public Gene (Double [] traits, Double [] weights) {
        this.traits = traits;
        this.weights = weights;
    }

    public Double[] getTraits() {
        return traits;
    }

    public void setTraits(Double[] traits) {
        this.traits = traits;
    }

    public Double[] getWeights() {
        return weights;
    }

    public void setWeights(Double[] weights) {
        this.weights = weights;
    }
}
