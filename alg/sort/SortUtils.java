package alg.sort;

import alg.arrays.ArrayUtils;
import alg.math.MathUtils;
import alg.misc.InterestingAlgorithm;
import alg.misc.MiscUtils;
import alg.misc.RecursionDepthExceeded;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SortUtils {

    @InterestingAlgorithm
    public static <T extends Comparable<? super T>> void    quickSort(List<T> list) {
        int size = list == null ? 0 : list.size();
        if (size < 2)
            return;
    	quickSort(list, 0, size);
    }

    @InterestingAlgorithm
    static <T extends Comparable<? super T>> void    quickSort(List<T> list, int start, int end) {
        if (start < end) {
            int partitionIdx = partition(list, start, end);
            quickSort(list, start, partitionIdx);
            quickSort(list, partitionIdx, end);
        }
    }

    public static <T extends Comparable<? super T>> void    quickSort(List<T> list, int maxRecDepth) throws RecursionDepthExceeded {
        int size = list == null ? 0 : list.size();
        if (size < 2)
            return;
        quickSort(list, 0, size, maxRecDepth, 0);
    }

    @InterestingAlgorithm
    static <T extends Comparable<? super T>> void    quickSort(List<T> list, int start, int end, int maxRecDepth, int recDepth) throws RecursionDepthExceeded {
        if (recDepth >= maxRecDepth)
            throw new RecursionDepthExceeded(maxRecDepth);

        if (start < end) {
            int partitionIdx = partition(list, start, end);
            quickSort(list, start, partitionIdx, maxRecDepth, recDepth++);
            quickSort(list, partitionIdx, end, maxRecDepth, recDepth++);
        }
    }

    @InterestingAlgorithm
    public static <T extends Comparable<? super T>> int partition (List<T> list, int start, int end) {
        int pivotIdx = choosePivot(list, start, end);
        T pivotValue = list.get(pivotIdx);

        MiscUtils.swap(list, pivotIdx, end);
        int storeIdx = start;

        for (int i = start; i < end; i++) {
            if (list.get(i).compareTo(pivotValue) < 0) {
                MiscUtils.swap(list, i, storeIdx);
                storeIdx++;
            }
        }
        MiscUtils.swap(list, storeIdx, end);
        return (storeIdx);
    }

    // random pivot choice for now:
    public static <T extends Comparable<? super T>> int choosePivot (List<T> list, int start, int end) {
         // simple random strategy for now:
        return (new Random().nextInt(end - start)) + start;
    }

    @InterestingAlgorithm
    public static <T extends Comparable<? super T>> void    mergeSort(List<T> list) {
    	int size = list == null ? 0 : list.size();

        if (size < 2)
            return;

        if (size == 2) {
            if (list.get(0).compareTo(list.get(1)) > 0) {
                MiscUtils.swap(list, 0, 1);
            }
        }
        else {
            int halfSize = size / 2;
            List<T> left = new ArrayList<T>();
            left.addAll(list.subList(0, halfSize));
            mergeSort(left);
            List<T> right = new ArrayList<T>();
            right.addAll(list.subList(halfSize, size - 1));
            mergeSort(right);

            mergeSortedListsWithStorage(left, right, list);
        }
    }

    @InterestingAlgorithm
    public static  <T extends Comparable<? super T>> void mergeSortedListsWithStorage (List<T> first, List<T> second, List<T> storage) {
        int firstLen = first == null ? 0 : first.size();
        int secondLen = second == null ? 0 : second.size();
        int storageLen = storage == null ? 0 : storage.size();

        if (firstLen + secondLen > storageLen)
            throw new RuntimeException("Not enough space for merge sorted lists... need: " + (firstLen + secondLen) + " and got: " + storageLen);

        Comparable []   ret = new Comparable[firstLen + secondLen];

        int array1Idx = 0;
        int array2Idx = 0;
        int idx = 0;
        while (array1Idx < firstLen && array2Idx < secondLen) {

            if (array1Idx >= firstLen) {
                ret [idx++] = second.get(array2Idx++);
            }
            else if (array2Idx >= secondLen) {
                ret [idx++] = first.get(array1Idx++);
            }
            else {
                if (first.get(array1Idx).compareTo(second.get(array2Idx)) < 0) {
                    ret [idx++] = first.get(array1Idx++);
                }
                else {
                    ret [idx++] = second.get(array2Idx++);
                }
            }
        }
    }

    /**
     * Begin with quicksort, switch to heapsort if recursion depth gets too large
     * @param list
     * @param <T>
     */
    @InterestingAlgorithm
    public static <T extends Comparable<? super T>> void    introSort(List<T> list) {
    	try {
            quickSort(list, (int)(3d * Math.log(list.size())));
        }
        catch (RecursionDepthExceeded e) {
            heapSort(list);
        }
    }

    @InterestingAlgorithm
    public static <T extends Comparable<? super T>> void    bubbleSort(List<T> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                T value = list.get(i);

                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(j).compareTo(value) < 0) {
                        MiscUtils.swap(list, i, j);
                    }
                    else
                        break;
                }

            }
        }
    }


    public static <T> void    heapSort (List<T> list) {
        heapSort(list, null);
    }

    @InterestingAlgorithm
    public static <T> void    heapSort (List<T> list, Comparator<T> comparator) {
         if (list == null)
             return;

        int size = list.size();

        if (list.size() < 2)
            return;

        if (comparator == null)
            heapify(list, size);
        else
            heapify(list, size, comparator);

        int end = size - 1;
        if (comparator == null) {
            while (end > 0) {
                MiscUtils.swap(list, end, 0);
                end--;
                siftDown(list, 0, end);
            }
        }
        else {
            while (end > 0) {
                MiscUtils.swap(list, end, 0);
                end--;
                siftDown(list, 0, end, comparator);
            }
        }
    }

    @InterestingAlgorithm
    public static <T> void    heapify (List<T> list, int count) {
        int start = (int)Math.floor((count - 2) / 2);

        while (start >= 0) {
            siftDown(list, start, count - 1);
            start--;
        }
    }

    public static <T> void    heapify (List<T> list, int count, Comparator<T> comparator) {
        int start = (int)Math.floor((count - 2) / 2);

        while (start >= 0) {
            siftDown(list, start, count - 1, comparator);
            start--;
        }
    }

    @InterestingAlgorithm
    public static <T> void    siftDown (List<T> list, int start, int end, Comparator<T> comparator) {
        int root = start;

        while (root * 2 + 1 <= end) {
            int child = root * 2 + 1;
            int swap = root;

            if (comparator.compare(list.get(swap), list.get(child)) < 0) {
                swap = child;
            }
            if (child + 1 <= end && comparator.compare(list.get(swap), list.get(child + 1)) < 0) {
                swap = child + 1;
            }
            if (swap == root) {
                return;
            }
            else {
                MiscUtils.swap(list, root, swap);
                root = swap;
            }
        }
    }

    @InterestingAlgorithm
    public static <T> void    siftDown (List<T> list, int start, int end) {
        int root = start;

        while (root * 2 + 1 <= end) {
            int child = root * 2 + 1;
            int swap = root;

            if (((Comparable)list.get(swap)).compareTo((Comparable)list.get(child)) < 0) {
                swap = child;
            }
            if (child + 1 <= end && (((Comparable)list.get(swap)).compareTo((Comparable)list.get(child + 1)) < 0)) {
                swap = child + 1;
            }
            if (swap == root) {
                return;
            }
            else {
                MiscUtils.swap(list, root, swap);
                root = swap;
            }
        }
    }

    @InterestingAlgorithm
    public static <T extends Comparable<? super T>> void    selectionSort(List<T> list) {
    	if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                int leastIdx = -1;
                T least = null;

                for (int j = i; j < list.size(); j++) {
                    if (least == null) {
                        leastIdx = j;
                        least = list.get(j);
                    }
                    else if (list.get(j).compareTo(least) < 0) {
                        leastIdx = j;
                        least = list.get(j);
                    }
                }
                if (leastIdx != i) {
                    MiscUtils.swap(list, i, leastIdx);
                }
            }
        }
    }

    public static void      sortInts (int [] values) {
    	radixSort(values);   // Hmm
    }

    @InterestingAlgorithm
    public static void    radixSort (int [] values) {
    	if (values == null || values.length < 2)
            return;

        int     numDigits = String.valueOf(MathUtils.max(values)).length();

        for (int i = 0; i < numDigits; i++) {
            ArrayList<Integer> []    lists = new ArrayList[10];
            int divisor = (int)Math.pow(10, i);

            for (int j = 0; j < values.length; j++) {
                int remainder = (values [j] / divisor) % 10;
                ArrayList<Integer> list = lists [remainder];
                if (list == null) {
                    list = new ArrayList<Integer>();
                    lists [remainder] = list;
                }
                list.add(values [j]);
            }

            int idx = 0;
            for (int j = 0; j < lists.length; j++) {
                ArrayList<Integer> list = lists[j];
                if (list != null) {
                    for (int k = 0; k < list.size(); k++) {
                        values [idx++] = list.get(k);
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @param inputLargeDataListFileName name of a file of a list of items to be sorted. May be too large to fit in memory
     */
    @InterestingAlgorithm
    public static void		externalSort (String inputLargeDataListFileName, String outputSortedDataListFileName) {
        // load blocks into memory one by one, sort them, then merge sorted blocks in pairs?
    	// TODO
        // issues: how to know size of items to be sorted, and how this relates to loaded block size. Try to avoid loading half an item
    }
}
