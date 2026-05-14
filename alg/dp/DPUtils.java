package alg.dp;

import alg.misc.InterestingAlgorithm;

/**
 * Classic dynamic programming algorithm implementations.
 */
public class DPUtils {

    /**
     * 0/1 Knapsack - each item can be used at most once.
     * @param weights   item weights
     * @param values    item values
     * @param capacity  knapsack capacity
     * @return maximum value achievable
     */
    @InterestingAlgorithm(timeComplexity = "O(n * capacity)", spaceComplexity = "O(n * capacity)")
    public static int knapsack01 (int [] weights, int [] values, int capacity) {
        if (weights == null || values == null || weights.length == 0 || capacity <= 0)
            return (0);

        int n = weights.length;
        int [][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp [i][w] = dp [i - 1][w];
                if (weights [i - 1] <= w) {
                    int include = dp [i - 1][w - weights [i - 1]] + values [i - 1];
                    if (include > dp [i][w])
                        dp [i][w] = include;
                }
            }
        }

        return (dp [n][capacity]);
    }

    /**
     * Unbounded Knapsack - each item can be used unlimited times.
     * @param weights   item weights
     * @param values    item values
     * @param capacity  knapsack capacity
     * @return maximum value achievable
     */
    @InterestingAlgorithm(timeComplexity = "O(n * capacity)", spaceComplexity = "O(capacity)")
    public static int knapsackUnbounded (int [] weights, int [] values, int capacity) {
        if (weights == null || values == null || weights.length == 0 || capacity <= 0)
            return (0);

        int [] dp = new int[capacity + 1];

        for (int w = 1; w <= capacity; w++) {
            for (int i = 0; i < weights.length; i++) {
                if (weights [i] <= w) {
                    int candidate = dp [w - weights [i]] + values [i];
                    if (candidate > dp [w])
                        dp [w] = candidate;
                }
            }
        }

        return (dp [capacity]);
    }

    /**
     * Minimum number of coins needed to make the given amount.
     * @param coins     available coin denominations
     * @param amount    target amount
     * @return minimum number of coins, or -1 if impossible
     */
    @InterestingAlgorithm(timeComplexity = "O(n * amount)", spaceComplexity = "O(amount)")
    public static int coinChange (int [] coins, int amount) {
        if (amount == 0)
            return (0);
        if (coins == null || coins.length == 0 || amount < 0)
            return (-1);

        int [] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++)
            dp [i] = Integer.MAX_VALUE;

        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins [j] <= i && dp [i - coins [j]] != Integer.MAX_VALUE) {
                    int candidate = dp [i - coins [j]] + 1;
                    if (candidate < dp [i])
                        dp [i] = candidate;
                }
            }
        }

        return (dp [amount] == Integer.MAX_VALUE ? -1 : dp [amount]);
    }

    /**
     * Number of distinct ways to make the given amount using the provided coins.
     * @param coins     available coin denominations
     * @param amount    target amount
     * @return number of combinations
     */
    @InterestingAlgorithm(timeComplexity = "O(n * amount)", spaceComplexity = "O(amount)")
    public static int coinChangeWays (int [] coins, int amount) {
        if (amount == 0)
            return (1);
        if (coins == null || coins.length == 0 || amount < 0)
            return (0);

        int [] dp = new int[amount + 1];
        dp [0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for (int j = coins [i]; j <= amount; j++) {
                dp [j] += dp [j - coins [i]];
            }
        }

        return (dp [amount]);
    }

    /**
     * Length of the longest strictly increasing subsequence.
     * @param array     input array
     * @return length of LIS
     */
    @InterestingAlgorithm(timeComplexity = "O(n log n)", spaceComplexity = "O(n)")
    public static int longestIncreasingSubsequence (int [] array) {
        if (array == null || array.length == 0)
            return (0);

        int [] tails = new int[array.length];
        int size = 0;

        for (int i = 0; i < array.length; i++) {
            int lo = 0;
            int hi = size;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails [mid] < array [i])
                    lo = mid + 1;
                else
                    hi = mid;
            }

            tails [lo] = array [i];
            if (lo == size)
                size++;
        }

        return (size);
    }

    /**
     * Minimum edit distance (Levenshtein distance) between two strings.
     * Operations: insert, delete, replace (each costs 1).
     * @param s1    first string
     * @param s2    second string
     * @return minimum number of operations
     */
    @InterestingAlgorithm(timeComplexity = "O(m * n)", spaceComplexity = "O(m * n)")
    public static int editDistance (String s1, String s2) {
        if (s1 == null || s1.length() == 0)
            return (s2 == null ? 0 : s2.length());
        if (s2 == null || s2.length() == 0)
            return (s1.length());

        int m = s1.length();
        int n = s2.length();
        int [][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
            dp [i][0] = i;
        for (int j = 0; j <= n; j++)
            dp [0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp [i][j] = dp [i - 1][j - 1];
                }
                else {
                    int insert = dp [i][j - 1];
                    int delete = dp [i - 1][j];
                    int replace = dp [i - 1][j - 1];
                    dp [i][j] = 1 + Math.min(insert, Math.min(delete, replace));
                }
            }
        }

        return (dp [m][n]);
    }

    /**
     * Matrix chain multiplication - find the minimum number of scalar multiplications
     * needed to compute the chain of matrices.
     * @param dimensions    array of dimensions where matrix i has dimensions[i] x dimensions[i+1]
     * @return minimum number of multiplications
     */
    @InterestingAlgorithm(timeComplexity = "O(n^3)", spaceComplexity = "O(n^2)")
    public static int matrixChainMultiplication (int [] dimensions) {
        if (dimensions == null || dimensions.length < 2)
            return (0);

        int n = dimensions.length - 1;
        int [][] dp = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp [i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = dp [i][k] + dp [k + 1][j] + dimensions [i] * dimensions [k + 1] * dimensions [j + 1];
                    if (cost < dp [i][j])
                        dp [i][j] = cost;
                }
            }
        }

        return (dp [0][n - 1]);
    }

    /**
     * Rod cutting problem - maximize revenue by cutting a rod of given length.
     * @param prices    prices[i] is the price of a rod of length i+1
     * @param length    total rod length
     * @return maximum revenue
     */
    @InterestingAlgorithm(timeComplexity = "O(n^2)", spaceComplexity = "O(n)")
    public static int rodCutting (int [] prices, int length) {
        if (prices == null || prices.length == 0 || length <= 0)
            return (0);

        int [] dp = new int[length + 1];

        for (int i = 1; i <= length; i++) {
            for (int j = 0; j < i && j < prices.length; j++) {
                int candidate = prices [j] + dp [i - (j + 1)];
                if (candidate > dp [i])
                    dp [i] = candidate;
            }
        }

        return (dp [length]);
    }

    /**
     * Finds the longest palindromic substring.
     * @param s     input string
     * @return the longest palindromic substring
     */
    @InterestingAlgorithm(timeComplexity = "O(n^2)", spaceComplexity = "O(n^2)")
    public static String longestPalindromicSubstring (String s) {
        if (s == null || s.length() == 0)
            return (s);

        int n = s.length();
        boolean [][] dp = new boolean[n][n];
        int start = 0;
        int maxLen = 1;

        for (int i = 0; i < n; i++)
            dp [i][i] = true;

        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp [i][i + 1] = true;
                if (maxLen < 2) {
                    start = i;
                    maxLen = 2;
                }
            }
        }

        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp [i + 1][j - 1]) {
                    dp [i][j] = true;
                    if (len > maxLen) {
                        start = i;
                        maxLen = len;
                    }
                }
            }
        }

        return (s.substring(start, start + maxLen));
    }

    /**
     * Kadane's algorithm - find the maximum sum of a contiguous subarray.
     * @param array     input array
     * @return maximum subarray sum
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int maxSubarraySum (int [] array) {
        if (array == null || array.length == 0)
            throw new IllegalArgumentException("Array must not be null or empty");

        int maxSoFar = array [0];
        int maxEndingHere = array [0];

        for (int i = 1; i < array.length; i++) {
            maxEndingHere = Math.max(array [i], maxEndingHere + array [i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return (maxSoFar);
    }

    /**
     * Number of unique paths from top-left to bottom-right in an m x n grid.
     * Can only move right or down.
     * @param m     number of rows
     * @param n     number of columns
     * @return number of unique paths
     */
    @InterestingAlgorithm(timeComplexity = "O(m * n)", spaceComplexity = "O(n)")
    public static int uniquePaths (int m, int n) {
        if (m <= 0 || n <= 0)
            return (0);

        int [] dp = new int[n];
        for (int j = 0; j < n; j++)
            dp [j] = 1;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp [j] += dp [j - 1];
            }
        }

        return (dp [n - 1]);
    }

    /**
     * Minimum path sum from top-left to bottom-right in a grid.
     * Can only move right or down.
     * @param grid  2D grid of non-negative integers
     * @return minimum path sum
     */
    @InterestingAlgorithm(timeComplexity = "O(m * n)", spaceComplexity = "O(1)")
    public static int minPathSum (int [][] grid) {
        if (grid == null || grid.length == 0 || grid [0].length == 0)
            return (0);

        int m = grid.length;
        int n = grid [0].length;

        for (int i = 1; i < m; i++)
            grid [i][0] += grid [i - 1][0];

        for (int j = 1; j < n; j++)
            grid [0][j] += grid [0][j - 1];

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid [i][j] += Math.min(grid [i - 1][j], grid [i][j - 1]);
            }
        }

        return (grid [m - 1][n - 1]);
    }
}
