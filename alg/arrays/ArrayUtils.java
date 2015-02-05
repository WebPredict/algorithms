package alg.arrays;

import alg.misc.InterestingAlgorithm;
import alg.misc.MiscUtils;
import alg.sort.SortUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtils {

    /**
     * Merge B into A, where A is assumed to have enough storage for both A and B.
     * @param A
     * @param m
     * @param B
     * @param n
     */
    @InterestingAlgorithm
    public static void merge(int A[], int m, int B[], int n) {

        for (int i = 0; i < n; i++) {

            int insertIdx = -1;
            for (int j = 0; j < m + i; j++) {
                if (A[j] > B[i]) {
                    insertIdx = j;
                    break;
                }
            }
            if (insertIdx == -1)
                insertIdx = m + i;

            for (int j = m + i; j > insertIdx; j--)
                A[j] = A[j - 1];
            A[insertIdx] = B[i];

        }

    }

    public static int []        join (int [] nums, int num) {
        int numsLen = nums == null ? 0 : nums.length;
        int [] ret = new int[numsLen + num];

        for (int i = 0; i < numsLen; i++) {
            ret [i] = nums [i];
        }
        ret [ret.length - 1] = num;
        return (ret);
    }

    public static int []        join (int [] nums, int [] nums2) {
        if (nums == null)
            return (nums2);
        else if (nums2 == null)
            return (nums);

        int len = nums.length + nums2.length;
        int [] ret = new int[len];

        System.arraycopy(nums, 0, ret, 0, nums.length);
        System.arraycopy(nums2, 0, ret, nums.length, nums2.length);

        return (ret);
    }

    @InterestingAlgorithm
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

    @InterestingAlgorithm
    public static void swapRows (int [][] grid, int fromIdx, int toIdx) {
        int [] tmp = grid [fromIdx];
        grid [fromIdx] = grid [toIdx];
        grid [toIdx] = tmp;
    }

    @InterestingAlgorithm
    public static void swapCols (int [][] grid, int fromIdx, int toIdx) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid [0].length; j++) {
                int tmp = grid [i][fromIdx];
                grid[i][fromIdx] = grid[i][toIdx];
                grid[i][toIdx] = tmp;
            }
        }
    }

    @InterestingAlgorithm
    public static List<Integer> spiralOrder (int [][] matrix) {
        if (matrix == null)
            return (null);

        if (matrix.length == 0 || matrix [0].length == 0)
            return (new ArrayList<Integer>());

        ArrayList<Integer>  numbers = new ArrayList<Integer>();

        int rows = matrix.length;
        int cols = matrix [0].length;
        int rowIdx = 0;
        int colIdx = 0;
        int maxRowIdx = rows - 1;
        int minRowIdx = 1;
        int maxColIdx = cols - 1;
        int minColIdx = 0;

        int direction = 0;
        while (numbers.size() < rows * cols) {
            numbers.add(matrix [rowIdx][colIdx]);
            switch (direction) {
                case 0:
                    if (colIdx < maxColIdx)
                        colIdx++;
                    else {
                        maxColIdx--;
                        rowIdx++;
                        direction = 1;
                    }
                    break;

                case 1:
                    if (rowIdx < maxRowIdx)
                        rowIdx++;
                    else {
                        colIdx--;
                        maxRowIdx--;
                        direction = 2;
                    }
                    break;

                case 2:
                    if (colIdx > minColIdx)
                        colIdx--;
                    else {
                        minColIdx++;
                        direction = 3;
                        rowIdx--;
                    }
                    break;

                case 3:
                    if (rowIdx > minRowIdx)
                        rowIdx--;
                    else {
                        minRowIdx++;
                        colIdx++;
                        direction = 0;
                    }
                    break;

            }
        }

        return (numbers);
    }

    @InterestingAlgorithm
    public static int [][] generateSpiralMatrix (int n) {
        if (n == 0)
            return (new int[0][0]);

        int [][] matrix = new int[n][n];
        int rows = n;
        int cols = n;
        int rowIdx = 0;
        int colIdx = 0;
        int maxRowIdx = rows - 1;
        int minRowIdx = 1;
        int maxColIdx = cols - 1;
        int minColIdx = 0;

        int direction = 0;
        int nextNum = 1;
        while (nextNum <= rows * cols) {
            matrix[rowIdx][colIdx] = nextNum++;

            switch (direction) {
                case 0:
                    if (colIdx < maxColIdx)
                        colIdx++;
                    else {
                        maxColIdx--;
                        rowIdx++;
                        direction = 1;
                    }
                    break;

                case 1:
                    if (rowIdx < maxRowIdx)
                        rowIdx++;
                    else {
                        colIdx--;
                        maxRowIdx--;
                        direction = 2;
                    }
                    break;

                case 2:
                    if (colIdx > minColIdx)
                        colIdx--;
                    else {
                        minColIdx++;
                        direction = 3;
                        rowIdx--;
                    }
                    break;

                case 3:
                    if (rowIdx > minRowIdx)
                        rowIdx--;
                    else {
                        minRowIdx++;
                        colIdx++;
                        direction = 0;
                    }
                    break;

            }
        }

        return (matrix);
    }

    /**
     *
     * @param array1
     * @param array2
     * @return  Sorted array of array1 + array2's contents
     */
    @InterestingAlgorithm
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

    @InterestingAlgorithm
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

    @InterestingAlgorithm
    public static List<List<Integer>> listPermutations (int [] num) {
        if (num == null)
            return (null);
        else if (num.length == 0)
            return (new ArrayList<List<Integer>>());
        else if (num.length == 1) {
             List<List<Integer>> ret = new ArrayList<List<Integer>>();
             ArrayList<Integer> retArr = new ArrayList<Integer>();
            retArr.add(num [0]);
            ret.add(retArr);
            return (ret);
        }

        int []  subArr = new int[num.length - 1];
        System.arraycopy(num, 1, subArr, 0, num.length - 1);
        List<List<Integer>>    subPermutations = listPermutations(subArr); // TODO fix to take an index to avoid this array copy
        List<List<Integer>>    retPermutations = new ArrayList<List<Integer>>();

        for (int i = 0; i < subPermutations.size(); i++) {
            List<Integer> subPermutation = subPermutations.get(i);
            for (int j = 0; j < subPermutation.size(); j++) {
                ArrayList<Integer> curPerm = new ArrayList<Integer>();
                curPerm.addAll(subPermutation.subList(0, j));
                curPerm.add(num[0]);
                curPerm.addAll(subPermutation.subList(j, subPermutation.size()));
                retPermutations.add(curPerm);
            }
            ArrayList<Integer> lastPerm = new ArrayList<Integer>();
            lastPerm.addAll(subPermutation);
            lastPerm.add(num[0]);
            retPermutations.add(lastPerm);
        }

        return (retPermutations);
    }

    public static List<Integer> getListOfBitsSet (BitSet bits) {
        ArrayList<Integer> next = new ArrayList<Integer>();
        for (int j = 0; j < bits.size(); j++) {
            if (bits.get(j))
                next.add(j + 1);
        }
        return (next);
    }

    /**
     * List all combinations of k numbers from the set 1..n
     * @param n
     * @param k
     * @return
     */
    @InterestingAlgorithm
    public static List<List<Integer>> listCombinations (int n, int k) {

        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        int []              com = new int[k];
        for (int i = 0; i < k; i++)
            com[i] = i;

        while (com[k - 1] < n) {

            ArrayList<Integer> nextCombo = new ArrayList<Integer>();
            for (int i = 0; i < k; i++)
                nextCombo.add(com[i] + 1);

            ret.add(nextCombo);

            int     t = k - 1;
            while (t != 0 && com[t] == n - k + t)
                t--;

            com[t]++;
            for (int i = t + 1; i < k; i++)
                com[i] = com[i - 1] + 1;
        }

        return (ret);
    }

    /**
     * List all combinations of k numbers from the given set of numbers
     * @param nums set of numbers
     * @param k
     * @return
     */
    @InterestingAlgorithm
    public static List<List<Integer>> listCombinations (int [] nums, int k) {

        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        int []              com = new int[k];
        for (int i = 0; i < k; i++)
            com[i] = i;

        int                 n = nums.length;
        while (com[k - 1] < n) {

            ArrayList<Integer> nextCombo = new ArrayList<Integer>();
            for (int i = 0; i < k; i++)
                nextCombo.add(nums[com[i]]);

            ret.add(nextCombo);

            int     t = k - 1;
            while (t != 0 && com[t] == n - k + t)
                t--;

            com[t]++;
            for (int i = t + 1; i < k; i++)
                com[i] = com[i - 1] + 1;
        }

        return (ret);
    }

    @InterestingAlgorithm
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

    /**
     * Merges overlapping intervals, from input text of the form: "[1 2],[2 6]..."
     */
    @InterestingAlgorithm
    public static List<Interval>    merge(String intervalText) {

        StringTokenizer tok = new StringTokenizer(intervalText, ",");
        ArrayList<Interval> intervals = new ArrayList<Interval>();
        while (tok.hasMoreTokens()) {
            String next = tok.nextToken();
            intervals.add(Interval.parse(next));
        }
        return (merge(intervals));
    }

    /**
     * Merges overlapping intervals
     * @param intervals
     * @return
     */
    @InterestingAlgorithm
    public static List<Interval>    insert(List<Interval> intervals, Interval newInterval) {
        intervals.add(newInterval);
        return (merge(intervals));
    }

    /**
     * Merges overlapping intervals
     * @param intervalList
     * @return
     */
    @InterestingAlgorithm
    public static List<Interval>    merge(List<Interval> intervalList) {
        if (intervalList == null)
            return (null);

        // sort intervals by start time
        Collections.sort(intervalList, new Comparator<Interval>() {

            public int compare(Interval first, Interval second) {
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
            boolean handledLast = false;
            while (next < intervalList.size()) {

                Interval nextInterval = intervalList.get(next);
                if (nextInterval.start >= interval.start && nextInterval.start <= interval.end) {
                     // they overlap.
                    // could be: completely contained, or actually overlapping:
                    if (nextInterval.end > interval.end)
                        interval =  new Interval(interval.start, nextInterval.end);
                    else
                        interval =  new Interval(interval.start, interval.end);

                    i++;
                    if (i == intervalList.size() - 1) {
                        merged.add(interval);
                        handledLast = true;
                    }
                }
                else {
                    merged.add(interval);
                    break;
                }
                next++;
            }
            if (i == intervalList.size() - 1 && !handledLast)
                merged.add(interval);
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
    @InterestingAlgorithm
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
    @InterestingAlgorithm
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

    @InterestingAlgorithm
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

    @InterestingAlgorithm
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
    @InterestingAlgorithm
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

    /**
     * Simple binary search
     * @param list
     * @return
     */
    public static int findIndexInSortedList (int [] list, int value) {
        if (list == null || list.length == 0)
            return (-1);

        int idx = list.length / 2;

        int startIdx = 0;
        int endIdx = list.length;

        while (startIdx != endIdx) {
            int curVal = list [idx];
            if (curVal == value) {
                return (idx);
            }
            else if (curVal < value) {
                startIdx = idx;
            }
            else {
                endIdx = idx;
            }
            idx = startIdx + (endIdx - startIdx) / 2;
        }
        return (-1);
    }

    /**
     * Finds two numbers that add to num, or null if none, in nlogn time.
     * @param values
     * @param num
     * @return
     */
    @InterestingAlgorithm
    public static int [] findTwoNumbersThatAddTo (int [] values, int num) {
        if (values == null)
            return (null);

        SortUtils.radixSort(values);

        for (int i = 0; i < values.length; i++) {
            int trial = values [i];

            int desired = num - trial;

            int desiredIdx = findIndexInSortedList(values, desired);
            if (desiredIdx != -1) {
                return (new int[] {i, desiredIdx});
            }
        }
        return (null);
    }

    /**
     * Removes duplicates in place, and returns length of de-duped array.
     * @param values
     * @return
     */
    @InterestingAlgorithm
    public static int   removeDuplicatesFromSortedArray (int [] values) {
        if (values == null)
            return (0);

        int idx = -1;

        Integer last = null;
        for (int i = 0; i < values.length; i++) {
            if (last == null || values [i] != last)
                idx++;

            if (idx != i)
                values [idx] = values [i];
            last = values [i];
        }

        return (idx + 1);
    }

    /**
     * Removes all instances of elem in the array in place and returns new size.
     * @param values
     * @return
     */
    @InterestingAlgorithm
    public static int   removeElement (int [] values, int elem) {
        if (values == null)
            return (0);

        int idx = -1;

        for (int i = 0; i < values.length; i++) {
            if (values [i] != elem) {
                idx++;

                if (idx != i)
                    values [idx] = values [i];
            }
        }

        return (idx + 1);
    }
}
