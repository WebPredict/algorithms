package test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/29/14
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtils {

    public static List<Integer> randomIntList(int maxSize, int range) {
        Random  random = new Random();
        int     size = random.nextInt(maxSize);

        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(range));
        }
        return (list);
    }
}
