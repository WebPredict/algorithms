package alg.math.finance;

import alg.arrays.Interval;
import alg.math.prob.ProbUtils;
import alg.math.stats.StatUtils;
import alg.misc.InterestingAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/9/15
 * Time: 9:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class StockUtils {

    /**
     *
     * @param prices
     * @return
     */
    @InterestingAlgorithm
    public static int [][]   getAllPeakAndValleyIndicesLeftToRight (int [] prices) {
        if (prices == null)
            return (null);

        ArrayList<Integer> peakIndices = new ArrayList<Integer>();
        ArrayList<Integer> valleyIndices = new ArrayList<Integer>();

        for (int i = 0; i < prices.length; i++) {

        }
        return (null);
    }

    /**
     *
     * @param prices
     * @return
     */
    @InterestingAlgorithm
    public static double    computeVolatility (int [] prices) {
        return (StatUtils.stddev(prices));
    }

    /**
     *
     * @param prices
     * @param n
     * @return
     */
    @InterestingAlgorithm
    public static int [][]  getTopNPeakAndValleyIndicesSorted (int [] prices, int n) {
        return (null);
    }

    /**
     *
     * @param num
     * @return
     */
    @InterestingAlgorithm
    public static int findPeakElementIdx(int[] num) {

        if (num == null || num.length < 2)
            return (0);

        for (int i = 0; i < num.length; i++) {

//            if (i == 1 && num[0] > num[1])
//                return (num[0]);// special case

            if (i > 0 && i < num.length - 1) {
                if (num [i - 1] < num [i] && num [i] > num[i + 1])
                    return (i);
            }
            else if (i == num.length - 1) {
                if (num.length == 1 || num [i-1] < num[i])
                    return (i);
            }

        }
        return (0);
    }

    /**
     *
     * @param prices
     * @return
     */
    @InterestingAlgorithm
    public static int   maxProfitWithMaxTransactions (int [] prices) {
        if (prices == null)
            return (0);

        int ret = 0;

        /**
         * Approach: Just add up all the climbs/runs
         *
         */
        Integer startIdx = null;

        for (int i = 0; i < prices.length; i++) {
            if (i > 0 && prices [i] > prices [i - 1] && startIdx == null) {
                startIdx = i - 1;
            }
            if (i > 0 && prices [i] < prices [i - 1] && startIdx != null) {
                ret += prices [i - 1] - prices [startIdx];
                startIdx = null;
            }
            else if (i == prices.length - 1 && startIdx != null) {
                ret += prices [i] - prices [startIdx];
            }
        }

        return (ret);
    }

    /**
     *
     * @param prices
     * @return
     */
    @InterestingAlgorithm
    public static int   maxProfitWithAtMostTwoTransactions (int [] prices) {
        if (prices == null)
            return (0);

        int ret = 0;
        int [] singleMaxIndices = maxProfitWithOneTransactionIndices (prices, 0, prices.length);

        if (singleMaxIndices == null)
            return (0);
        else {

            int max = prices [singleMaxIndices[1]] - prices [singleMaxIndices[0]];

            int [] leftMaxIndices = maxProfitWithOneTransactionIndices(prices, 0, singleMaxIndices [0]);

            int leftMax = max;
            int rightMax = max;
            if (leftMaxIndices != null) {
                leftMax += prices [leftMaxIndices[1]] - prices [leftMaxIndices[0]];
            }
            int [] rightMaxIndices = maxProfitWithOneTransactionIndices(prices, singleMaxIndices[1], prices.length);
            if (rightMaxIndices != null) {
                rightMax += prices [rightMaxIndices[1]] - prices [rightMaxIndices[0]];
            }

            if (leftMax > max)
                ret = leftMax;
            else if (rightMax > max)
                ret = rightMax;
            else
                ret = max;

            /**
             * This covers the cases of:
             * single large transaction is best
             * biggest transaction plus next biggest to the left, or next biggest to the right is two best
             * Remaining case: two transactions, both less than the biggest, add up to more than that and should be taken
             * One of which must be more than half the biggest
             * Approach: Find all runs that are at least half as big
             * Take the two biggest ones that do not overlap. If they add up to more than best so far, return their total
             */
        }
        return (ret);
    }

    /**
     * @param prices
     * @return
     */
    @InterestingAlgorithm
    public static int   maxProfitWithOneTransaction (int [] prices) {
        if (prices == null)
            return (0);

        return (maxProfitWithOneTransaction(prices, 0, prices.length));
    }


    /**
     * @param prices
     * @return
     */
    @InterestingAlgorithm
    public static int   maxProfitWithOneTransaction (int [] prices, int startIdx, int endIdx) {
        if (prices == null)
            return (0);

        int ret = 0;

        /**
         * some cases:
         * length 0, return 0
         * all prices going down, return 0
         * monotonically up, return last - first
         *
         */
        int runStartIdx = -1;
        int curRunSize = 0;
        int maxRunSize = 0;
        int maxRunStartIdx = -1;
        int maxRunEndIdx = -1;

        for (int i = startIdx; i < endIdx; i++) {
            if (i > startIdx) {
                if (prices [i] > prices [i - 1]) {
                    // we have a run
                    if (runStartIdx == -1) {
                        runStartIdx = i - 1;
                        curRunSize = 0; //prices [i] - prices [i - 1];
                    }
                }

                curRunSize +=  prices [i] - prices [i - 1];

                if (curRunSize < 0) {
                    // should consider a new run
                    runStartIdx = -1;
                }

                if (curRunSize > maxRunSize) {
                    maxRunSize = curRunSize;
                    maxRunStartIdx = runStartIdx;
                    maxRunEndIdx = i;
                }
            }
        }

        if (maxRunStartIdx != -1 && maxRunEndIdx != -1) {
            ret = prices [maxRunEndIdx] - prices [maxRunStartIdx];
        }
        return (ret);
    }

    /**
     * @param prices
     * @return
     */
    public static int []   maxProfitWithOneTransactionIndices (int [] prices, int startIdx, int endIdx) {
        if (prices == null)
            return (null);

        int ret = 0;

        /**
         * some cases:
         * length 0, return 0
         * all prices going down, return 0
         * monotonically up, return last - first
         *
         */
        int runStartIdx = -1;
        int curRunSize = 0;
        int maxRunSize = 0;
        int maxRunStartIdx = -1;
        int maxRunEndIdx = -1;

        for (int i = startIdx; i < endIdx; i++) {
            if (i > startIdx) {
                if (prices [i] > prices [i - 1]) {
                    // we have a run
                    if (runStartIdx == -1) {
                        runStartIdx = i - 1;
                        curRunSize = 0; //prices [i] - prices [i - 1];
                    }
                }

                curRunSize +=  prices [i] - prices [i - 1];

                if (curRunSize < 0) {
                    // should consider a new run
                    runStartIdx = -1;
                }

                if (curRunSize > maxRunSize) {
                    maxRunSize = curRunSize;
                    maxRunStartIdx = runStartIdx;
                    maxRunEndIdx = i;
                }
            }
        }

        if (maxRunStartIdx != -1 && maxRunEndIdx != -1) {
            return new int[] {maxRunStartIdx, maxRunEndIdx};
        }
        return (null);
    }
}
