package test.alg.misc;

import alg.arrays.ArrayUtils;
import alg.arrays.Interval;
import alg.graphics.ImageMap;
import alg.graphics.ImageUtils;
import alg.math.MathUtils;
import alg.math.finance.StockUtils;
import alg.misc.MiscUtils;
import alg.misc.StackWithMin;
import alg.sequence.SequenceUtils;
import alg.sort.SortUtils;
import alg.strings.StringUtils;
import alg.trees.TreeUtils;
import alg.util.BinaryNode;
import alg.util.LinkNode;
import alg.words.WordUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiscUtilsTest {

    public static void main (String [] args) throws Exception {

        ArrayUtils.generateCombinations(10, 3);

        int distance = SequenceUtils.levenshteinDistance("this", "thing");

        distance = SequenceUtils.levenshteinDistance("this", "this");

        distance = SequenceUtils.levenshteinDistance("this", "kiss");

        distance = SequenceUtils.levenshteinDistance("something", "");


        ImageMap map = new ImageMap(5, 5, 2);
        map.setColorAt(2, 2, 1);
        map.setColorAt(3, 3, 1);
        map.setColorAt(3, 1, 1);
        map.setColorAt(4, 2, 1);

        System.out.println(map);

        //map.dilate();
        System.out.println(map);

        //map.erode();
        System.out.println(map);

        //map.convertToManhattanDistance();
        System.out.println(map);

        ImageMap corrected = ImageUtils.correctSnow(map, 1);

        System.out.println(corrected);

        double value = MathUtils.evaluateArithmeticExpression("2 + 3 * 3 * 3");

        List<WordUtils.WordFrequency> frequencies = WordUtils.getWordFrequencies("This is an example of some great sample text that is my finest work. I hope it is some good stuff.", 5, true, true);

        StackWithMin<Integer> stackWithMin = new StackWithMin<Integer>();
        stackWithMin.push(3);
        int min = stackWithMin.min();

        stackWithMin.push(4);
        min = stackWithMin.min();
        stackWithMin.push(5);
        stackWithMin.push(1);
        min = stackWithMin.min();
        stackWithMin.pop();
        min = stackWithMin.min();

        value = MathUtils.evaluateArithmeticExpression("2.1 * (3 + ((4 - 2) + 1))");

        String roman = WordUtils.toRomanNumeral(3434);

        int fromRoman = WordUtils.fromRomanNumeral(roman);

        int [] values = {100,3,2,1};        //{1, 3, 2, 12, 10, 231, 112};

        int maxGap = MiscUtils.maximumGap(values);


        value = MathUtils.evaluateArithmeticExpression("2");

        value = MathUtils.evaluateArithmeticExpression("(3)");

        value = MathUtils.evaluateArithmeticExpression("2 * (3 + 8.6) / -4");

        value = MathUtils.evaluateArithmeticExpression("2 * 3 * 80");

        value = MathUtils.evaluateArithmeticExpression("2 - 3 - 4");

        value = MathUtils.evaluateArithmeticExpression("-2");

        value = MathUtils.evaluateArithmeticExpression("2.1 * (3 + ((4 - 2) + 1))");

        int     len = ArrayUtils.removeElement(values, 1);

        int max = StockUtils.maxProfitWithOneTransaction(new int[]{1, 2, 3, 2});
        max = StockUtils.maxProfitWithOneTransaction(new int [] {1, 2, 3});
        max = StockUtils.maxProfitWithOneTransaction(new int [] {1});
        max = StockUtils.maxProfitWithOneTransaction(new int [] {3, 2, 1});
        max = StockUtils.maxProfitWithOneTransaction(new int [] {1, 2, 3, 2});
        max = StockUtils.maxProfitWithMaxTransactions(new int[]{2, 1, 5});

        len = ArrayUtils.removeDuplicatesFromSortedArray(new int[] {1, 1, 1, 2, 2, 3, 6, 7, 7});
        List<List<Integer>>    perms = ArrayUtils.listPermutations(new int[] {1, 2, 3, 4});

        System.out.println(perms);

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
