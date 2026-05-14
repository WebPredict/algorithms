package alg.stackproblems;

import alg.misc.InterestingAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Common stack-based interview patterns.
 */
public class StackUtils {

    /**
     * Returns true if the string has valid matching parentheses, brackets, and braces.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(n)")
    public static boolean isValidParentheses (String s) {
        if (s == null || s.length() == 0)
            return (true);

        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            else {
                if (stack.isEmpty())
                    return (false);

                char top = stack.pop();
                if (c == ')' && top != '(')
                    return (false);
                if (c == ']' && top != '[')
                    return (false);
                if (c == '}' && top != '{')
                    return (false);
            }
        }

        return (stack.isEmpty());
    }

    /**
     * Evaluates a reverse Polish notation expression.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(n)")
    public static int evalRPN (String [] tokens) {
        if (tokens == null || tokens.length == 0)
            throw new IllegalArgumentException("Invalid input");

        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens [i];

            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                int b = stack.pop();
                int a = stack.pop();

                if (token.equals("+"))
                    stack.push(a + b);
                else if (token.equals("-"))
                    stack.push(a - b);
                else if (token.equals("*"))
                    stack.push(a * b);
                else
                    stack.push(a / b);
            }
            else {
                stack.push(Integer.parseInt(token));
            }
        }

        return (stack.pop());
    }

    /**
     * For each day, returns the number of days until a warmer temperature.
     * Returns 0 if no warmer day exists.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(n)")
    public static int [] dailyTemperatures (int [] temperatures) {
        if (temperatures == null || temperatures.length == 0)
            return (new int [0]);

        int n = temperatures.length;
        int [] result = new int [n];
        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures [i] > temperatures [stack.peek()]) {
                int idx = stack.pop();
                result [idx] = i - idx;
            }
            stack.push(i);
        }

        return (result);
    }

    /**
     * Returns the area of the largest rectangle that can be formed in the histogram.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(n)")
    public static int largestRectangleInHistogram (int [] heights) {
        if (heights == null || heights.length == 0)
            return (0);

        Stack<Integer> stack = new Stack<Integer>();
        int maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            int curHeight = (i == heights.length) ? 0 : heights [i];

            while (!stack.isEmpty() && curHeight < heights [stack.peek()]) {
                int height = heights [stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                int area = height * width;
                if (area > maxArea)
                    maxArea = area;
            }

            stack.push(i);
        }

        return (maxArea);
    }

    /**
     * A stack that supports push, pop, top, and getMin all in O(1) time.
     */
    public static class MinStack {

        private List<Integer> data;
        private List<Integer> mins;

        public MinStack () {
            data = new ArrayList<Integer>();
            mins = new ArrayList<Integer>();
        }

        @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
        public void push (int val) {
            data.add(val);
            if (mins.isEmpty() || val <= mins.get(mins.size() - 1))
                mins.add(val);
            else
                mins.add(mins.get(mins.size() - 1));
        }

        @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
        public int pop () {
            if (data.isEmpty())
                throw new RuntimeException("Empty stack");

            int val = data.remove(data.size() - 1);
            mins.remove(mins.size() - 1);
            return (val);
        }

        @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
        public int top () {
            if (data.isEmpty())
                throw new RuntimeException("Empty stack");

            return (data.get(data.size() - 1));
        }

        @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
        public int getMin () {
            if (mins.isEmpty())
                throw new RuntimeException("Empty stack");

            return (mins.get(mins.size() - 1));
        }
    }
}
