package alg.machlearn.genetic;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Gene {

    protected Double []   traits;
    protected Double []   weights;
    protected Double fitness;

    public Gene (Double [] traits, Double [] weights) {
        this.traits = traits;
        this.weights = weights;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
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
