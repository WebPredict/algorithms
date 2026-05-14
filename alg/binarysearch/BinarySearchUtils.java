package alg.binarysearch;

import alg.misc.InterestingAlgorithm;

/**
 * Common binary search interview patterns.
 */
public class BinarySearchUtils {

    /**
     * Searches for a target in a rotated sorted array. Returns index or -1 if not found.
     */
    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public static int searchRotatedArray (int [] nums, int target) {
        if (nums == null || nums.length == 0)
            return (-1);

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums [mid] == target)
                return (mid);

            if (nums [left] <= nums [mid]) {
                // Left half is sorted
                if (target >= nums [left] && target < nums [mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            }
            else {
                // Right half is sorted
                if (target > nums [mid] && target <= nums [right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }

        return (-1);
    }

    /**
     * Finds the first and last position of a target in a sorted array.
     * Returns {-1, -1} if target is not found.
     */
    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public static int [] findFirstAndLast (int [] sorted, int target) {
        int [] result = new int [] { -1, -1 };

        if (sorted == null || sorted.length == 0)
            return (result);

        // Find first occurrence
        int left = 0;
        int right = sorted.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (sorted [mid] == target) {
                result [0] = mid;
                right = mid - 1;
            }
            else if (sorted [mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }

        if (result [0] == -1)
            return (result);

        // Find last occurrence
        left = result [0];
        right = sorted.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (sorted [mid] == target) {
                result [1] = mid;
                left = mid + 1;
            }
            else if (sorted [mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return (result);
    }

    /**
     * Searches a matrix where each row is sorted and the first element of each row
     * is greater than the last element of the previous row.
     */
    @InterestingAlgorithm(timeComplexity = "O(log(m*n))", spaceComplexity = "O(1)")
    public static boolean searchMatrix (int [][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix [0].length == 0)
            return (false);

        int rows = matrix.length;
        int cols = matrix [0].length;
        int left = 0;
        int right = rows * cols - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int row = mid / cols;
            int col = mid % cols;
            int val = matrix [row] [col];

            if (val == target)
                return (true);
            else if (val < target)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return (false);
    }

    /**
     * Finds a peak element (strictly greater than its neighbors). Returns its index.
     * For elements at the edges, only one neighbor is considered.
     */
    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public static int findPeakElement (int [] nums) {
        if (nums == null || nums.length == 0)
            return (-1);

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums [mid] > nums [mid + 1])
                right = mid;
            else
                left = mid + 1;
        }

        return (left);
    }

    /**
     * Returns floor(sqrt(x)) using binary search.
     */
    @InterestingAlgorithm(timeComplexity = "O(log x)", spaceComplexity = "O(1)")
    public static int sqrtFloor (int x) {
        if (x < 0)
            throw new IllegalArgumentException("Cannot compute sqrt of negative number");

        if (x == 0)
            return (0);

        long left = 1;
        long right = x;
        long result = 0;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (mid * mid == x)
                return ((int) mid);
            else if (mid * mid < x) {
                result = mid;
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }

        return ((int) result);
    }

    /**
     * Finds the minimum element in a rotated sorted array (no duplicates).
     */
    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public static int findMinRotatedSorted (int [] nums) {
        if (nums == null || nums.length == 0)
            throw new IllegalArgumentException("Invalid input");

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums [mid] > nums [right])
                left = mid + 1;
            else
                right = mid;
        }

        return (nums [left]);
    }
}
