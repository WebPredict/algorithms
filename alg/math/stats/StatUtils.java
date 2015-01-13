package alg.math.stats;

import alg.arrays.ArrayUtils;
import alg.misc.InterestingAlgorithm;
import alg.sort.SortUtils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/6/14
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatUtils {

    /**
     * mean
     * median
     * mode
     * stddev
     * variance
     */

    public static double average (double [] values) {
        double  ret = Double.NaN;

        if (values != null && values.length > 0) {
            ret = 0;
            for (int i = 0; i < values.length; i++) {
                ret += values [i];
            }

            ret /= (double)values.length;
        }

        return (ret);
    }

    public static double average (int [] values) {
        double  ret = Double.NaN;

        if (values != null && values.length > 0) {
            ret = 0;
            for (int i = 0; i < values.length; i++) {
                ret += values [i];
            }

            ret /= (double)values.length;
        }

        return (ret);
    }

    @InterestingAlgorithm
    public static double modeSkewness (int [] values) {
        double mean = average(values);
        Integer mode = mode(values);

        if (mode == null)
            return (Double.NaN);

        return ((mean - (double)mode) / stddev(values));
    }

    @InterestingAlgorithm
    public static Integer mode (int [] values) {
        Integer ret = null;

        if (values != null && values.length > 0) {
            HashMap<Integer, Integer> valueToInstancesMap = new HashMap<Integer, Integer>();
            int highestIdxSeenSoFar = -1;
            int highestInstancesSoFar = 0;
            for (int i = 0; i < values.length; i++) {
                 Integer existing = valueToInstancesMap.get(values [i]);
                if (existing == null) {
                     existing = 0;
                }
                int newInstances = existing + 1;
                valueToInstancesMap.put(values [i], newInstances);
                if (newInstances > highestInstancesSoFar) {
                    highestInstancesSoFar = newInstances;
                    highestIdxSeenSoFar = i;
                }
            }
            return (values [highestIdxSeenSoFar]);

        }
        return (ret);
    }

    @InterestingAlgorithm
    public static double median (int [] values) {
        if (values == null || values.length == 0)
            return (Double.NaN);

        int [] sorted = ArrayUtils.copy(values);
        SortUtils.sortInts(sorted);

        if (sorted.length % 2 == 0) {
            int half = sorted.length / 2;
            return ((double)(sorted [half - 1] + sorted [half]) / 2d);
        }
        else
            return ((double)sorted [sorted.length / 2]);
    }

    @InterestingAlgorithm
    public static double variance (int [] values) {
        if (values == null || values.length == 0)
            return (Double.NaN);

        Double average = average(values);
        Double ret = 0d;
        for (int i = 0; i < values.length; i++) {
         double diff = (double)values [i] - average;
            ret += diff * diff;
        }
        return (ret);
    }

    public static double stddev (int [] values) {
        return (Math.sqrt(variance(values)));
    }

    // runtime should be O(log(m + n)):
    @InterestingAlgorithm
    public static double findMedianSortedArrays (int [] a1, int [] a2) {
         return (Double.NaN); // TODO
    }

}
