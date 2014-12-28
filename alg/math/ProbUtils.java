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
    
    public static int []	randomIntegers (int [] list, int randomSize) {
    	// returns a random subset of size randomSize from list
    	
    	int []	randomList = new int [randomSize];
    	// one approach: shuffle list, then just pick first randomSize from it
    	// but we don't want to modify list, nor take up a lot of space
    	// issue is how to avoid duplicates
    	
    	for (int i = 0; i < randomList.length; i++) {
    		int nextIdx = MathUtils.generateRandom(list.length);
    		
    	}
    	return (randomList); 
    }
}
