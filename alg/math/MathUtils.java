package alg.math;

import alg.arrays.Interval;
import alg.misc.InterestingAlgorithm;

import java.util.*;

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
    @InterestingAlgorithm
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
    @InterestingAlgorithm
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

    public static MarsenneTwister TWISTER = new MarsenneTwister();

    @InterestingAlgorithm
    public static int     generateRandom (int max) {
        int nextRandom = TWISTER.getNextRandom();
        return (nextRandom % max);
    }

    public static int     generateRandom (int max, int seed) {
        return (0); // TODO
    }


    /**
     * TODO:
     *  solving linear equations
     *
     *  solve for x: 3x + ((4 - x) * 5) + x = 5
     */


    /**
     *
     * @param n
     * @return  List of prime factors in n
     */
    @InterestingAlgorithm
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

    /**
     * Computes factorial of n
     * @param n
     * @return
     */
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
    @InterestingAlgorithm
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
    @InterestingAlgorithm
    public static long   nChooseK (int n, int k) {
        /**
         * 8 7 6 5 4 3 2 1 / (3 2 1) (5 4 3 2 1)
         */
        // TODO change to use string multiplication / division to support arbitrarily long numbers
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

    @InterestingAlgorithm
    public static double [] quadraticSolve (double a, double b, double y) {
        double first = -b + Math.sqrt(b * b - 4 * a * y) / 2d * a;
        double second = -b - Math.sqrt(b * b - 4 * a * y) / 2d * a;
        return (new double[] {a, b});
    }

    @InterestingAlgorithm
    public static double [] cubicSolve (double a, double b, double c, double y) {
        return (null); // TODO
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

    /**
     *
     * @param numerator
     * @param denominator
     * @param numDigits
     * @return  Decimal computed to numDigits. Sort of a poor man's BigInt, using strings
     */
    @InterestingAlgorithm
    public static String    fractionToDecimal (int numerator, int denominator, int numDigits) {

        int []  result = longDivisionWithRemainder(numerator, denominator);

        String answer = result [0] + ".";

        if (result [1] != 0) {

        }
        return (null); // TODO
    }

    /**
     *  elementary school multiplication algorithm on arbitrarily long numbers in string form
     * @param a
     * @param b
     * @return  a * b
     */
    @InterestingAlgorithm
    public static String    multiplication (String a, String b) {

        /**
         *       354
         *      x440
         *      ----
         *       000
         *     1416
         *    1416
         *    ------
         *    155760
         */
        String answer = "";

        String bTrimmed = b == null ? "" : b.trim();
        String aTrimmed = a == null ? "" : a.trim();

        if (bTrimmed.length() == 0 || aTrimmed.length() == 0)
            throw new RuntimeException("Undefined multiplication on empty strings");

        String [] toAdd = new String[bTrimmed.length()];

        for (int i = bTrimmed.length() - 1; i >=0; i--) {
            toAdd [i] = "";
            char c = bTrimmed.charAt(i);
            if (c < '0' || c > '9')
                throw new RuntimeException("Invalid positive integer: " + bTrimmed);

            int carry = 0;
            for (int j = aTrimmed.length() - 1; j >= 0; j--) {

                char ca = aTrimmed.charAt(j);
                if (c < '0' || c > '9')
                    throw new RuntimeException("Invalid positive integer: " + aTrimmed);

                int value = ((c - '0') * (ca - '0')) + carry;
                carry = 0;
                if (value > 9) {
                    // carry it
                    carry += value / 10;
                    value = value % 10;
                }

                toAdd [i] = String.valueOf(value) + toAdd [i];
            }
            if (carry > 0) {
                toAdd [i] = String.valueOf(carry) + toAdd [i];
            }

            for (int j = 0; j < (bTrimmed.length() - 1) - i; j++) {
                toAdd [j] += "0";
            }
        }

        // add all the strings in toAdd with string addition to avoid overflow
        int carry = 0;
        int maxLen = toAdd [toAdd.length - 1].length();
        for (int i = maxLen - 1; i >= 0; i--) {
            int total = 0;
            for (int j = 0; j < toAdd.length; j++) {

                if (j == 0) {
                    total = carry;
                    carry = 0;
                }

                String cur = toAdd [j];
                if (cur.length() <= i)
                    continue;

                char c = cur.charAt(i);
                int cVal = c - '0';
                total += cVal;
            }
            if (total > 9) {
                carry = total / 10;
                total = total % 10;
            }
            answer = String.valueOf(total) + answer;
        }

        if (carry > 0)
            answer = String.valueOf(carry) + answer;

        return (answer);
    }

    /**
     *
     * @param divisor
     * @param dividend
     * @return  an answer of the form: 5 / 7 = 1 remainder 2 = [1, 2]
     */
    @InterestingAlgorithm
    public static int []    longDivisionWithRemainder (int divisor, int dividend) {
        /**
         * example: 7 / 300 = 7 goes into 3? no, so try 30. 7 goes into 30? yes, 4 times. remainder
         * of 2. Bring down the 0. 7 goes into 20? yes, 2 times, remainder 6.
         */

        if (dividend == 0)
            throw new RuntimeException("division by zero (undefined)");

        String  dividendStr = String.valueOf(dividend);
        String  result = "";
        int     toDivide = 0;
        int     dividendStrIdx = 0;
        while (dividendStrIdx < dividendStr.length()) {
            char c = dividendStr.charAt(dividendStrIdx);
            if (c < '0' || c > '9')
                throw new RuntimeException("Invalid positive integer: " + dividend);

            int digit = c - '0';
            if (toDivide == 0)
                toDivide = digit;
            else {
                // shift it and add
                toDivide *= 10;
                toDivide += digit;
            }

            if (divisor <= toDivide) {
                int goesInto = toDivide / divisor;
                result += goesInto;

                int remainder = toDivide - divisor * goesInto;
                toDivide = remainder;
                dividendStrIdx += String.valueOf(goesInto).length();
            }
            else {
                // bring down the next digit
                dividendStrIdx++;
            }
        }

        return (new int[] {Integer.parseInt(result), toDivide});
    }

    /**
     *
     * @param divisor
     * @param dividend
     * @return  an answer of the form: 5 / 7 = 1 remainder 2 = [1, 2]
     */
    @InterestingAlgorithm
    public static String    longDivision (int divisor, int dividend, int maxDigits) {
        /**
         * example: 7 / 300 = 7 goes into 3? no, so try 30. 7 goes into 30? yes, 4 times. remainder
         * of 2. Bring down the 0. 7 goes into 20? yes, 2 times, remainder 6.
         */

        if (dividend == 0)
            throw new RuntimeException("division by zero (undefined)");

        String  dividendStr = String.valueOf(dividend);
        String  result = "";
        int     toDivide = 0;
        int     dividendStrIdx = 0;
        while (dividendStrIdx < dividendStr.length()) {
            char c = dividendStr.charAt(dividendStrIdx);
            if (c < '0' || c > '9')
                throw new RuntimeException("Invalid positive integer: " + dividend);

            int digit = c - '0';
            if (toDivide == 0)
                toDivide = digit;
            else {
                // shift it and add
                toDivide *= 10;
                toDivide += digit;
            }

            if (divisor <= toDivide) {
                int goesInto = toDivide / divisor;  // TODO remove this division
                result += goesInto;

                int remainder = toDivide - divisor * goesInto;
                toDivide = remainder;
                dividendStrIdx += String.valueOf(goesInto).length();
            }
            else {
                // bring down the next digit
                dividendStrIdx++;
            }
        }

        if (toDivide > 0) {
            result += ".";

            int digits = 0;
            while (digits < maxDigits) {

                int extra = 0;
                while (toDivide < divisor) {
                    toDivide *= 10; // TODO take care for overflow here
                    extra++;
                }
                int nextValue = toDivide / divisor; // TODO remove division
                result += nextValue;
                toDivide = toDivide - divisor * nextValue;
                if (toDivide == 0)
                    break;

                digits += extra;
            }
        }

        return (result);
    }

    @InterestingAlgorithm
    public static List<List<Integer>>  pascalsTriangle (int numRows) {
        if (numRows == 0)
            return (new ArrayList<List<Integer>>());

        List<List<Integer>> triangle = new ArrayList<List<Integer>>();
        ArrayList<Integer> firstRow = new ArrayList<Integer>();
        firstRow.add(1);
        triangle.add(firstRow);

        for (int i = 1; i < numRows; i++) {

            ArrayList<Integer> row = new ArrayList<Integer>();

            List<Integer> previousRow = triangle.get(i - 1);

            for (int j = 0; j < previousRow.size() + 1; j++) {
                int value;
                if (j == 0)
                    value = 1;
                else if (j == previousRow.size())
                    value = previousRow.get(j - 1);
                else
                    value = previousRow.get(j - 1) + previousRow.get(j);
                row.add(value);
            }
            triangle.add(row);
        }
        return (triangle);
    }

    /**
     *
     * @param k 1st row is row 0
     * @return
     */
    @InterestingAlgorithm
    public static List<Integer>  getRowInPascalsTriangle (int k) {
        ArrayList<Integer> previousRow = new ArrayList<Integer>();
        previousRow.add(1);

        for (int i = 0; i < k; i++) {
            ArrayList<Integer> row = new ArrayList<Integer>();

            for (int j = 0; j < previousRow.size() + 1; j++) {
                int value;
                if (j == 0)
                    value = 1;
                else if (j == previousRow.size())
                    value = previousRow.get(j - 1);
                else
                    value = previousRow.get(j - 1) + previousRow.get(j);
                row.add(value);
            }
            previousRow = row;
        }
        return (previousRow);
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public static int frogJumps(int X, int Y, int D) {
        int diff = Y - X;

        int minSteps = (int)Math.ceil((float)diff / (float)D);
        return (minSteps);
    }

    @InterestingAlgorithm
    public static double angleBetweenVectors2D (Vector v1, Vector v2) {
        double cross = v1.cross2d(v2);
        double normV1 = v1.norm();
        double normV2 = v2.norm();

        double sinAngleBetween = cross / (normV1 * normV2);
        return (Math.acos(sinAngleBetween));
    }

    /**
     *
     * @param asciiNum
     * @return
     */
    @InterestingAlgorithm
    public static int  atoi (String str) {
        if (str == null)
            return (0);

        String trimmed = str.trim();
        if (trimmed.length() == 0)
            return (0);

        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (c < '0' || c > '9') {
                if (c != '-' && c != '+') {
                    trimmed = trimmed.substring(0, i);
                    break; // cut it off at invalid char
                }
            }
        }

        long total = 0;
        boolean negate = false;
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (c >= '0' && c <= '9') {
                total += Math.pow(10, trimmed.length() - (1 + i)) * -('0' - c);
            }
            else if (i == 0) {
                if (c == '-')
                    negate = true;
                else if (c != '+') {
                    break;
                }
            }
            else
                break; // syntax error
        }

        if (negate && (int)total == Integer.MIN_VALUE)
            return (Integer.MIN_VALUE);
        return (int)(negate ? -total : total);
    }

    @InterestingAlgorithm
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

    @InterestingAlgorithm
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
    @InterestingAlgorithm
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
    @InterestingAlgorithm
    public static int lcm (int num1, int num2) {
         return (num1 * num2 / gcd(num1, num2));
    }

    public static boolean closeEnough (double d1, double d2) {
        return (Math.abs(d1 - d2) <= epsilon);
    }

    public static double epsilon = 0.0000001d;

}
