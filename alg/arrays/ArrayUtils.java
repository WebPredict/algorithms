package alg.arrays;

import alg.misc.MiscUtils;

import java.util.*;

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

    /**
     *
     * @param array1
     * @param array2
     * @return  Sorted array of array1 + array2's contents
     */
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

    public int []           equilibriumIndices (int [] array) {
        if (array == null)
            return (null);

        long [] reversePartialSums = new long[array.length];

        for (int i = array.length - 1; i >= 0; i--) {
            if (i == array.length - 1)
                reversePartialSums [i] = array [i];
            else
                reversePartialSums [i] = array [i] + reversePartialSums [i + 1];
        }

        ArrayList<Integer> indices = new ArrayList<Integer>();
        long partialSum = 0;
        for (int i = 0; i < array.length; i++) {
            partialSum += array [i];
            if (partialSum == reversePartialSums [i])
                indices.add(i);
        }
        int [] ret = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++)
            ret [i] = indices.get(i);

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
        if (intervalList == null)
            return (null);

        // sort intervals by start time
        Collections.sort(intervalList, new Comparator<Interval>() {

            public int compare (Interval first, Interval second) {
                if (first.start < second.start)
                    return (-1);
                else if (first.start == second.start)
                    return (0);
                else
                    return (1);
            }
        });

        ArrayList<Interval> merged = new ArrayList<Interval>();
        // then merge overlaps
        for (int i = 0; i < intervalList.size(); i++) {

            Interval interval = intervalList.get(i);
            int next = i + 1;
            while (next < intervalList.size()) {

                Interval nextInterval = intervalList.get(next);
                if (nextInterval.start >= interval.start && nextInterval.start <= interval.end) {
                     // they overlap.
                    // could be: completely contained, or actually overlapping:
                    if (nextInterval.end <= interval.end) {
                        // we don't need this one
                        //i++;
                    }
                    else {
                        interval =  new Interval(interval.start, nextInterval.end);
                    }
                }
                else {
                    merged.add(interval);
                    break;
                }
                next++;
            }
        }

        return (merged);
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
        return (null);
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
