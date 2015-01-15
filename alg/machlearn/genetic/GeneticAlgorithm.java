package alg.machlearn.genetic;

import alg.misc.InterestingAlgorithm;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/12/14
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class GeneticAlgorithm {
    // Placeholder for something more specific...

    private double generationalFractionToKeep;
    private double mutationRate; // probability of random mutation in one factor in one gene

    public GeneticAlgorithm (double generationalFractionToKeep, double mutationRate) {
        this.generationalFractionToKeep = generationalFractionToKeep;
        this.mutationRate = mutationRate;
    }

    @InterestingAlgorithm
    public Population   computeNextGeneration (Population population, FitnessEvaluator fitnessEvaluator) {

        Population newPopulation = new Population();
        List<Gene> genes = population.getIndividuals();

        if (genes == null)
            return (newPopulation); // TODO

        /**
         * Approach:
         * evaluate them all for fitness
         * do some cross-breeding, mostly with the fitter ones?
         * Only promote top fraction to next generation
         */
        final HashMap<Gene, Double> geneToFitness = new HashMap<Gene, Double>();

        for (Gene gene : genes) {
            geneToFitness.put(gene, fitnessEvaluator.evaluate(gene));
        }

        ArrayList<Gene> newGenes = new ArrayList<Gene>();
        newGenes.addAll(genes);

        Collections.sort(newGenes, new Comparator<Gene>() {
            @Override
            public int compare(Gene o1, Gene o2) {
                Double o1Fitness = geneToFitness.get(o1);
                Double o2Fitness = geneToFitness.get(o2);

                if (o1Fitness < o2Fitness)
                    return (-1);
                else if (o1Fitness > o2Fitness)
                    return (1);
                else
                    return (0);
            }
        });

        // TODO: breeding, for example:
        // For each pair of fit individuals:
        // randomly choose either first or second's weights for a particular factor
        // sprinkle in the mutations
        // add to population

        return (newPopulation); // TODO
    }
}
