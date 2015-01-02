package alg.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathUtils {

    /**
     * Compute the value of an expression like: "3 + (3 * (4 / (5 - 2))) * -1.2"
     * @param expression
     * @return
     */
    public static double evaluateArithmeticExpression (String expression) throws Exception {

        if (expression == null)
            return (Double.NaN);

        String trimmed = expression.trim();

        // TODO: finish
        int state = 0;
        int tokenStartIdx = 0;
        String operation = null;
        Double firstOperand = null;
        Double secondOperand = null;

        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);

            // possible states:
            // initial = 0
            // parsing a number = 1

            // parsing an operator = 2
            // computing a sub-result   ?

            switch (state) {
                case 0: // initial state
                    if ((c >= '0' && c <= '9') || c == '-') {
                        tokenStartIdx = i;
                        state = 1; // parsing a number, possibly negative
                    }
                    else if (c == ' ') {
                        // nothing to do
                    }
                    else if (c == '(') {
                        double num =  evaluateArithmeticExpression(trimmed.substring(i + 1));
                        // TODO
                    }
                    else
                        throw new Exception("Syntax error in " + trimmed + " at position " + i);
                    break;

                case 1: // parsing a number
                    if (c == ' ') {
                        double num = atod(trimmed.substring(tokenStartIdx, i));
                        if (operation != null) {
                            if (firstOperand == null) {
                                firstOperand = num;
                                state = 2;
                            }
                            else if (secondOperand == null) {
                                secondOperand = num;
                                // TODO the operation

                                Double result = 0d;
                                switch (operation.charAt(0)) {
                                    case '+':
                                        result = firstOperand + secondOperand;
                                        break;
                                }
                                operation = null;
                                state = 0;
                            }
                        }

                    }
                    else if (c == '(') {
                        throw new Exception("Syntax error in " + trimmed + " at position " + i + " unbalanced parentheses");

                    }
                    else if (c == ')') {
                        return (atod(trimmed.substring(tokenStartIdx, i)));
                    }
                    break;

                case 2:
                    if (c == '/' || c == '+' || c == '*' || c == '-') {
                        operation = String.valueOf(c);
                        state = 0;
                    }
                    else
                        throw new Exception("Syntax error in " + trimmed + " at position " + i + " expecting operator");
                    break;

            }
        }

        return (Double.NaN); // TODO
    }

    /**
     * All primes up to a certain number
     * random # generators
     */

   public static List<Integer> sieveOfEratosthenes (int allPrimesUpTo) {
       boolean [] possiblePrimes = new boolean[allPrimesUpTo];

       possiblePrimes [0] = true;
       possiblePrimes [1] = true;

       ArrayList<Integer> primes = new ArrayList<Integer>();
       for (int i = 2; i < allPrimesUpTo; i++) {

           if (!possiblePrimes[i]) {
               primes.add(i);
               possiblePrimes[i] = true;
               int total = i + i;
               while (total < possiblePrimes.length) {

                   possiblePrimes [total] = true;
                   total += i;
               }
           }
       }

       return (primes);
    }

    public static int     generateRandom (int max) {
        return (0); // TODO
    }

    public static int     generateRandom (int max, int seed) {
        return (0); // TODO
    }


    /**
     * TODO:
     *  solving linear equations
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

    /**
     * Catalan number = (1 / (n + 1)) * (2n choose n)
     * @param n
     * @return
     */
    public static long   nthCatalan (int n) {

        long twoNChooseN = nChooseK(2 * n, n);

        return ((long) twoNChooseN / (n + 1));
    }

    /**
     *  n! / k! (n - k)!
     * @param n
     * @param k
     * @return
     */
    public static long   nChooseK (int n, int k) {
        /**
         * 8 7 6 5 4 3 2 1 / (3 2 1) (5 4 3 2 1)
         */

        long nMinusK = n - k;
        long nFactorialNeeded = 1;

        if (nMinusK >= k) {
            for (long i = nMinusK + 1; i <= n; i++) {
                nFactorialNeeded *= i;
            }
            for (long i = 1; i <= k; i++) {
                nFactorialNeeded /= i;
            }
        }
        else {
            for (long i = k + 1; i <= n; i++) {
                nFactorialNeeded *= i;
            }
            for (long i = 1; i <= nMinusK; i++) {
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
        if (asciiNum == null || asciiNum.length() == 0)
            throw new RuntimeException("atoi undefined for: " + asciiNum);

        int total = 0;
        boolean negate = false;
        for (int i = 0; i < asciiNum.length(); i++) {
            char c = asciiNum.charAt(i);
            if (c >= '0' && c <= '9') {
                 total += Math.pow(10, asciiNum.length() - (1 + i)) * -('0' - c);
            }
            else if (c == '-' && i == 0) {
                negate = true;
            }
            else
                throw new RuntimeException("atoi undefined for " + asciiNum);
        }

        return (negate ? -total : total);
    }

    public static double atod (String asciiNum) {
        if (asciiNum == null || asciiNum.length() == 0)
            throw new RuntimeException("atod undefined for: " + asciiNum);

        double total = 0;
        boolean negate = false;
        int decIdx = asciiNum.indexOf('.');
        if (decIdx == -1)
            decIdx = asciiNum.length();
        for (int i = 0; i < decIdx; i++) {
            char c = asciiNum.charAt(i);
            if (c >= '0' && c <= '9') {
                total += Math.pow(10, asciiNum.length() - (1 + i)) * (c - '0');
            }
            else if (c == '-' && i == 0) {
                negate = true;
            }
            else
                throw new RuntimeException("atod undefined for " + asciiNum);
        }

        if (decIdx + 1 < asciiNum.length()) {
            for (int i = decIdx + 1; i < asciiNum.length(); i++) {
                char c = asciiNum.charAt(i);
                if (c >= '0' && c <= '9') {
                    int power = decIdx - i;
                    total += Math.pow(10, power) * (c - '0');
                }
                else
                    throw new RuntimeException("atod undefined for " + asciiNum);
            }
        }
        return (negate ? -total : total);
    }

    public static String itoa (int num) {
        if (num == 0)
            return ("0");

        boolean negate = false;
        if (num < 0) {
            negate = true;
            num =- num;
        }
        String ret = "";
        while (num > 0) {

            int remainder = num % 10;
            ret = remainder + ret;
            num /= 10;
        }
        return (negate ? "-" + ret : ret);
    }

    /**
     * greatest common divisor
     * @param num1
     * @param num2
     * @return    greatest common divisor
     */
    public static int gcd (int num1, int num2) {
        if (num2 == 0)
            return (num1);
        return (gcd(num2, num1 % num2));
    }

    /**
     * Least common multiple
     * @param num1
     * @param num2
     * @return
     */
    public static int lcm (int num1, int num2) {
         return (num1 * num2 / gcd(num1, num2));
    }
}
