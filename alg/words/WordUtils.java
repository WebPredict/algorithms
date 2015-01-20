package alg.words;

import alg.misc.InterestingAlgorithm;
import alg.strings.StringUtils;
import alg.trees.Trie;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/6/14
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class WordUtils {

    public static String []     commonWords; // TODO fill

    @InterestingAlgorithm
    public static boolean   rhymingEnglishWords (String s1, String s2) {
        return (false);
    }

    @InterestingAlgorithm
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

    @InterestingAlgorithm
    public static String [] doubleMetaphone (String s) {
        return (null); // TODO
    }

    /**
     * Tries to make most useful autocorrections based on the following factors:
     * keyboard layout (assumes qwerty for now)
     * language (english for now)
     * dictionary, optionally with word frequencies
     * list of past suggestions vs. what the user did with them (accepted suggestion vs. changed to something else)
     * Note this method computes a Trie (prefix tree) of the dictionary of words, which is potentially pretty expensive!
     * @param s
     * @param wordToGeneralFrequencyMap
     * @param pastSuggestionsToCorrectionsMap
     * @return
     */
    @InterestingAlgorithm
    public static String    autocorrect (String s, Map<String, Float> wordToGeneralFrequencyMap, Map<String, String> pastSuggestionsToCorrectionsMap) {
        if (wordToGeneralFrequencyMap == null)
            return (s); // can't do anything

        return (autocorrect(s, wordToGeneralFrequencyMap, new Trie(wordToGeneralFrequencyMap.keySet()), pastSuggestionsToCorrectionsMap));

    }

    // TODO: for completeness this should also take into consideration N-grams
    public static String    autocorrect (String s, Map<String, Float> wordToGeneralFrequencyMap, Trie dictionaryTrie, Map<String, String> pastSuggestionsToCorrectionsMap) {

        Set<String> wordSet = wordToGeneralFrequencyMap.keySet();

        /**
         * Some notes on approaches:
         *
         * if the word is in the dictionary:
         *  if there are no words starting with this word in the trie,
         *  return s
         *  else find the highest frequency word that starts with this word
         *  if there are no past suggestions about it, return highest frequency word
         *  else return the correction
         *
         *
         *  when s is not in the trie, need to look at min edit distance to a word in the dictionary,
         *  taking into consideration distance function for qwerty keyboard.
         *
         *  But first look to see if s is in the past suggestions map, and return value if it's there.
         *
         *  approach: precompute a bunch of guesses based on qwerty edit distance to possible words
         *  then see which ones are in the dictionary, then sort by frequency
         *
         */

        // This needs to be cached, as it's expensive to compute this possibly large set of words starting with some prefix:
        Set<String>    wordsStartWithS = dictionaryTrie.wordsStartingWith(s);

        if (wordsStartWithS == null) {

        }
        else {
            if (wordsStartWithS.size() == 1)
                return (s);
            else {


                // find highest frequency word
                // then look for existing corrections around that word

            }
        }

        return (s); // TODO
    }

    @InterestingAlgorithm
    public static String []     syllables (String s) {
        return (null); // TODO
    }

    @InterestingAlgorithm
    public static String    capitalizeAndJoin (String... words) {
        String ret = "";

        for (String word : words) {
            ret += capitalize(word);
        }
        return (ret);
    }

    /**
     * Takes camelCase or under_score word and produces "Camel Case"
     * @param camelCaseWord
     * @return
     */
    @InterestingAlgorithm
    public static String    capitalizeAndSpace (String camelOrUnderscore) {
        ArrayList<Character> chars = new ArrayList<Character>();
        boolean sawLower = false;
        for (int i = 0; i < camelOrUnderscore.length(); i++) {
            char c = camelOrUnderscore.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if (!sawLower) {
                    sawLower = true;

                    if (i == 0)  // should only need to capitalize first letter if word is camel case
                        c = Character.toUpperCase(c);
                }
            }
            else if (c == '_' || (c >= 'A' && c <= 'Z')) {
                if (sawLower) {
                    chars.add(' ');
                    sawLower = false;
                }
            }

            if (c != '_')
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

    @InterestingAlgorithm
    public static String    reverseWords (String s) {
        // reverse order of words, not letters in words - do the double reverse:
        if (s == null)
            return (null);

        char [] chars = s.toCharArray();
        StringUtils.reverseInPlace(chars, 0, s.length());
        int wordStart = -1;
        for (int i = 0; i < chars.length; i++) {
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
        if (wordStart != -1) {
            StringUtils.reverseInPlace(chars, wordStart, chars.length);
            wordStart = -1;
        }

        return (new String(removeExcessSpaces(chars)));
    }

    /**
     * Performs a trim both at beginning, end and in between words in chars.
     * @param chars
     * @return
     */
    @InterestingAlgorithm
    public static String removeExcessSpaces (char [] chars) {
        char [] tmpArr = new char[chars.length];
        boolean seenWord = false;
        int lastSeenLetterIdx = -1;
        int toRetIdx = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars [i] != ' ') {
                tmpArr [toRetIdx++] = chars [i];
                seenWord = true;
                lastSeenLetterIdx = toRetIdx;
            }
            else if (seenWord) {
                seenWord = false;
                tmpArr [toRetIdx++] = chars [i];
            }
        }
        if (lastSeenLetterIdx == -1)
            return ("");
        char [] ret = new char[lastSeenLetterIdx];
        System.arraycopy(tmpArr, 0, ret, 0, lastSeenLetterIdx);
        return (new String(ret));
    }


    /**
     * English pluralization
     * @param s
     * @return
     */
    public static String    pluralize (String s) {
        if (s == null)
            return ("s");
        else if (s.endsWith("y")) {
            return (s.substring(0, s.length() - 1) + "ies");
        }
        else if (s.endsWith("s")) {
            return (s.substring(0, s.length()) + "es");
        }
        else
            return (s + "s");
    }

    @InterestingAlgorithm
    public static List<String>  leftRightTextJustification (String words, int lineLength) {
        if (words == null)
            return (null);

        String [] lines = words.split("\n");

        ArrayList<String> allWords = new ArrayList<String>();
        for (String line : lines) {
            allWords.addAll(Arrays.asList(line.split(" ")));
        }
        return (leftRightTextJustification(allWords, lineLength));
    }

    @InterestingAlgorithm
    public static List<String>  leftRightTextJustificationParagraphs (List<String> paragraphs, int lineLength) {
        if (paragraphs == null)
            return (null);


        ArrayList<String> allWords = new ArrayList<String>();

        for (String paragraph : paragraphs) {
            String [] lines = paragraph.split("\n");

            boolean first = true;
            for (String line : lines) {
                List<String> words = Arrays.asList(line.split(" "));
                if (words != null && words.size() > 0 && first) {
                    words.set(0, "    " + words.get(0));
                    first = false;
                }
                allWords.addAll(words);
            }
        }
        return (leftRightTextJustification(allWords, lineLength));
    }

    @InterestingAlgorithm
    public static List<String>  leftRightTextJustification (List<String> words, int lineLength) {
        if (words == null)
            return (null);

        ArrayList<String> lines = new ArrayList<String>();

        StringBuilder builder = new StringBuilder();

        // TODO: simplify...
        int     curLineSize = 0;
        int     lastWordIdx = 0;
        for (int i = 0; i < words.size(); i++) {
            String curWord = words.get(i);

            int curWordLen = curWord.length();
            
            if (curWordLen > lineLength) {
            	String [] chunks = StringUtils.chunk(curWord, lineLength);
            	words.set(i, chunks [0]);
            	for (int k = 1; k < chunks.length; k++) {
            		words.add(i + k, chunks [k]);
            	}
            }
            if (i < words.size() - 1) {
            	if (curWordLen + curLineSize < lineLength - 1)
            		curWordLen += 1; // need a space if we're not about to fill the line
            }
            if (curWordLen + curLineSize < lineLength) {
                curLineSize += curWordLen;

                if (i == words.size() - 1) {
                    int numWords = i + 1 - lastWordIdx;
                    int extraSpace = lineLength - curLineSize;
                    float extraSpacePerWord =  (float)extraSpace / (float)numWords;
                    float extraSpaceGiven = 0;
                    // interesting case: how to evenly space out the extra space
                    for (int j = lastWordIdx; j < i + 1; j++) {
                        builder.append(words.get(j));
                        builder.append(" "); // TODO compute right amount of space

                        extraSpaceGiven += extraSpacePerWord;

                        if (((int)extraSpaceGiven) > 0) {
                            for (int k = 0; k < (int)extraSpaceGiven; k++) {
                                builder.append(" ");
                            }
                            extraSpaceGiven -= (int)extraSpaceGiven;
                        }
                    }

                    lines.add(builder.toString());
                    builder = new StringBuilder();
                    lastWordIdx = i - 1;
                    curLineSize = 0;
                }
            }
            else if (curWordLen + curLineSize == lineLength) { // a perfect fit
                // TODO: add all words to curLine with a single space
                for (int j = lastWordIdx; j < i; j++) {
                    builder.append(words.get(j));
                    if (i < words.size() - 1)
                        builder.append(" ");
                }

                builder.append(words.get(i));
                lines.add(builder.toString());
                builder = new StringBuilder();
                lastWordIdx = i + 1;
                curLineSize = 0;
            }
            else {
                int numWords = i - lastWordIdx;
                int extraSpace = lineLength - curLineSize;
                float extraSpacePerWord =  (float)extraSpace / (float)(numWords - 1);
                float extraSpaceGiven = 0;
                // interesting case: how to evenly space out the extra space
                for (int j = lastWordIdx; j < i; j++) {
                	if (j == i - 1) {
                		String spaces = StringUtils.repeat(' ', lineLength - (builder.toString().length() + words.get(j).length()));
                		builder.append(spaces);
                		builder.append(words.get(j));
                	}
                	else {
	                    builder.append(words.get(j));
	                    builder.append(" "); // TODO compute right amount of space
	
	                    extraSpaceGiven += extraSpacePerWord;
	
	                    if (((int)extraSpaceGiven) > 0) {
	                        for (int k = 0; k < (int)extraSpaceGiven; k++) {
	                            builder.append(" ");
	                        }
	                        extraSpaceGiven -= (int)extraSpaceGiven;
	                    }
                	}
                }

                lines.add(builder.toString());
                builder = new StringBuilder();
                
                if (i == words.size() - 1) {
                    if (words.get(i).length() < lineLength)
                	    lines.add(words.get(i) + StringUtils.repeat(' ', (lineLength - words.get(i).length())));
                    else
                        lines.add(words.get(i));
                }
                
                lastWordIdx = i;
                curLineSize = curWordLen == lineLength ? curWordLen : curWordLen + 1;
            }

        }

        return (lines);
    }

    public static List<String>  leftRightTextJustification (String [] words, int lineLength) {
        if (words == null)
            return (null);

        ArrayList<String> lines = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();

        int     curLineSize = 0;
        int     lastWordIdx = 0;
        for (int i = 0; i < words.length; i++) {
            String curWord = words [i];

            int curWordLen = curWord.length();

            if (i < words.length - 1) {
                if (curWordLen + curLineSize < lineLength - 1)
                    curWordLen += 1; // need a space if we're not about to fill the line
            }
            if (curWordLen + curLineSize < lineLength) {
                curLineSize += curWordLen;

                if (i == words.length - 1) {
                    for (int j = lastWordIdx; j < i + 1; j++) {
                        if (j == i) {
                            String spaces = StringUtils.repeat(' ', lineLength - (builder.toString().length() + words [j].length()));
                            builder.append(words [j]);
                            builder.append(spaces);
                        }
                        else {
                            builder.append(words [j]);
                            builder.append(" ");
                        }
                    }

                    lines.add(builder.toString());
                    builder = new StringBuilder();
                    lastWordIdx = i - 1;
                    curLineSize = 0;
                }
            }
            else if (curWordLen + curLineSize == lineLength) { // a perfect fit
                // TODO: add all words to curLine with a single space
                for (int j = lastWordIdx; j < i; j++) {
                    builder.append(words [j]);
                    if (i < words.length - 1)
                        builder.append(" ");
                }

                builder.append(words [i]);
                lines.add(builder.toString());
                builder = new StringBuilder();
                lastWordIdx = i + 1;
                curLineSize = 0;
            }
            else {
                arrangeLine(words, lastWordIdx, i, lineLength, builder, curLineSize);

                lines.add(builder.toString());
                builder = new StringBuilder();

                if (i == words.length - 1) {
                    if (words[i].length() < lineLength)
                        lines.add(words[i] + StringUtils.repeat(' ', (lineLength - words[i].length())));
                    else
                        lines.add(words[i]);
                }

                lastWordIdx = i;
                curLineSize = curWordLen == lineLength ? curWordLen : curWordLen + 1;
            }

        }

        return (lines);
    }

    static void arrangeLine (String [] words, int lastWordIdx, int wordIdx, int lineLength, StringBuilder builder, int curLineSize) {
        int numWords = wordIdx - lastWordIdx;
        int extraSpace = lineLength - curLineSize;
        float extraSpacePerWord =  (float)extraSpace / (float)(numWords - 1);
        float extraSpaceGiven = 0;
        if (numWords == 1) {
            String spaces = StringUtils.repeat(' ', lineLength - (builder.toString().length() + words [lastWordIdx].length()));
            builder.append(words [lastWordIdx]);
            builder.append(spaces);
            return;
        }
        // interesting case: how to evenly space out the extra space
        for (int j = lastWordIdx; j < wordIdx; j++) {
            if (j == wordIdx - 1) {
                String spaces = StringUtils.repeat(' ', lineLength - (builder.toString().length() + words [j].length()));
                builder.append(spaces);
                builder.append(words [j]);
            }
            else {
                builder.append(words [j]);
                builder.append(" "); // TODO compute right amount of space

                extraSpaceGiven += extraSpacePerWord;

                int roundedExtra = Math.round(extraSpaceGiven);
                if (roundedExtra > 0) {
                    for (int k = 0; k < roundedExtra; k++) {
                        builder.append(" ");
                    }
                    extraSpaceGiven -= roundedExtra;
                }
            }
        }
    }

    @InterestingAlgorithm
    public static boolean isMatch (String s, String pattern) {
        // ? matches any character
        // * matches any sequence of characters including empty
        return (false); // TODO
    }
    
    public static String	toEnglish (int value) {
    	return (null); // TODO
    }

    @InterestingAlgorithm
    public static int       numWords (String s) {
        if (s == null)
            return (0);

        boolean startedWord = false;
        int     numWords = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ' || c == '\t') {
                if (startedWord) {
                    startedWord = false;
                    numWords++;
                }
            }
            else {
                if (!startedWord)
                    startedWord = true;
            }

        }
        if (startedWord) {
            numWords++;
        }
        return (numWords);
    }


    /**
     * Returns min length word transformation ladder with given dictionary, or null if not possible
     * @param start
     * @param end
     * @param dictionary
     * @return
     */
    @InterestingAlgorithm
    public static List<String>  minWordLadder (String start, String end, List<String> dictionary) {
        List<String> ret = null;

        // TODO
        // Only one letter can be changed at a time
        // perhaps a recursive approach and take min length of solutions?
        return (ret);
    }


    /**
     *
     * @param value
     * @return
     */
    @InterestingAlgorithm
    public static String toRomanNumeral (int value) {
        return (null); // TODO
    }

    /**
     *
     * @param numeral
     * @return
     */
    @InterestingAlgorithm
    public static int fromRomanNumeral (String numeral) {
        return (0); // TODO
    }

    /**
     * 123 = one hundred twenty three
     *
     * 123 350 one hundred twenty three thousand three hundred fifty
     * @param value
     * @return
     */
    @InterestingAlgorithm
    public static String spelledOutInEnglish (int value) {
        if (value < 20)
            return (NUM_WORDS[value]);
        else if (value < 100) {
            String ret = BIGGER_NUM_WORDS[value / 10];
            if (value % 10 != 0)
                ret += " " + NUM_WORDS[value % 10];
        }
        else {
            // hundreds
            // thousands
            // millions
            // billions
        }

        return (null); // TODO
    }

    private static String [] NUM_WORDS = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static String [] BIGGER_NUM_WORDS = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety", "hundred", "thousand", "million", "billion"};

    /**
     * Makes sure any expression involving (, ), [, ], {, or } is balanced.
     * @param value
     * @return
     */
    public static boolean validParens (String value) {

        if (value == null)
            return (true);

        Stack<Character> seenParens = new Stack<Character>();

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '(' || c == '{' || c == '[')
                seenParens.push(c);
            else if (c == ')') {
                if (seenParens.empty())
                    return (false);
                else {
                    char matched = seenParens.pop();
                    if (matched != '(')
                        return (false);
                }
            }
            else if (c == '}') {
                    if (seenParens.empty())
                        return (false);
                    else {
                        char matched = seenParens.pop();
                        if (matched != '{')
                            return (false);
                    }
            }
            else if (c == ']') {
                if (seenParens.empty())
                    return (false);
                else {
                    char matched = seenParens.pop();
                    if (matched != '[')
                        return (false);
                }
            }

        }

        return (seenParens.isEmpty());

    }
}
