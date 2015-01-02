package test.alg.math;

import alg.math.MathUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/2/15
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class MathTest {

    public static void main (String [] args) {

        long value = MathUtils.nthCatalan(1);
        System.out.println(value);
        value = MathUtils.nthCatalan(3);
        System.out.println(value);
        value = MathUtils.nthCatalan(5);
        System.out.println(value);
        value = MathUtils.nthCatalan(10);
        System.out.println(value);
    }
}
