package alg.misc;

import alg.strings.StringUtils;
import alg.util.LinkNode;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiscUtils {

    public static int fibonacci(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Invalid fibonacci num: " + n);
        else if (n < 2)
            return (n);

        int prev = 1;
        int cur = 1;

        for (int i = 2; i < n; i++) {
            int tmp = cur;
            cur = prev + cur;
            prev = tmp;
        }

        return (cur);
    }

    public static int fibonacciRec(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Invalid fibonacci num: " + n);
        else if (n < 2)
            return (n);

        return (fibonacciRec(n - 1) + fibonacciRec(n - 2));
    }


    public static <T> LinkNode removeValue (LinkNode start, T value) {
        LinkNode ret = null;
        LinkNode cur = ret;

        LinkNode prev = null;
        while (cur != null) {

            if (cur.getValue().equals(value)) {

                LinkNode next = cur.getNext();
                if (prev != null)
                    prev.setNext(next);
            }
            else {
                if (ret == null)
                    ret = cur;
                prev = cur;
            }
            cur = cur.getNext();
        }
        return (ret);
    }

    /**
     *
     * @param index of column header
     * @return Excel column header
     */
    public static String    excelColumn (int index) {
        /**
         * 1 -> A
         * 2 -> B
         * 26 -> Z
         * 27 -> AA
         * 28 -> AB
         *
         * 26 + 26 -> AZ
         * 26 * 2 + 1 -> BA
         * 26 * 26 + 3 * 26 + 4 -> ACD
         */

        String ret = "";
        int curval = index;
        int counter = 1;
        while (curval > 0) {

            int remainder = curval % 26;
            if (remainder > 0) {
                ret += String.valueOf('A' + (remainder - 1));
            }
            // remove remainder
            // then divide by power of 26
            curval /= Math.pow(26, counter++);
        }

        return (StringUtils.reverse(ret));
    }


    // find the max difference between successive elements in sorted form, but computed in linear time & space.
    // All ints are non-negative
    public static int       maximumGap (List<Integer> numbers) {
        return (0);
    }

    // compares version numbers of the form 1.0.0
    public static int       compareVersions (String v1, String v2) {
         return (0); // TODO
    }

    /**
     *
     * @param numbers
     * @return A peak element is higher than its neighbors. Returns first one.
     */
    public static int       findPeakElement (List<Integer> numbers) {
        return (0); // TODO
    }

    public static boolean   safeEquals (String s1, String s2) {
        if (s1 == null)
            return (s2 == null);
        else if (s2 == null)
            return (s1 == null);
        return (s1.equals(s2));
    }

    public static void swap (List list, int firstIdx, int secondIdx) {
        Object tmp = list.get(firstIdx);

        list.set(firstIdx, list.get(secondIdx));

        list.set(secondIdx, tmp);
    }

    public static void swap (Object [] list, int firstIdx, int secondIdx) {
        Object tmp = list [firstIdx];

        list [firstIdx] = list [secondIdx];

        list [secondIdx] = tmp;
    }
}
