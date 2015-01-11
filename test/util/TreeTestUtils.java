package test.util;

import alg.util.BinaryNode;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class TreeTestUtils {

    public BinaryNode<Integer> createRandomIntTree (int depth, int start, int end) {
        if (depth == 0)
            return (null);
        int data = start + new Random().nextInt(end - start);
        BinaryNode<Integer> root = new BinaryNode<Integer>(data);

        root.setLeft(createRandomIntTree(depth -1, 0, data));
        root.setRight(createRandomIntTree(depth -1, data, end));
        return (root);
    }
}
