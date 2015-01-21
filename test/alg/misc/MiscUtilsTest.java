package test.alg.misc;

import alg.arrays.ArrayUtils;
import alg.arrays.Interval;
import alg.misc.MiscUtils;
import alg.strings.StringUtils;
import alg.trees.TreeUtils;
import alg.util.BinaryNode;
import alg.util.LinkNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiscUtilsTest {

    public static void main (String [] args) {

        int [][]    matrix = ArrayUtils.generateSpiralMatrix(1);

        String simplified = MiscUtils.simplifyUnixPath("/."); //("/a/.//b");
        simplified = MiscUtils.simplifyUnixPath("..//b");

        simplified = MiscUtils.simplifyUnixPath("/a/.//b");
        simplified = MiscUtils.simplifyUnixPath("/home/user//path/../other/");

        simplified = MiscUtils.simplifyUnixPath("/..");

        simplified = MiscUtils.simplifyUnixPath("/../");
        simplified = MiscUtils.simplifyUnixPath("/...");

        MiscUtils.fizzbuzz();

        LinkNode list1 = generateList(5);
        BinaryNode tree = TreeUtils.sortedListToBST(list1);

        list1 = generateList(4);
        tree = TreeUtils.sortedListToBST(list1);

        list1 = generateList(1);
        tree = TreeUtils.sortedListToBST(list1);

        list1 = generateList(10);
        tree = TreeUtils.sortedListToBST(list1);

        List<Interval> intervals = ArrayUtils.merge("[1 3],[2 6],[8 10],[15 18]");

        intervals = ArrayUtils.merge("[1 4],[0 4]");

        intervals = ArrayUtils.merge("[1 3]");

        intervals = ArrayUtils.merge("[1 3],[2 6]");

        int [] A =  new int[] {3, 4, 5, 0, 0};
        ArrayUtils.merge(A, 3, new int[] {4, 7}, 2);

        A = new int[] {3, 4, 5, 0};
        ArrayUtils.merge(A, 3, new int[] {1}, 1);
        A =  new int[] {4, 0, 0, 0, 0, 0};
        ArrayUtils.merge(A, 1, new int[] {1, 2, 3, 5, 6}, 5);
        A = new int[] {0, 0};
        ArrayUtils.merge(A, 0, new int[] {4, 7}, 2);

        int colIdx = MiscUtils.excelColumnIndex("A");
        colIdx = MiscUtils.excelColumnIndex("E");
        colIdx = MiscUtils.excelColumnIndex("Z");
        colIdx = MiscUtils.excelColumnIndex("AA");
        colIdx = MiscUtils.excelColumnIndex("AZ");
        colIdx = MiscUtils.excelColumnIndex("BB");
        colIdx = MiscUtils.excelColumnIndex("BCE");

        boolean palin = StringUtils.isPalindromeOnlyAlphanumeric("A man, a plan, a canal: Panama");
        palin = StringUtils.isPalindromeOnlyAlphanumeric("A man, a plan, a canal: yeah Panama");

        for (int i = 0; i < 20; i++) {
            System.out.println(MiscUtils.fibonacciRec(i));
        }

        String col1 = MiscUtils.excelColumnTitle(1);
        String col5 = MiscUtils.excelColumnTitle(5);
        String col26 = MiscUtils.excelColumnTitle(26);
        String col27 = MiscUtils.excelColumnTitle(27);
        String az = MiscUtils.excelColumnTitle(26 + 26);
        String ba = MiscUtils.excelColumnTitle(26 * 2 + 1);
        String ec = MiscUtils.excelColumnTitle(26 * 5 + 3);


        LinkNode<Integer>   links = generateList(10);
        System.out.println(links);

        LinkNode<Integer>   threeFive = new LinkNode<Integer>();
        threeFive.setValue(3);
        LinkNode<Integer>   five = new LinkNode<Integer>();
        five.setValue(5);
        threeFive.setNext(five);

        LinkNode<Integer>   partialReversed = MiscUtils.reverseLinkedListBetween(threeFive, 1, 2);
        LinkNode<Integer>   reversed = MiscUtils.reverseLinkedList(links);

        System.out.println(reversed);

        System.out.println();
    }

    public static LinkNode<Integer> generateList (int size) {
        if (size == 0)
            return (null);

        LinkNode<Integer> root = new LinkNode<Integer>();
        root.setValue(0);

        int ctr = 1;
        LinkNode<Integer> prev = root;
        while (ctr < size) {
            LinkNode<Integer> next = new LinkNode<Integer>();
            next.setValue(ctr);

            if (prev != null)
                prev.setNext(next);
            prev = next;
            ctr++;
        }
        return (root);
    }
}
