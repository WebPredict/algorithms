package test.alg.misc;

import alg.misc.MiscUtils;
import alg.util.LinkNode;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiscUtilsTest {

    public static void main (String [] args) {

        for (int i = 0; i < 20; i++) {
            System.out.println(MiscUtils.fibonacciRec(i));
        }

        String col1 = MiscUtils.excelColumn(1);
        String col5 = MiscUtils.excelColumn(5);
        String col26 = MiscUtils.excelColumn(26);
        String col27 = MiscUtils.excelColumn(27);
        String az = MiscUtils.excelColumn(26 + 26);
        String ba = MiscUtils.excelColumn(26 * 2 + 1);
        String ec = MiscUtils.excelColumn(26 * 5 + 3);


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
