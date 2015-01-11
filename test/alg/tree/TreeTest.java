package test.alg.tree;

import alg.trees.TreeUtils;
import alg.util.BinaryNode;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/11/15
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class TreeTest {

    public static void main (String [] args) {

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
