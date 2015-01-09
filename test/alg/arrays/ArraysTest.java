package test.alg.arrays;

import alg.arrays.ArrayUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/9/15
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArraysTest {

    public static void main (String [] args) {


        int [][]    testMatrix = new int [][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        List<Integer> spiralOrder = ArrayUtils.spiralOrder(testMatrix);

        for (int i = 0; i < spiralOrder.size(); i++)
            System.out.println(spiralOrder.get(i));

        testMatrix = new int [][] {{1}};
        spiralOrder = ArrayUtils.spiralOrder(testMatrix);

        for (int i = 0; i < spiralOrder.size(); i++)
            System.out.println(spiralOrder.get(i));

        testMatrix = new int [][] {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        spiralOrder = ArrayUtils.spiralOrder(testMatrix);

        for (int i = 0; i < spiralOrder.size(); i++)
            System.out.println(spiralOrder.get(i));

    }
}
