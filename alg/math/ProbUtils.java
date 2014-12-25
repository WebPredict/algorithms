package alg.math;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/11/14
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProbUtils {

    /**
     * Markov chains
     * dice
     * coins
     * cards
     * expected values
     * bayes theorem
     */

    public double   bayesTheorem (double probA, double probB, double probBGivenA) {
        return (probBGivenA * probA / probB);
    }
}
