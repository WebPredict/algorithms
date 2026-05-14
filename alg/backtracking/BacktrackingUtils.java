package alg.backtracking;

import alg.misc.InterestingAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classic backtracking algorithm implementations.
 */
public class BacktrackingUtils {

    /**
     * Solve the N-Queens problem. Returns all distinct board configurations.
     * Each solution is a list of strings where 'Q' represents a queen and '.' an empty cell.
     * @param n     board size (n x n)
     * @return all valid board configurations
     */
    @InterestingAlgorithm(timeComplexity = "O(n!)", spaceComplexity = "O(n^2)")
    public static List<List<String>> solveNQueens (int n) {
        List<List<String>> results = new ArrayList<List<String>>();
        if (n <= 0)
            return (results);

        int [] queens = new int[n];
        Arrays.fill(queens, -1);
        solveNQueensHelper(queens, 0, n, results);
        return (results);
    }

    private static void solveNQueensHelper (int [] queens, int row, int n, List<List<String>> results) {
        if (row == n) {
            List<String> board = new ArrayList<String>();
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(queens [i] == j ? 'Q' : '.');
                }
                board.add(sb.toString());
            }
            results.add(board);
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isQueenSafe(queens, row, col)) {
                queens [row] = col;
                solveNQueensHelper(queens, row + 1, n, results);
                queens [row] = -1;
            }
        }
    }

    private static boolean isQueenSafe (int [] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens [i] == col)
                return (false);
            if (Math.abs(queens [i] - col) == Math.abs(i - row))
                return (false);
        }
        return (true);
    }

    /**
     * Generate all valid combinations of n pairs of parentheses.
     * @param n     number of pairs
     * @return all valid parentheses combinations
     */
    @InterestingAlgorithm(timeComplexity = "O(4^n / sqrt(n))", spaceComplexity = "O(n)")
    public static List<String> generateParentheses (int n) {
        List<String> results = new ArrayList<String>();
        if (n <= 0)
            return (results);

        generateParenthesesHelper(results, new StringBuilder(), 0, 0, n);
        return (results);
    }

    private static void generateParenthesesHelper (List<String> results, StringBuilder current, int open, int close, int n) {
        if (current.length() == n * 2) {
            results.add(current.toString());
            return;
        }

        if (open < n) {
            current.append('(');
            generateParenthesesHelper(results, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1);
        }

        if (close < open) {
            current.append(')');
            generateParenthesesHelper(results, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1);
        }
    }

    /**
     * Generate all subsets (power set) of the given array.
     * @param nums  input array
     * @return all subsets
     */
    @InterestingAlgorithm(timeComplexity = "O(2^n)", spaceComplexity = "O(2^n * n)")
    public static List<List<Integer>> subsets (int [] nums) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (nums == null)
            return (results);

        subsetsHelper(nums, 0, new ArrayList<Integer>(), results);
        return (results);
    }

    private static void subsetsHelper (int [] nums, int start, List<Integer> current, List<List<Integer>> results) {
        results.add(new ArrayList<Integer>(current));

        for (int i = start; i < nums.length; i++) {
            current.add(nums [i]);
            subsetsHelper(nums, i + 1, current, results);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Generate all permutations of the given array.
     * @param nums  input array
     * @return all permutations
     */
    @InterestingAlgorithm(timeComplexity = "O(n!)", spaceComplexity = "O(n! * n)")
    public static List<List<Integer>> permutations (int [] nums) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0)
            return (results);

        boolean [] used = new boolean[nums.length];
        permutationsHelper(nums, used, new ArrayList<Integer>(), results);
        return (results);
    }

    private static void permutationsHelper (int [] nums, boolean [] used, List<Integer> current, List<List<Integer>> results) {
        if (current.size() == nums.length) {
            results.add(new ArrayList<Integer>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!used [i]) {
                used [i] = true;
                current.add(nums [i]);
                permutationsHelper(nums, used, current, results);
                current.remove(current.size() - 1);
                used [i] = false;
            }
        }
    }

    /**
     * Find all unique combinations of candidates that sum to the target.
     * Each candidate may be reused unlimited times.
     * @param candidates    available numbers
     * @param target        target sum
     * @return all valid combinations
     */
    @InterestingAlgorithm(timeComplexity = "O(n^(target/min))", spaceComplexity = "O(target/min)")
    public static List<List<Integer>> combinationSum (int [] candidates, int target) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (candidates == null || candidates.length == 0 || target <= 0)
            return (results);

        Arrays.sort(candidates);
        combinationSumHelper(candidates, target, 0, new ArrayList<Integer>(), results);
        return (results);
    }

    private static void combinationSumHelper (int [] candidates, int remaining, int start, List<Integer> current, List<List<Integer>> results) {
        if (remaining == 0) {
            results.add(new ArrayList<Integer>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            if (candidates [i] > remaining)
                break;
            current.add(candidates [i]);
            combinationSumHelper(candidates, remaining - candidates [i], i, current, results);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Determine if a word exists in the board by following adjacent cells (horizontal/vertical neighbors).
     * Each cell may only be used once per word.
     * @param board     2D character grid
     * @param word      word to search for
     * @return true if word exists in the grid
     */
    @InterestingAlgorithm(timeComplexity = "O(m * n * 4^L)", spaceComplexity = "O(L)")
    public static boolean wordSearch (char [][] board, String word) {
        if (board == null || board.length == 0 || board [0].length == 0)
            return (false);
        if (word == null || word.length() == 0)
            return (true);

        int rows = board.length;
        int cols = board [0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (wordSearchHelper(board, word, i, j, 0))
                    return (true);
            }
        }

        return (false);
    }

    private static boolean wordSearchHelper (char [][] board, String word, int row, int col, int index) {
        if (index == word.length())
            return (true);
        if (row < 0 || row >= board.length || col < 0 || col >= board [0].length)
            return (false);
        if (board [row][col] != word.charAt(index))
            return (false);

        char saved = board [row][col];
        board [row][col] = '#';

        boolean found = wordSearchHelper(board, word, row + 1, col, index + 1)
                || wordSearchHelper(board, word, row - 1, col, index + 1)
                || wordSearchHelper(board, word, row, col + 1, index + 1)
                || wordSearchHelper(board, word, row, col - 1, index + 1);

        board [row][col] = saved;
        return (found);
    }

    /**
     * Solve a 9x9 Sudoku puzzle in place. Empty cells are represented by 0.
     * @param board     9x9 grid with 0 for empty cells
     * @return true if the puzzle was solved
     */
    @InterestingAlgorithm(timeComplexity = "O(9^(empty cells))", spaceComplexity = "O(empty cells)")
    public static boolean solveSudoku (int [][] board) {
        if (board == null || board.length != 9 || board [0].length != 9)
            return (false);

        return (solveSudokuHelper(board));
    }

    private static boolean solveSudokuHelper (int [][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board [row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSudokuValid(board, row, col, num)) {
                            board [row][col] = num;
                            if (solveSudokuHelper(board))
                                return (true);
                            board [row][col] = 0;
                        }
                    }
                    return (false);
                }
            }
        }
        return (true);
    }

    private static boolean isSudokuValid (int [][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board [row][i] == num)
                return (false);
            if (board [i][col] == num)
                return (false);
        }

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board [i][j] == num)
                    return (false);
            }
        }

        return (true);
    }
}
