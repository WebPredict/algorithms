package alg.slidingwindow;

import alg.misc.InterestingAlgorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Common sliding window interview patterns.
 */
public class SlidingWindowUtils {

    /**
     * Returns the maximum sum of any contiguous subarray of size k.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public static int maxSumSubarrayOfSizeK (int [] arr, int k) {
        if (arr == null || arr.length < k || k <= 0)
            throw new IllegalArgumentException("Invalid input");

        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr [i];
        }

        int maxSum = windowSum;

        for (int i = k; i < arr.length; i++) {
            windowSum += arr [i] - arr [i - k];
            if (windowSum > maxSum)
                maxSum = windowSum;
        }

        return (maxSum);
    }

    /**
     * Returns the length of the longest substring without repeating characters.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(min(n, alphabet))")
    public static int longestSubstringWithoutRepeating (String s) {
        if (s == null || s.length() == 0)
            return (0);

        Map<Character, Integer> charIndex = new HashMap<Character, Integer>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (charIndex.containsKey(c) && charIndex.get(c) >= left)
                left = charIndex.get(c) + 1;

            charIndex.put(c, right);
            int windowLen = right - left + 1;
            if (windowLen > maxLen)
                maxLen = windowLen;
        }

        return (maxLen);
    }

    /**
     * Returns the minimum window in s that contains all characters of t.
     * Returns empty string if no such window exists.
     */
    @InterestingAlgorithm(timeComplexity = "O(n + m)", spaceComplexity = "O(n + m)")
    public static String minWindowSubstring (String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0)
            return ("");

        Map<Character, Integer> needed = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            needed.put(c, needed.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();
        int have = 0;
        int need = needed.size();
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            windowCounts.put(c, windowCounts.getOrDefault(c, 0) + 1);

            if (needed.containsKey(c) && windowCounts.get(c).intValue() == needed.get(c).intValue())
                have++;

            while (have == need) {
                int windowLen = right - left + 1;
                if (windowLen < minLen) {
                    minLen = windowLen;
                    minLeft = left;
                }

                char leftChar = s.charAt(left);
                windowCounts.put(leftChar, windowCounts.get(leftChar) - 1);
                if (needed.containsKey(leftChar) && windowCounts.get(leftChar) < needed.get(leftChar))
                    have--;

                left++;
            }
        }

        if (minLen == Integer.MAX_VALUE)
            return ("");

        return (s.substring(minLeft, minLeft + minLen));
    }

    /**
     * Returns the length of the longest substring with at most k distinct characters.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(k)")
    public static int longestSubstringWithKDistinct (String s, int k) {
        if (s == null || s.length() == 0 || k <= 0)
            return (0);

        Map<Character, Integer> charCount = new HashMap<Character, Integer>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);

            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0)
                    charCount.remove(leftChar);
                left++;
            }

            int windowLen = right - left + 1;
            if (windowLen > maxLen)
                maxLen = windowLen;
        }

        return (maxLen);
    }

    /**
     * Returns an array of maximums of each sliding window of size k.
     * Uses a deque to achieve linear time.
     */
    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(k)")
    public static int [] slidingWindowMaximum (int [] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0)
            throw new IllegalArgumentException("Invalid input");

        int n = nums.length;
        int [] result = new int [n - k + 1];
        Deque<Integer> deque = new ArrayDeque<Integer>();

        for (int i = 0; i < n; i++) {
            // Remove indices outside the window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1)
                deque.pollFirst();

            // Remove smaller elements from the back
            while (!deque.isEmpty() && nums [deque.peekLast()] < nums [i])
                deque.pollLast();

            deque.offerLast(i);

            if (i >= k - 1)
                result [i - k + 1] = nums [deque.peekFirst()];
        }

        return (result);
    }
}
