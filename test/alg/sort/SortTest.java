package test.alg.sort;

import alg.sort.SortUtils;
import test.util.TestUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/29/14
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SortTest {

    public static void  testHeapsort () {

        List<Integer> intList = TestUtils.randomIntList(10, 100);
        int origSize = intList.size();
        System.out.println("Before: " + intList);
        SortUtils.heapSort(intList);

        System.out.println("After: " + intList);

        assert(intList.size() == origSize);
        for (int i = 0; i < origSize - 2; i++) {
            assert(intList.get(i) <= intList.get(i + 1));
        }
    }

    public static void main (String [] args) throws Exception {
        testHeapsort();
    }
}
