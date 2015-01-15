package alg.machlearn.genetic;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FitnessEvaluator {

    /**
     * How good is these gene, on a scale of 0 to 1?
     * @param gene
     * @return
     */
    double  evaluate (Gene gene);
}
