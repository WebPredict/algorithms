package alg.arrays;

import alg.misc.MiscUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtils {

    public static Comparable [] removeDuplicates (Comparable [] inArray) {
        if (inArray == null)
            return (null);

        HashSet<Comparable> seen = new HashSet<Comparable>();
        ArrayList<Comparable> retArray = new ArrayList<Comparable>();

        for (int i = 0; i < inArray.length; i++) {
            if (!seen.contains(inArray[i])) {
                retArray.add(inArray[i]);
                seen.add(inArray[i]);
            }
        }
        Comparable []   ret = new Comparable[retArray.size()];
        retArray.toArray(ret);

        return (ret);
    }

    public static Comparable [] mergeSortedArrays (Comparable [] array1, Comparable [] array2) {
        if (array1 == null)
            return (array2);
        else if (array2 == null)
            return (array1);

        Comparable []   ret = new Comparable[array1.length + array2.length];

        int array1Idx = 0;
        int array2Idx = 0;
        for (int i = 0; i < array1.length + array2.length; i++) {
            if (array1Idx < array1.length) {
                if (array1 [array1Idx].compareTo(array2 [array2Idx]) <= 0) {
                     ret [i] = array1[array1Idx++];
                }
                else {
                    ret [i] = array2[array2Idx++];
                }
            }
        }
        return (ret);
    }

    public static int []    copy (int [] array) {
        if (array == null)
            return (null);
        int [] ret = new int[array.length];
        System.arraycopy(array, 0, ret, 0, array.length);
        return (ret);
    }

    public static int []    interleave (int [] array1, int [] array2) {
        if (array1 == null)
            return (array2);
        else if (array2 == null)
            return (array1);

        int [] ret = new int[array1.length + array2.length];

        int leastMaxLen = Math.min(array1.length, array2.length);

        for (int i = 0; i < leastMaxLen; i++) {
            if (i % 2 == 0) {
                ret [i] = array1 [i / 2];
            }
            else {
                ret [i] = array2 [i / 2];
            }
        }

        if (array1.length > array2.length) {
            System.arraycopy(array1, array2.length, ret, array2.length, array1.length - array2.length);
        }
        else if (array2.length > array1.length) {
            System.arraycopy(array2, array1.length, ret, array1.length, array2.length - array1.length);
        }

        return (ret);
    }

    public static List<Interval>    merge(List<Interval> intervalList) {
        return (null); // TODO
    }

    public static int median (List<Integer> list) {
        return (list.get(medianOfMediansIdx(list, 0, list.size())));
    }

    public static int medianOfMediansIdx (List<Integer> list) {
        return (medianOfMediansIdx(list, 0, list.size()));
    }

    /**
     * returns the element that appears more than numbers.length / 2 times, or null if there is no such element.
     * @param numbers
     * @return
     */
    public static Integer majorityElement (int [] numbers) {
        Integer ret = null;

        if (numbers != null) {
            int     threshhold = numbers.length / 2 + 1;

            HashMap<Integer, Integer> numToCountMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < numbers.length; i++) {
                Integer seen = numToCountMap.get(numbers[i]);
                if (seen == null) {
                    seen = 0;
                }
                seen++;

                if (seen >= threshhold) {
                    return (numbers[i]);
                }
                numToCountMap.put(numbers[i], seen);
            }
        }
        return (ret);
    }

    /**
     * returns median of medians between start and end indexes in list. Modifies list!
     * @param list
     * @param start
     * @param end
     * @return
     */
    public static int medianOfMediansIdx (List<Integer> list, int start, int end) {
        int numMedians = (int)Math.ceil(((double)(end - start)) / 5d);

        for (int i = 0; i < numMedians; i++) {
            int subStart = start + i * 5;
            int subEnd = subStart + 4;

            if (subEnd > end)
                subEnd = end;

            int medianIdx = selectIdx(list, subStart, subEnd, (subEnd - subStart) / 2);

            MiscUtils.swap(list, start + i, medianIdx);
        }
        return (selectIdx(list, start, end + numMedians - 1, numMedians / 2));
    }

    public static int selectKthLargest (List<Integer> list, int k) {
        if (list == null || list.size() < k)
            throw new RuntimeException("Invalid list for select kth largest: " + list + " k: " + k);

        return (select(list, 0, list.size(), k));
    }

    /**
     * returns nth smallest element of list within left -> right inclusive
     * @param list
     * @param left
     * @param right
     * @param n
     * @return
     */
    public static int select (List<Integer> list, int left, int right, int n) {
        int idx = selectIdx(list, left, right, n);
        return (list.get(idx));
    }

    public static int selectIdx (List<Integer> list, int left, int right, int n) {
        if (left == right)
            return (left);
        else {
            while (true) {
                int pivotIndex = left + (int)Math.floor(Math.random() * (right - left + 1));
                pivotIndex = partition(list, left, right, pivotIndex);
                // the pivot is in its final sorted position
                if (n == pivotIndex)
                    return (n);
                else if (n < pivotIndex)
                    return (selectIdx(list, left, pivotIndex - 1, n));
                else
                    return (selectIdx(list, pivotIndex + 1, right, n));
            }
        }
    }

    /**
     * moves everything less than list[pivotIndex] to the left, and everything greater to the right,
     * and returns the new pivotIndex after doing so
     * @param list
     * @param left
     * @param right
     * @param pivotIndex
     * @return
     */
    public static int partition(List<Integer> list, int left, int right, int pivotIndex) {
        int pivotValue = list.get(pivotIndex);
        MiscUtils.swap(list, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (list.get(i) < pivotValue) {
                MiscUtils.swap(list, storeIndex, i);
                storeIndex++;
            }
        }
        MiscUtils.swap(list, right, storeIndex);
        return (storeIndex);

    }
}
