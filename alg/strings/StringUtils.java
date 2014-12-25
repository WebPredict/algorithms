package alg.strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    public static boolean isPalindrome (String s) {
        if (s == null)
            return (true);

        for (int i = 0; i <= s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i))
                return (false);
        }
        return (true);
    }


    public static void reverseInPlace (char [] chars, int startIdx, int endIdxInclusive) {
        for (int i = 0; i < (endIdxInclusive - startIdx) / 2; i++) {
            char tmp = chars [i + startIdx];

            chars [i] = chars [endIdxInclusive - i];
            chars [endIdxInclusive - i] = tmp;
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
            // Use hash tables for lower memory usage
        }

        return (ret);
    }

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

    public static List<List<String>>    allPossiblePalindromePartitions (String s) {
        return (null); // TODO
    }

    public static String        pad (String s, char padding, int amount) {
        return ((s == null ? "" : s) + String.copyValueOf(new char[] {padding}, 0, amount));
    }

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
            // use hashtables for lower memory
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

    // TODO: need a version of this for lines, not individual characters
    // TODO: optimize by detecting any initial similarities and end similarities in the strings/lines
    public static void printDiff (StringBuffer buf, int [][] longest, String s1, String s2, int i, int j) {
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

    public static void addedQuoted (StringBuffer buf, String prop, String val) {
        buf.append("\"" + prop + "\"\t:\"" + safe(val) + "\"\n");
    }


    public static String safe (String s) {
        return s == null ? "" : s;
    }

    public static void addLine (StringBuffer buf, String text) {
        buf.append(text + "\n");
    }

    public static void addTabbedLine (StringBuffer buf, String text) {
        buf.append("\t" + text + "\n");
    }

    public static void addLineBreak (StringBuffer buf) {
        buf.append("\n");
    }

    public static void addTabbedLine (StringBuffer buf, String text, int tabs) {
        for (int i = 0; i < tabs; i++) {
            buf.append("\t");
        }
        buf.append(text + "\n");
    }

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
}