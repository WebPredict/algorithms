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


        BinaryNode root = new BinaryNode();

        int minHeight = TreeUtils.minHeight(root);
        System.out.println(minHeight);

        //root.setLeft(new BinaryNode());
        //minHeight = TreeUtils.minHeight(root);
        //System.out.println(minHeight);

        root.setRight(new BinaryNode());
        minHeight = TreeUtils.minHeight(root);
        System.out.println(minHeight);

    }
}
