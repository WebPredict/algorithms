package alg.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathUtils {

    /**
     * primes
     * random # generators
     */

   public static int [] sieveOfEratosthenes (int max) {
        return (null);
    }

    public static int     generateRandom (int max) {
        return (0); // TODO
    }

    public static int     generateRandom (int max, int seed) {
        return (0); // TODO
    }


    /**
     * line intersection
     *  solving linear equations
     *
     *
     */


    public static List<Integer> primeFactors (int n) {
        double sqrt = Math.sqrt((double)n);
        int ceil = (int)Math.ceil(sqrt);
        boolean foundFactors = false;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (int i = 2; i < ceil; i++) {
             int remainder = n % i;
            if (remainder == 0) {
                foundFactors = true;

                List<Integer> recFactors = primeFactors(n / i);
                if (recFactors.size() > 0) {
                    ret.addAll(recFactors);
                }
                recFactors = primeFactors(i);
                if (recFactors.size() > 0) {
                    ret.addAll(recFactors);
                }
            }
        }
        if (!foundFactors && n > 1)
            ret.add(n);
        return (ret);
    }

    public static int     factorial (int n) {
        int total = 1;
        for (int i = 2; i <= n; i++) {
            total *= i;
        }
        return (total);
    }

    public static int   nthCatalan (int n) {
        return (0); // TODO
    }

    public static int   nChooseK (int n, int k) {
        // n! / k! (n - k)!

        /**
         * 8 7 6 5 4 3 2 1 / (3 2 1) (5 4 3 2 1)
         */

        int nMinusK = n - k;
        int nFactorialNeeded = 1;

        if (nMinusK >= k) {
            for (int i = nMinusK + 1; i <= n; i++) {
                nFactorialNeeded *= i;
            }
            for (int i = 1; i <= k; i++) {
                nFactorialNeeded /= i;
            }
        }
        else {
            for (int i = k + 1; i <= n; i++) {
                nFactorialNeeded *= i;
            }
            for (int i = 1; i <= nMinusK; i++) {
                nFactorialNeeded /= i;
            }
        }
        return (nFactorialNeeded);
    }

    public static double [] quadraticSolve (double a, double b, double y) {
        double first = -b + Math.sqrt(b * b - 4 * a * y) / 2d * a;
        double second = -b - Math.sqrt(b * b - 4 * a * y) / 2d * a;
        return (new double[] {a, b});
    }

    public static int min (int [] list) {
        Integer least = null;

        for (int i = 0; i < list.length; i++) {
            if (least == null || list [i] < least)
                least = list [i];
        }

        return (least);
    }

    public static int max (int [] list) {
        Integer most = null;

        for (int i = 0; i < list.length; i++) {
            if (most == null || list [i] > most)
                most = list [i];
        }

        return (most);
    }

    public static String    fractionToDecimal (int numerator, int denominator) {
        return (null); // TODO
    }

    // elementary school multiplication algorithm on arbitrarily long numbers in string form
    public static String    multiplication (String a, String b) {

        return (null); // TODO
    }

    public static String    longDivision (int divisor, int dividend) {
        // returns an answer like: 5 / 7 = "1 remainder 2"

        return (null); // TODO
    }

    public static List<List<Integer>>  pascalsTriangle (int numRows) {

        return (null); // TODO
    }

    public static int  atoi (String asciiNum) {
        return (0); // TODO
    }


    public static String itoa (int num) {
        return (null); // TODO
    }
}
