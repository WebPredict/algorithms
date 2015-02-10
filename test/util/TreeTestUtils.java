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

    public static BinaryNode<Integer> createRandomBinaryIntTree (int depth, int start, int end) {
        if (depth == 0)
            return (null);
        int data = start + new Random().nextInt(end - start);
        BinaryNode<Integer> root = new BinaryNode<Integer>(data);

        root.setLeft(createRandomBinaryIntTree(depth -1, start, data));
        root.setRight(createRandomBinaryIntTree(depth -1, data, end));
        return (root);
    }
}
