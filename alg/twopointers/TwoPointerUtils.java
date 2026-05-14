package alg.twopointers;

import alg.misc.InterestingAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Common two-pointer interview patterns.
 */
public class TwoPointerUtils {

    /**
     * Given a sorted array and a target, returns the indices of two elements that sum to target.
     * Returns null if no such pair exists.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int [] twoSum (int [] sorted, int target) {
        if (sorted == null || sorted.length < 2)
            return (null);

        int left = 0;
        int right = sorted.length - 1;

        while (left < right) {
            int sum = sorted [left] + sorted [right];

            if (sum == target)
                return (new int [] { left, right });
            else if (sum < target)
                left++;
            else
                right--;
        }

        return (null);
    }

    /**
     * Returns all unique triplets that sum to target.
     */
    @InterestingAlgorithm(timeComplexity = "O(n^2)", spaceComplexity = "O(n)")
    public static List<List<Integer>> threeSum (int [] nums, int target) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        if (nums == null || nums.length < 3)
            return (result);

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums [i] == nums [i - 1])
                continue;

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums [i] + nums [left] + nums [right];

                if (sum == target) {
                    List<Integer> triplet = new ArrayList<Integer>();
                    triplet.add(nums [i]);
                    triplet.add(nums [left]);
                    triplet.add(nums [right]);
                    result.add(triplet);

                    while (left < right && nums [left] == nums [left + 1])
                        left++;
                    while (left < right && nums [right] == nums [right - 1])
                        right--;

                    left++;
                    right--;
                }
                else if (sum < target)
                    left++;
                else
                    right--;
            }
        }

        return (result);
    }

    /**
     * Returns the maximum area of water that can be contained between two lines.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int containerWithMostWater (int [] heights) {
        if (heights == null || heights.length < 2)
            return (0);

        int left = 0;
        int right = heights.length - 1;
        int maxArea = 0;

        while (left < right) {
            int height = Math.min(heights [left], heights [right]);
            int width = right - left;
            int area = height * width;

            if (area > maxArea)
                maxArea = area;

            if (heights [left] < heights [right])
                left++;
            else
                right--;
        }

        return (maxArea);
    }

    /**
     * Returns the total amount of trapped rain water.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int trappingRainWater (int [] heights) {
        if (heights == null || heights.length < 3)
            return (0);

        int left = 0;
        int right = heights.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        while (left < right) {
            if (heights [left] < heights [right]) {
                if (heights [left] >= leftMax)
                    leftMax = heights [left];
                else
                    water += leftMax - heights [left];
                left++;
            }
            else {
                if (heights [right] >= rightMax)
                    rightMax = heights [right];
                else
                    water += rightMax - heights [right];
                right--;
            }
        }

        return (water);
    }

    /**
     * Removes duplicates from a sorted array in-place and returns the new length.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int removeDuplicatesSorted (int [] sorted) {
        if (sorted == null || sorted.length == 0)
            return (0);

        int slow = 0;

        for (int fast = 1; fast < sorted.length; fast++) {
            if (sorted [fast] != sorted [slow]) {
                slow++;
                sorted [slow] = sorted [fast];
            }
        }

        return (slow + 1);
    }

    /**
     * Checks if a string is a palindrome, considering only alphanumeric characters and ignoring case.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static boolean isPalindromeString (String s) {
        if (s == null || s.length() == 0)
            return (true);

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left)))
                left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right)))
                right--;

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right)))
                return (false);

            left++;
            right--;
        }

        return (true);
    }
}
