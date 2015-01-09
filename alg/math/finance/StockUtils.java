package alg.math.finance;

import alg.arrays.Interval;
import alg.math.prob.ProbUtils;
import alg.math.stats.StatUtils;

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
    public static double    computeVolatility (int [] prices) {
        return (StatUtils.stddev(prices));
    }

    /**
     *
     * @param prices
     * @param n
     * @return
     */
    public static int [][]  getTopNPeakAndValleyIndicesSorted (int [] prices, int n) {
        return (null);
    }

    public static int   maxProfitWithMaxTransactions (int [] prices) {
        if (prices == null)
            return (0);

        int ret = 0;

        /**
         * Approach: Just add up all the climbs/runs I guess?
         *
         */
        Integer startIdx = null;

        for (int i = 0; i < prices.length; i++) {
            if (i < prices.length - 1 && prices [i + 1] > prices [i] && startIdx == null) {
                startIdx = i;
            }
            if (i > 0 && prices [i - 1] > prices [i] && startIdx != null) {
                ret += prices [startIdx] - prices [i - 1];
                startIdx = null;
            }
        }

        return (ret);
    }

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


//    /**
//     * TODO remove maybe
//     * @param prices
//     * @return
//     */
//    public static int   maxProfitWithAtMostTwoTransactionsBad (int [] prices) {
//        if (prices == null)
//            return (0);
//
//        int ret = 0;
//
//
//        /**
//         * one approach:
//         * get least, second, third idxes
//         * get most, second, third idxes
//         *
//         *  then we can make intervals for all pairs of low, high where lowidx < highidx
//         *  then we need to return either two non-overlapping pairs, or one pair, whichever adds to the most
//         *
//         */
//
//        int [] lowestValues = new int [] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
//        int [] lowestValueIdxes = new int [] {-1, -1, -1};
//        int [] highestValues = new int [] {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
//        int [] highestValueIdxes = new int [] {-1, -1, -1};
//
//        for (int i = 0; i < prices.length; i++) {
//            if (prices [i] < lowestValues [0]) {
//                lowestValues[0] = prices [i];
//                lowestValueIdxes[0] = i;
//            }
//            else if (prices [i] < lowestValues[1]) {
//                lowestValues[1] = prices [i];
//                lowestValueIdxes[1] = i;
//            }
//            else if (prices [i] < lowestValues[2]) {
//                lowestValues[2] = prices [i];
//                lowestValueIdxes[2] = i;
//            }
//            // TODO
//            if (prices [i] < lowestValues [0]) {
//                lowestValues[0] = prices [i];
//                lowestValueIdxes[0] = i;
//            }
//            else if (prices [i] < lowestValues[1]) {
//                lowestValues[1] = prices [i];
//                lowestValueIdxes[1] = i;
//            }
//            else if (prices [i] < lowestValues[2]) {
//                lowestValues[2] = prices [i];
//                lowestValueIdxes[2] = i;
//            }
//            // TODO the same for highest
//        }
//
//        ArrayList<Interval> intervals = new ArrayList<Interval>();
//        int maxDiff = 0;
//        for (int i = 0; i < lowestValueIdxes.length; i++) {
//            int lowestIdx = lowestValueIdxes [i];
//
//            for (int j = 0; j < highestValueIdxes.length; j++) {
//                int highestIdx = highestValueIdxes [j];
//                if (highestIdx > lowestIdx) {
//                    int diff = highestValues [j] - lowestValues [i];
//                    if (diff > maxDiff)
//                        maxDiff = diff;
//                    intervals.add(new Interval(lowestIdx, highestIdx));
//                }
//            }
//        }
//        Collections.sort(intervals, new Comparator<Interval>() {
//
//            public int compare(Interval first, Interval second) {
//                //return ((first.end - first.start) - (second.end - second.start));
//                if (first.start < second.start)
//                    return (-1);
//                else if (first.start == second.start)
//                    return (0);
//                else
//                    return (1);
//            }
//        });
//
//        for (int i = 0; i < intervals.size(); i++) {
//            Interval cur = intervals.get(i);
//
//            for (int j = i + 1; j < intervals.size(); j++) {
//                Interval next = intervals.get(j);
//                if (next.start > cur.end) {
//                    int possibleMax  = prices [cur.end] - prices [cur.start] + prices [next.end] - prices [next.start];
//                    if (possibleMax > maxDiff)
//                        maxDiff = possibleMax;
//                }
//            }
//        }
//
//        return (maxDiff);
//    }

    /**
     * @param prices
     * @return
     */
    public static int   maxProfitWithOneTransaction (int [] prices) {
        if (prices == null)
            return (0);

        return (maxProfitWithOneTransaction(prices, 0, prices.length));
    }

    /**
     * @param prices
     * @return
     */
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
        int currentLowest = Integer.MAX_VALUE;
        int currentLowestIdx = -1;
        int currentHighest = Integer.MIN_VALUE;
        int currentHighestIdx = -1;

        for (int i = startIdx; i < endIdx; i++) {
            if (i > startIdx) {
                if (prices [i] < currentLowest) {
                    currentLowest = prices [i];
                    currentLowestIdx = i;
                }
                else if (prices [i] > currentHighest) {
                    currentHighest = prices [i];
                    currentHighestIdx = i;
                }
            }
        }

        if (currentLowestIdx != -1 && currentLowestIdx < currentHighestIdx) {
            ret = prices [currentHighestIdx] - prices [currentLowestIdx];
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

        /**
         * some cases:
         * length 0, return 0
         * all prices going down, return 0
         * monotonically up, return last - first
         *
         */
        int currentLowest = Integer.MAX_VALUE;
        int currentLowestIdx = -1;
        int currentHighest = Integer.MIN_VALUE;
        int currentHighestIdx = -1;

        for (int i = startIdx; i < endIdx; i++) {
            if (i > startIdx) {
                if (prices [i] < currentLowest) {
                    currentLowest = prices [i];
                    currentLowestIdx = i;
                }
                else if (prices [i] > currentHighest) {
                    currentHighest = prices [i];
                    currentHighestIdx = i;
                }
            }
        }

        if (currentLowestIdx != -1 && currentLowestIdx < currentHighestIdx) {
            return (new int[] {currentLowestIdx, currentHighestIdx});
        }
        return (null);
    }
}
