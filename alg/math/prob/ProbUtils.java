package alg.math.prob;

import alg.math.MathUtils;
import alg.misc.InterestingAlgorithm;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/11/14
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProbUtils {

    /**
     * dice
     * coins
     * cards
     * expected values
     * bayes theorem
     */

    public static double   bayesTheorem (double probA, double probB, double probBGivenA) {
        return (probBGivenA * probA / probB);
    }

    @InterestingAlgorithm
    public static int []	randomIntegers (int [] list, int randomSize) {
    	// returns a random subset of size randomSize from list

        // This should work, but TODO change to different approach to avoid the array copying
    	Integer []	shuffledList = new Integer [list.length];
    	for (int i = 0; i < shuffledList.length; i++) {
            shuffledList [i] = list [i];
        }

        randomShuffleInPlace(shuffledList);

        int [] randomList = new int[randomSize];
        for (int i = 0; i < randomSize; i++) {
            randomList [i] = shuffledList [i];
        }
    	return (randomList); 
    }

    @InterestingAlgorithm
    public static void  randomShuffleInPlace (Object [] objects) {
        if (objects == null)
            return;
        for (int i = 0; i < objects.length; i++) {
            int randomIdx = new Random().nextInt(objects.length);
            Object tmp = objects [randomIdx];
            objects [randomIdx] = objects [i];
            objects [i] = tmp;
        }
    }

    public static double expectedValue (double chanceSomethingHappens, double payoff, double penalty, int attempts) {

        return (double) (attempts * (chanceSomethingHappens * payoff - (1d - chanceSomethingHappens) * penalty));
    }

    public static boolean coinFlip () {
        return (MathUtils.generateRandom(2) == 0);
    }
}
