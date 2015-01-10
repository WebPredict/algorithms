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

    @InterestingAlgorithm
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


    /**
     * Removes all nodes in a linked list with a particular value
     * @param start
     * @param value
     * @param <T>
     * @return
     */
    @InterestingAlgorithm
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
     * Returns new head of list
     * @param root
     * @return
     */
    @InterestingAlgorithm
    public static LinkNode reverseLinkedList (LinkNode root) {

        if (root == null)
            return (null);

        LinkNode next = root.getNext();

        /**
         * A -> B -> C -> D
         *
         * next is B
         * root is A
         * We need:
         * B -> A, save C
         *
         * tmp = next -> next
         * next -> next = root
         * next = tmp
         */
        root.setNext(null);
        while (next != null) {
            LinkNode tmp = next.getNext();

            next.setNext(root);
            root = next;

            next = tmp;
        }
        return (root);
    }

    @InterestingAlgorithm
    public static LinkNode reverseLinkedListBetween (LinkNode root, int fromIdxOneBased, int toIdxIncl) {
        if (root == null)
            return (null);

        LinkNode next = root;

        int ctr = 1;
        LinkNode beforeNext = null;
        while (ctr < fromIdxOneBased) {
            ctr++;
            beforeNext = next;
            next = next.getNext();
        }

        LinkNode afterNewSection = next;
        LinkNode newHead = next;
        while (ctr <= toIdxIncl) {
            LinkNode tmp = next.getNext();

            next.setNext(newHead);
            newHead = next;

            next = tmp;
            ctr++;
        }
        afterNewSection.setNext(next);
        if (beforeNext == null)
            return (newHead);

        beforeNext.setNext(newHead);

        return (root);
    }

    /**
     * Returns beginning of cycle or null if none.
     * @param root
     * @return
     */
    @InterestingAlgorithm
    public static LinkNode   detectCycle (LinkNode root) {
        LinkNode cur = root;
        LinkNode twiceCur = root;

        while (twiceCur != null) {
            cur = cur.getNext();

            twiceCur = twiceCur.getNext();
            if (twiceCur != null)
                twiceCur = twiceCur.getNext();

            if (twiceCur == cur)
                return (cur);
        }

        return (null);
    }

    /**
     * Returns intersecting node, or null if none.
     * @param root1
     * @param root2
     * @return
     */
    @InterestingAlgorithm
    public static LinkNode   detectIntersection (LinkNode root1, LinkNode root2) {

        int lengthFirst = length(root1);
        int lengthSecond = length(root2);

        int diff = lengthFirst - lengthSecond;

        LinkNode first = root1;
        LinkNode second = root2;
        if (diff > 0) {
            while (diff-- > 0) {
                 first = first.getNext();
            }
        }
        else if (diff < 0) {
            while (diff++ < 0) {
                second = second.getNext();
            }
        }

        while (first == null || first != second) {
            first = first.getNext();
            second = second.getNext();
        }
        return (first);
    }

    /**
     * Returns length of list
     * @param root
     * @return
     */
    @InterestingAlgorithm
    public static int length (LinkNode root) {
        int length = 0;

        LinkNode next = root;
        while (next != null) {
            length++;
            next = next.getNext();
        }

        return (length);
    }

    /**
     *
     * @param index of column header
     * @return Excel column header
     */
    @InterestingAlgorithm
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
         * 26 * 2 + 26 - > BZ
         * 26 * 26 + 3 * 26 + 4 -> ACD
         */
        String ret = "";
        int curval = index;
        while (true) {

            int remainder = (curval - 1) % 26;
            ret += String.valueOf((char)('A' + remainder));

            if (curval < 27)
                break;
            curval -= (remainder + 1);

            curval /= 26;
        }

        return (StringUtils.reverse(ret));
    }

    /**
     *    find the max difference between successive elements in sorted form, but computed in linear time & space.
     *
     * @param unsortedNonNegIntegers   Non negative integers
     * @return
     */
    @InterestingAlgorithm
    public static int       maximumGap (List<Integer> unsortedNonNegIntegers) {
        // Hard part is doing it without sorting i.e. in linear time

        return (0);
    }

    // compares version numbers of the form 1.0.0
    @InterestingAlgorithm
    public static int       compareVersions (String v1, String v2) {

        int v1FirstDotIdx = v1.indexOf('.');
        int v1NextDotIdx = v1.indexOf('.', v1FirstDotIdx + 1);
        int v2FirstDotIdx = v2.indexOf('.');
        int v2NextDotIdx = v2.indexOf('.', v2FirstDotIdx + 1);

        int firstCompare = Integer.valueOf(v1.substring(0, v1FirstDotIdx)).compareTo(Integer.valueOf(v2.substring(0, v2FirstDotIdx)));
        if (firstCompare != 0)
            return (firstCompare);
        int secondCompare = Integer.valueOf(v1.substring(v1FirstDotIdx + 1, v1NextDotIdx)).compareTo(Integer.valueOf(v2.substring(v2FirstDotIdx + 1, v2NextDotIdx)));
        if (secondCompare != 0)
            return (secondCompare);
        int thirdCompare = Integer.valueOf(v1.substring(v1NextDotIdx + 1)).compareTo(Integer.valueOf(v2.substring(v1NextDotIdx + 1)));
        return (thirdCompare);
    }

    /**
     *
     * @param numbers
     * @return A peak element is higher than its neighbors. Returns first one or null if none.
     */
    @InterestingAlgorithm
    public static Integer       findPeakElement (List<Integer> numbers) {

        if (numbers == null)
            return (null);

        for (int i = 0; i < numbers.size(); i++) {

            if (i > 0 && numbers.get(i) > numbers.get(i - 1)) {
                if (i == numbers.size() - 1)
                    return (numbers.get(i));
                else if (numbers.get(i + 1) < numbers.get(i))
                    return (numbers.get(i));
            }
            else if (numbers.size() == 1)
                return (numbers.get(0));
        }

        return (null);
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
