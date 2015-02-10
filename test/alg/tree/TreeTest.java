package test.alg.tree;

import alg.misc.MiscUtils;
import alg.trees.TreeUtils;
import alg.util.BinaryNode;
import alg.util.LinkNode;
import test.util.TreeTestUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/11/15
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class TreeTest {

    public static void main (String [] args) {

        LinkNode first = new LinkNode<Integer>();
        first.setValue(2);
        LinkNode second = new LinkNode<Integer>();
        second.setValue(1);

        LinkNode merged = MiscUtils.mergeTwoSortedLists(first, second);

        BinaryNode<Integer> integerBinaryNode = TreeTestUtils.createRandomBinaryIntTree(4, 0, 1000);

        List<List> levelOrder = TreeUtils.levelOrder(new BinaryNode<Integer>(null));


        BinaryNode root = new BinaryNode(1);

        int minHeight = TreeUtils.minHeight(root);
        System.out.println(minHeight);

        root.setLeft(new BinaryNode(2));
        minHeight = TreeUtils.minHeight(root);
        //System.out.println(minHeight);

        root.setRight(new BinaryNode(3));
        minHeight = TreeUtils.minHeight(root);
        System.out.println(minHeight);

    }
}
