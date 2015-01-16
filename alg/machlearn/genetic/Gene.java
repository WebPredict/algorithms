package alg.machlearn.genetic;

import java.util.Random;

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

    public void mutate (double mutationRate) {
        Random random = new Random();
        for (int i = 0; i < weights.length; i++) {
            if (random.nextDouble() < mutationRate) {
                // hmm how much mutation?
                weights [i] = random.nextDouble();
            }
        }
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
