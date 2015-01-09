package alg.sequence;

import alg.math.MathUtils;
import alg.misc.InterestingAlgorithm;
import alg.misc.MiscUtils;
import alg.strings.StringUtils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SequenceUtils {
    /**
     *
     * TODO: Hirschberg's algorithm for optimal sequence alignment
     *
     */
    @InterestingAlgorithm
    public static int hammingDistance (String s1, String s2) {
        int s1Len = s1 == null ? 0 : s1.length();
        int s2Len = s2 == null ? 0 : s2.length();

        if (s1Len != s2Len)
            throw new RuntimeException("Cannot compute hamming distance on different length strings: " + s1 + " and " + s2);

        int distance = 0;
        for (int i = 0; i < s1Len; i++) {
            if (s1.charAt(i) != s2.charAt(i))
                distance++;
        }
        return (distance);
    }

    @InterestingAlgorithm
    public static int [] needlemanWunschScore (String x, String y) {
//        HashMap<Integer, HashMap<Integer, Integer>> matrixMap = new HashMap<Integer, HashMap<Integer, Integer>>();
//
//        HashMap<Integer, Integer> first = new HashMap<Integer, Integer>();
//        first.put(0, 0);
//
//        matrixMap.put(0, first);

        int         xLen = x == null ? 0 : x.length();
        int         yLen = y == null ? 0 : y.length();

        // TODO: optimize to only use two vectors!
        int [][]    scores = new int[xLen][yLen];
        scores [0][0] = 0;

        // TODO: is this < or <=?
        for (int j = 1; j < y.length(); j++) {
            scores [0][j] = scores [0][j - 1] + insertionCost(y.charAt(j));
        }

        for (int i = 1; i < x.length(); i++) {
            scores [i][0] = scores [i - 1][0] + deletionCost(x.charAt(i));

            for (int j = 1; j < y.length(); j++) {
                int scoreSub = scores [i -1][j - 1] + substitutionCost(x.charAt(i), y.charAt(j));
                int scoreDel = scores [i - 1][j] + deletionCost(x.charAt(i));
                int scoreIns = scores [i][j - 1] + insertionCost(y.charAt(j));

                scores [i][j] = MathUtils.max(new int [] {scoreSub, scoreDel, scoreIns});
            }
        }

        int []  lastLine = new int[y.length()];

        for (int j = 0; j < yLen; j++) {
            lastLine [j] = scores [xLen - 1][j];
        }
        return (lastLine);
    }

    public static int insertionCost(char c) {
        return (1);
    }

    public static int deletionCost(char c) {
        return (1);
    }

    public static int substitutionCost(char c1, char c2) {
        return (1);
    }

    @InterestingAlgorithm
    public static String []     hirschbergOptimalAlignment (String x, String y) {
        String []   ret = new String [] {"", ""};
        int         xLen = x == null ? 0 : x.length();
        int         yLen = y == null ? 0 : y.length();

        if (xLen == 0) {
            for (int i = 0; i < yLen; i++) {
                ret [0] += '-';
                ret [1] += y.charAt(i);
            }
        }
        else if (yLen == 0) {
            for (int i = 0; i < xLen; i++) {
                ret [0] += x.charAt(i);
                ret [1] += '-';
            }
        }
        else if (xLen == 1 && yLen == 1) {
            //ret = needlemanWunsch(x, y);     TODO
        }
        else {
            int xlen = x.length();
            int xmid = xlen / 2;
            int ylen = y.length();

            // TODO: to work space efficiently, we need to not make copies of substrings of the main strings:

            String firstHalfX = x.substring(0, xmid);
            int [] scoreL = needlemanWunschScore(firstHalfX, y);

            String secondHalfX = x.substring(xmid);
            int [] scoreR = needlemanWunschScore(StringUtils.reverse(secondHalfX), StringUtils.reverse(y));

            int ymid = partition(scoreL, scoreR);

            String firstHalfY = x.substring(0, ymid);
            ret = hirschbergOptimalAlignment(firstHalfX, firstHalfY);
            String secondHalfY = x.substring(ymid);
            String [] second = hirschbergOptimalAlignment(secondHalfX, secondHalfY);
            ret [0] += second [0];
            ret [1] += second [1];
        }
        return (ret);
    }

    public static int partition (int [] scoreL, int [] scoreR) {
         // TODO: return "arg max of scoreL + reverse(scoreR) whatever that means

        int []  scoreLAndReverseOfScoreR = new int[scoreL.length];
        for (int i = 0; i < scoreLAndReverseOfScoreR.length; i++) {
            scoreLAndReverseOfScoreR [i] = scoreL [i] + scoreR [i];
        }

        // perhaps this is what "arg max" means in this context?
        Integer max = null;
        int     maxIdx = -1;
        for (int i = 0; i < scoreLAndReverseOfScoreR.length; i++) {
              if (max == null || scoreLAndReverseOfScoreR [i] > max) {
                  max = scoreLAndReverseOfScoreR [i];
                  maxIdx = i;
              }
        }
        return (maxIdx);
    }

    public static int levenshteinDistanceRec (String s1, String s2) {
        return (levenshteinDistanceRec(s1, s1 == null ? 0 : s1.length(), s2, s2 == null ? 0 : s2.length()));
    }

    @InterestingAlgorithm
    public static int levenshteinDistanceRec (String s1, int s1Len, String s2, int s2Len) {
        if (s1Len == 0)
            return (s2Len);
        if (s2Len == 0)
            return (s1Len);

        int cost;
        if (s1.charAt(s1Len - 1) == s2.charAt(s2Len - 1))
            cost = 0;
        else
            cost = 1;

        return (MathUtils.min(new int[] {
                levenshteinDistanceRec(s1, s1Len - 1, s2, s2Len) + 1,
                levenshteinDistanceRec(s1, s1Len, s2, s2Len - 1) + 1,
                levenshteinDistanceRec(s1, s1Len - 1, s2, s2Len - 1) + cost
        }));
    }

    /**
     * the iterative dynamic programming version
     * @param s1
     * @param s2
     * @return
     */
    @InterestingAlgorithm
    public static int levenshteinDistance (String s1, String s2) {
        if (MiscUtils.safeEquals(s1, s2))
            return (0);
        int s1Len = s1 == null ? 0 : s1.length();
        int s2Len = s2 == null ? 0 : s2.length();

        if (s1Len == 0)
            return (s2Len);
        if (s2Len == 0)
            return (s1Len);

        int []  v0 = new int[s2.length() + 1];
        int []  v1 = new int[s2.length() + 1];

        for (int i = 0; i < v0.length; i++)
            v0 [i] = i;

        for (int i = 0; i < s1.length(); i++) {
            v1 [0] = i + 1;

            for (int j = 0; j < s2.length(); j++) {
                int cost = s1.charAt(i) == s2.charAt(j) ? 0 : 1;
                v1[j + 1] = MathUtils.min(new int[] {
                   v1[j] + 1, v0[j + 1] + 1, v0[j] + cost
                });
            }

            for (int j = 0; j < v0.length; j++)
                v0 [j] = v1[j];
        }
        return (v1[s2.length()]);
    }
}
