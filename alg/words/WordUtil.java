package alg.words;

import alg.strings.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/6/14
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class WordUtil {

    public static String []     commonWords; // TODO fill

    public static boolean   rhymingEnglishWords (String s1, String s2) {
        return (false);
    }

    public static String    soundex (String s) {

        if (s == null)
            return (null);
        else if (s.trim().equals(""))
            return ("");
        String  trim = s.trim();

        String ret = s.substring(0, 1);

        ArrayList<Character> chars = new ArrayList<Character>();

        // TODO: need to first remove characters that would result in duplicate numbers, before removing vowels it looks like
        for (int i = 1; i < s.length(); i++) {
            char c = Character.toLowerCase(s.charAt(i));
            if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u' && c != 'y' && c != 'h' && c != 'w')
                chars.add(c);
        }
        for (int i = 0; i < chars.size(); i++) {
            char c = chars.get(i);
            if (c == 'b' || c == 'f' || c == 'p' || c == 'v')
                ret += "1";
            else if (c == 'c' || c == 'g' || c == 'j' || c == 'k' || c == 'q' || c == 's' || c == 'x' || c == 'z')
                ret += "2";
            else if (c == 'd' || c == 't')
                ret += "3";
            else if (c == 'l')
                ret += "4";
            else if (c == 'm' || c == 'n')
                ret += "5";
            else if (c == 'r')
                ret += "6";

            if (ret.length() > 3)
                break;
        }
        if (ret.length() < 4)
            ret = StringUtils.pad(ret, '0', 4 - ret.length());

        return (ret);
    }


    public static String [] doubleMetaphone (String s) {
        return (null); // TODO
    }

    public static String    autocorrect (String s) {
        return (s); // TODO
    }

    public static String []     syllables (String s) {
        return (null); // TODO
    }

    public static String    capitalizeAndJoin (String... words) {
        String ret = "";

        for (String word : words) {
            ret += capitalize(word);
        }
        return (ret);
    }

    public static String    capitalizeAndSpace (String camelCaseWord) {
        ArrayList<Character> chars = new ArrayList<Character>();
        boolean sawLower = false;
        for (int i = 0; i < camelCaseWord.length(); i++) {
            char c = camelCaseWord.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if (!sawLower) {
                    sawLower = true;

                    if (i == 0)  // should only need to capitalize first letter if word is camel case
                        c = Character.toUpperCase(c);
                }
            }
            else if (c >= 'A' && c <= 'Z') {
                if (sawLower) {
                    chars.add(' ');
                    sawLower = false;
                }
            }

            chars.add(c);
        }

        char [] charArr = new char[chars.size()];
        for (int i = 0; i < chars.size(); i++) {
            charArr [i] = chars.get(i);
        }
        return (new String(charArr));
    }

    public static String    capitalize (String word) {
        if (word == null || word.length() == 0)
            return (word);

        return (Character.toUpperCase(word.charAt(0)) + word.substring(1));
    }


    public static String    reverseWords (String s) {
        // reverse order of words, not letters in words - do the double reverse:

        String ret = StringUtils.reverse(s);
        int wordStart = -1;
        char [] chars = ret.toCharArray();
        for (int i = 0; i < ret.length(); i++) {
            if (chars [i] != ' ') {
                if (wordStart == -1)
                    wordStart = i;
            }
            else {
                if (wordStart != -1) {
                    StringUtils.reverseInPlace(chars, wordStart, i);
                    wordStart = -1;
                }
            }
        }
        return (ret);
    }

    public static String    pluralize (String s) {
        if (s == null)
            return ("s");
        else if (s.endsWith("y")) {
            return (s.substring(0, s.length() - 1) + "ies");
        }
        else
            return (s + "s");
    }


    public static List<String>  textJustification (List<String> words, int lineLength) {
        return (null); // TODO
    }

    public static boolean isMatch (String s, String pattern) {
        // ? matches any character
        // * matches any sequence of characters including empty
        return (false); // TODO
    }
}
