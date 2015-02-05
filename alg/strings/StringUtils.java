package alg.strings;

import alg.misc.InterestingAlgorithm;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    /**
     *
     * @param s
     * @return
     */
    @InterestingAlgorithm
    public static boolean isPalindrome (String s) {
        if (s == null)
            return (true);

        for (int i = 0; i <= s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i))
                return (false);
        }
        return (true);
    }

    public static boolean isAlphanumeric (char c) {
        return ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'));
    }

    @InterestingAlgorithm
    public static boolean isPalindromeOnlyAlphanumeric (String s) {
        if (s == null)
            return (true);

        int leftCtr = 0;
        int rightCtr = s.length() - 1;

        while (leftCtr < rightCtr) {

            char leftChar = Character.toLowerCase(s.charAt(leftCtr));
            char rightChar = Character.toLowerCase(s.charAt(rightCtr));

            if (!isAlphanumeric(leftChar)) {
                leftCtr++;
            }
            else if (!isAlphanumeric(rightChar)) {
                rightCtr--;
            }
            else {
                leftCtr++;
                rightCtr--;
                if (leftChar != rightChar)
                    return (false);
            }
        }
        return (true);
    }

    @InterestingAlgorithm
    public static String [] chunk (String s, int size) {
    	if (s == null)
    		return (null);
    	
    	String [] ret = new String [(int)Math.ceil((double)s.length() / (double)size)];
    	int startIdx = 0;
    	int	idx = 0;
    	while (true) {
    		if (startIdx + size >= s.length()) {
    			ret [idx] = s.substring(startIdx);
    			break;
    		}
    		ret [idx++] = s.substring(startIdx, startIdx + size);
    		startIdx += size;
    	}
    	return (ret);
    }

    @InterestingAlgorithm
    public static String repeat (char c, int num) {
        if (num == 0)
            return ("");

        char []     repeated = new char[num];
        for (int i = 0; i < num; i++) {
            repeated [i] = c;
        }
        return (new String(repeated));
    }

    @InterestingAlgorithm
    public static String join (String [] lines) {
        StringBuilder builder = new StringBuilder();

        if (lines != null) {
            for (int i = 0; i < lines.length; i++) {
                builder.append(lines [i]);
                builder.append("\n");
            }
        }
        return (builder.toString());
    }

    @InterestingAlgorithm
    public static void reverseInPlace (char [] chars, int startIdx, int endIdxExclusive) {
        for (int i = 0; i < (endIdxExclusive - startIdx) / 2; i++) {
            char tmp = chars [i + startIdx];

            chars [i + startIdx] = chars [endIdxExclusive - i - 1];
            chars [endIdxExclusive - i - 1] = tmp;
        }
    }

    public static String reverse (String s) {
        if (s == null)
            return (null);

        char [] reversed = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            reversed [i] = s.charAt(s.length() - (i + 1));
        }
        return (new String(reversed));
    }

    public static int DPMaxMatrixSize = 1000 * 1000;

    @InterestingAlgorithm
    public static String longestCommonSubstring (String s1, String s2) {
        if (s1 == null || s2 == null)
            return (null);

        String ret = "";
        if (s1.length() * s2.length() <= DPMaxMatrixSize) {
            int [][] longest = new int[s1.length()][s2.length()];

            int z = 0;

            for (int i = 0; i < s1.length(); i++) {
                for (int j = 0; j < s2.length(); j++) {
                    if (s1.charAt(i) == s2.charAt(j)) {
                        if (i == 0 || j == 0) {
                            longest [i][j] = 1;
                        }
                        else {
                            longest [i][j] = longest [i - 1][j - 1] + 1;
                        }

                        if (longest [i][j] > z) {
                            z = longest [i][j];
                            ret = s1.substring(i - z + 1, i + 1);
                        }
                        else if (longest [i][j] == z) {
                            ret = ret + s1.substring(i - z + 1, i + 1);
                        }
                    }
                    else {
                        longest [i][j] = 0;
                    }
                }
            }
        }
        else {
            throw new RuntimeException("Unimplemented for larger strings");
            // Use hash tables for lower memory usage
        }

        return (ret);
    }

    @InterestingAlgorithm
    public static boolean areAnagrams (String s1, String s2) {
        if (s1 == null)
            return (s2 == null);
        else if (s2 == null)
            return (s1 == null);
        if (s1.length() != s2.length())
            return (false);

        HashMap<Character, Integer> charToIntCount = new HashMap<Character, Integer>();

        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            Integer seen = charToIntCount.get(c);
            if (seen == null)
                charToIntCount.put(c, 1);
            else
                charToIntCount.put(c, seen + 1);
        }

        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            Integer seen = charToIntCount.get(c);
            if (seen == 1)
                charToIntCount.remove(c);
            else
                charToIntCount.put(c, seen - 1);
        }

       return (charToIntCount.size() == 0);
    }

    @InterestingAlgorithm
    public static List<String> listAnagrams (String s) {
        if (s == null)
            return (null);
        else if (s.equals(""))
            return (Collections.singletonList(s));
        else if (s.length() == 1)
            return (Collections.singletonList(s));

        List<String>    subAnagrams = listAnagrams(s.substring(1));
        List<String>    retAnagrams = new ArrayList<String>();

        for (int i = 0; i < subAnagrams.size(); i++) {
            String subAnagram = subAnagrams.get(i);
            for (int j = 0; j < subAnagram.length(); j++) {
                retAnagrams.add(subAnagram.substring(0, j) + s.charAt(0) + subAnagram.substring(j));
            }
            retAnagrams.add(subAnagram + s.charAt(0));
        }

        return (retAnagrams);
    }

    /**
     * Returns all groups of strings that have anagrams in this list, in no particular order, flattened array.
     * @param strs
     * @return
     */
    @InterestingAlgorithm
    public static List<String> listAnagrams (String [] strs) {
        if (strs == null)
            return (null);
        else if (strs.length == 0)
            return (new ArrayList<String>());

        HashMap<String, List<String>> sortedToListMap = new HashMap<String, List<String>>();

        for (String s : strs) {

            char [] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            List<String> list = sortedToListMap.get(key);
            if (list == null) {
                list = new ArrayList<String>();
                sortedToListMap.put(key, list);
            }
            list.add(s);
        }

        List<String> all = new ArrayList<String>();

        for (List<String> value : sortedToListMap.values()) {
           if (value.size() > 1)
               all.addAll(value);
        }

        return (all);
    }

    @InterestingAlgorithm
    public static List<List<String>>    allPossiblePalindromePartitions (String s) {
        return (null); // TODO
    }

    public static String        pad (String s, char padding, int amount) {
        return ((s == null ? "" : s) + repeat(padding, amount));
    }

    @InterestingAlgorithm
    public static String        longestCommonSubsequence (String s1, String s2) {
        if (s1 == null || s2 == null)
            return (null);

        String ret = "";
        if (s1.length() * s2.length() <= DPMaxMatrixSize) {
            int [][] longest = new int[s1.length() + 1][s2.length() + 1];

            for (int i = 1; i < s1.length(); i++) {
                for (int j = 1; j < s2.length(); j++) {
                    if (s1.charAt(i) == s2.charAt(j)) {
                         longest [i][j] = longest [i - 1][j - 1] + 1;
                    }
                    else {
                        longest [i][j] = Math.max(longest [i][j - 1], longest [i - 1][j]);
                    }
                }
            }

            int i = s1.length();
            int j = s2.length();

            ret = backtrack(longest, s1, s2, s1.length(), s2.length());
        }
        else {
            // TODO: use hashtables for lower memory
        }

        return (ret);
    }

    public static String backtrack (int [][] longest, String s1, String s2, int m, int n) {
        if (m == 0 || n == 0)
            return "";

        else if (s1.charAt(m) == s2.charAt(m)) {
            return (backtrack(longest, s1, s2, m - 1, n -1) + s1.charAt(m));
        }
        else {
            if (longest [m][n - 1] > longest [m - 1][n]) {
                return (backtrack(longest, s1, s2, m, n - 1));
            }
            else {
               return (backtrack(longest, s1, s2, m - 1, n));
            }
        }
    }

    /**
     * Prints char-by-char differences between strings
     *  TODO: need a version of this for lines, not individual characters
     *  TODO: optimize by detecting any initial similarities and end similarities in the strings/lines
     * @param buf
     * @param longest
     * @param s1
     * @param s2
     * @param i
     * @param j
     */
    @InterestingAlgorithm
    public static void printDiff (StringBuilder buf, int [][] longest, String s1, String s2, int i, int j) {
        if (i > 0 && j > 0 && s1.charAt(i) == s2.charAt(j)) {
            printDiff(buf, longest, s1, s2, i - 1, j - 1);
            buf.append("  " + s1.charAt(i));
        }
        else if (j > 0 && (i == 0 || longest[i][j - 1] >= longest [i - 1][j])) {
            printDiff(buf, longest, s1, s2, i, j - 1);
            buf.append("+ " + s2.charAt(j));
        }
        else if (i > 0 && (j == 0 || longest[i][j - 1] < longest [i - 1][j])) {
            printDiff(buf, longest, s1, s2, i - 1, j);
            buf.append("- " + s1.charAt(i));
        }
//        else
//            buf.append(""); // pointless?
    }

    public static void addedQuoted (StringBuilder buf, String prop, String val) {
        buf.append("\"" + prop + "\"\t:\"" + safe(val) + "\"\n");
    }


    public static String safe (String s) {
        return s == null ? "" : s;
    }

    public static void addLine (StringBuilder buf, String text) {
        buf.append(text + "\n");
    }

    public static void addTabbedLine (StringBuilder buf, String text) {
        buf.append("\t" + text + "\n");
    }

    public static void addLineBreak (StringBuilder buf) {
        buf.append("\n");
    }

    public static void addTabbedLine (StringBuilder buf, String text, int tabs) {
        for (int i = 0; i < tabs; i++) {
            buf.append("\t");
        }
        buf.append(text + "\n");
    }

    @InterestingAlgorithm
    public static Integer extractIntegerFrom (String s) {
        int started = -1;
        int ended = -1;
        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    if (started == -1) {
                        started =  i;
                    }
                }
                else {
                    if (started != -1 && ended == -1) {
                        ended = i;
                    }
                }
            }
            if (started != -1 && ended == -1) {
                ended = s.length();
            }
        }
        if (started == -1)
            throw new RuntimeException("Could not extract number from " + s);
        else
            return (Integer.parseInt(s.substring(started, ended)));
    }

    /**
     *
     * @param s
     * @return if s == "aaaabbbccccd" return "a4b3c4d" or original string if compressed will be longer
     */
    @InterestingAlgorithm
    public static String	basicCompression (String s) {
    	return (s); // TODO
    }

    @InterestingAlgorithm
    public static int		shortestDistanceBetweenWords (String text, String word1, String word2) {
    	return (0); // TODO
    }

    @InterestingAlgorithm
    public static String longestCommonPrefix (String[] strs) {
        if (strs == null)
            return (null);
        else if (strs.length == 0)
            return ("");

        int commonIdx = 0;

        boolean allMatched;
        do {
            Character c = null;
            allMatched = false;
            for (int i = 0; i < strs.length; i++) {
                if (commonIdx < strs [i].length()) {
                    if (c == null) {
                        c = strs [i].charAt(commonIdx);
                        allMatched = true;
                    }
                    else if (c != strs [i].charAt(commonIdx)) {
                        allMatched = false;
                        break;
                    }
                }
                else {
                    allMatched = false;
                    break;
                }
            }
            if (allMatched)
                commonIdx++;
            else
                break;
        } while (allMatched);

        if (commonIdx == 0)
            return ("");
        return (strs [0].substring(0, commonIdx));
    }
}
