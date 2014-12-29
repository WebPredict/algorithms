package alg.sort;

import alg.misc.MiscUtils;
import alg.misc.RecursionDepthExceeded;

import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SortUtils {

    public static <T extends Comparable<? super T>> void    quickSort(List<T> list) {
    	// TODO
    }

    public static <T extends Comparable<? super T>> void    quickSort(List<T> list, int maxRecDepth) throws RecursionDepthExceeded {
        // TODO
    }

    public static <T extends Comparable<? super T>> void    mergeSort(List<T> list) {
    	// TODO
    }

    /**
     * Begin with quicksort, switch to heapsort if recursion depth gets too large
     * @param list
     * @param <T>
     */
    public static <T extends Comparable<? super T>> void    introSort(List<T> list) {
    	try {
            quickSort(list, (int)(3d * Math.log(list.size())));
        }
        catch (RecursionDepthExceeded e) {
            heapSort(list);
        }
    }

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
    	// TODO
    }

    public static void    radixSort (int [] values) {
    	// TODO
    }
    
    /**
     * 
     * @param inputLargeDataListFileName name of a file of a list of items to be sorted. May be too large to fit in memory
     */
    public static void		externalSort (String inputLargeDataListFileName, String outputSortedDataListFileName) {
        // load blocks into memory one by one, sort them, then merge sorted blocks in pairs?
    	// TODO
    }
}
