package alg.misc;

import alg.sort.SortUtils;
import alg.strings.StringUtils;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiscUtils {

    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int fibonacci(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Invalid fibonacci num: " + n);
        else if (n < 2)
            return (n);

        int prev = 1;
        int cur = 1;

        for (int i = 2; i < n; i++) {
            int tmp = cur;
            cur = prev + cur;
            prev = tmp;
        }

        return (cur);
    }

    public static int fibonacciRec(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Invalid fibonacci num: " + n);
        else if (n < 2)
            return (n);

        return (fibonacciRec(n - 1) + fibonacciRec(n - 2));

    }

    /**
     *
     * 1 -> A
     * 2 -> B
     * 26 -> Z
     * 27 -> AA
     * 28 -> AB
     *
     * 26 + 26 -> AZ
     * 26 * 2 + 1 -> BA
     * 26 * 2 + 26 - > BZ
     * 26 * 26 + 3 * 26 + 4 -> ACD
     * @param index of column header (1 based)
     * @return Excel column header
     */
    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(log n)")
    public static String    excelColumnTitle (int index) {
        String ret = "";
        int curval = index;
        while (true) {

            int remainder = (curval - 1) % 26;
            ret += String.valueOf((char)('A' + remainder));

            if (curval < 27)
                break;
            curval -= (remainder + 1);

            curval /= 26;
        }

        return (StringUtils.reverse(ret));
    }

    /**
     *
     * @param title of column header (1 based)
     * @return Excel column index
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int    excelColumnIndex (String title) {
        int ret = 0;

        for (int i = 0; i < title.length(); i++) {

            char c = title.charAt(i);
            ret += Math.pow(26, title.length() - (i + 1)) * ((c + 1) - 'A');
        }

        return (ret);
    }

    /**
     *    find the max difference between successive elements in sorted form, but computed in linear time & space.
     *
     * @param num   Non negative integers
     * @return
     */
    @InterestingAlgorithm(timeComplexity = "O(n * k)", spaceComplexity = "O(n)")
    public static int       maximumGap (int [] num) {
        if (num == null || num.length < 2)
            return (0);

        // This isn't really right for linear time/space, but close!
        SortUtils.radixSort(num);

        Integer max = null;

        for (int i = 0; i < num.length - 1; i++) {
            int diff = Math.abs(num [i + 1] - num [i]);
            if (max == null) {
                max = diff;
            }
            else if (max < diff) {
                max = diff;
            }
        }
        // Hard part is doing it without sorting i.e. in linear time

        /**
         * 3 5 10 9 2 1 8
         */
        return (max);
    }

    /**
     * 0.1 < 1.1 < 1.2 < 13.37
     * @param v1
     * @param v2
     * @return
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int       compareVersions (String v1, String v2) {

        int curV1Idx = 0;
        int curV2Idx = 0;
        int prevV1Idx = -1;
        int prevV2Idx = -1;

        String nextV1Part = null;
        String nextV2Part = null;
        while (true) {
            if (prevV1Idx == v1.length())
                nextV1Part = "0";
           else {
                curV1Idx = v1.indexOf('.', prevV1Idx + 1);
                nextV1Part = curV1Idx == -1 ? v1.substring(prevV1Idx + 1) : v1.substring(prevV1Idx + 1, curV1Idx);
            }

            if (prevV2Idx == v2.length())
                nextV2Part = "0";
            else {
                curV2Idx = v2.indexOf('.', prevV2Idx + 1);
                nextV2Part = curV2Idx == -1 ? v2.substring(prevV2Idx + 1) : v2.substring(prevV2Idx + 1, curV2Idx);
            }

            int firstCompare = Integer.valueOf(nextV1Part).compareTo(Integer.valueOf(nextV2Part));
            if (firstCompare != 0 || (curV1Idx == -1 && curV2Idx == -1))
                return (firstCompare);

            if (curV1Idx != -1)
                prevV1Idx = curV1Idx;
            else
                prevV1Idx = v1.length();

            if (curV2Idx != -1)
                prevV2Idx = curV2Idx;
            else
                prevV2Idx = v2.length();
        }
    }

    /**
     *
     * @param numbers
     * @return A peak element is higher than its neighbors. Returns first one or null if none.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static Integer       findPeakElement (List<Integer> numbers) {

        if (numbers == null)
            return (null);

        for (int i = 0; i < numbers.size(); i++) {

            if (i > 0 && numbers.get(i) > numbers.get(i - 1)) {
                if (i == numbers.size() - 1)
                    return (numbers.get(i));
                else if (numbers.get(i + 1) < numbers.get(i))
                    return (numbers.get(i));
            }
            else if (numbers.size() == 1)
                return (numbers.get(0));
        }

        return (null);
    }

    public static boolean   safeEquals (String s1, String s2) {
        if (s1 == null)
            return (s2 == null);
        else if (s2 == null)
            return (s1 == null);
        return (s1.equals(s2));
    }

    public static void swap (List list, int firstIdx, int secondIdx) {
        Object tmp = list.get(firstIdx);

        list.set(firstIdx, list.get(secondIdx));

        list.set(secondIdx, tmp);
    }

    public static void swap (Object [] list, int firstIdx, int secondIdx) {
        Object tmp = list [firstIdx];

        list [firstIdx] = list [secondIdx];

        list [secondIdx] = tmp;
    }

    /**
     * Removes redundant Unix path elements, e.g. duplicate slashes
     * @param path
     * @return
     */
    public static String simplifyUnixPath (String path) {
        if (path == null)
            return (null);

        String          ret = null;
        Stack<String>   directories = new Stack<String>();

        int             state = 0;
        int             startIdx = -1;
        boolean         sawRoot = false;
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);

            switch (c) {
                case '/':
                    if (directories.empty())
                        sawRoot = true;

                    if (state == 3) {
                        directories.push(path.substring(startIdx, i));
                        startIdx = -1;
                    }

                    state = 1;
                    break;

                case '.':
                    if (state == 2) {
                        if (directories.size() > 0) {
                            directories.pop();
                        }
                        else {
                            if (!sawRoot)
                                directories.push("..");
                            else {
                                // more to come?
                                if (i < path.length() - 1)
                                    directories.push("..");
                            }
                        }
                        state = 4; // ..
                    }
                    else {
                        // try to handle this odd case: "/..." = "/..."  - not sure this is even a valid case to support
                        if (state == 4 && directories.size() > 0) {
                            String adjust = directories.pop();
                            directories.push(adjust + ".");
                        }
                        state = 2;
                    }
                    break;

                default:
                    if (state != 3) {
                        startIdx = i;
                    }

                    if (i == path.length() - 1) {
                        directories.push(path.substring(startIdx, path.length()));
                    }
                    state = 3;
                    break;
            }
        }

        if (directories.empty() && sawRoot)
            return ("/"); // special case

        while (!directories.empty()) {
            if (ret == null) {
                ret = directories.pop();
            }
            else {
                ret = directories.pop() + "/" + ret;
            }
        }
        if (sawRoot)
            return (ret == null || ret.equals("..") ? "/" : "/" + ret);
        else
            return (ret);
    }


    /**
     * Jeff Atwood's legendary FizzBuzz. If you can't write this in 15 seconds or less you are bad, and should feel bad.
     */
    public static void fizzbuzz () {
        for (int i = 1; i <= 100; i++) {
            if (i % 15 == 0)
                System.out.println("fizzbuzz");
            else if (i % 5 == 0)
                System.out.println("buzz");
            else if (i % 3 == 0)
                System.out.println("fizz");
            else
                System.out.println(i);
        }
    }

    /**
     * All ways to climb stairs 1 or 2 steps at a time. Another interview classic
     * @param n
     * @return
     */
    public static int climbStairs (int n) {
        if (n == 0 || n == 1)
            return (1);

        int []  ways = new int[n];
        ways [0] = 1;
        ways [1] = 2;

        for (int i = 2; i < n; i++) {
            ways [i] = ways [i - 2] + ways [i - 1];
        }
        return (ways [n - 1]);
    }
}
