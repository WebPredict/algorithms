package test.alg.words;

import alg.io.FileUtils;
import alg.trees.Trie;
import alg.words.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/1/15
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordTest {

    public static void main (String [] args) throws Exception {

        testShortJustify();
        testLonger();

        testAutocorrect();

        System.out.println(WordUtils.reverseWords("  This   is a test"));
        System.out.println(WordUtils.reverseWords("  a   b"));
        System.out.println(WordUtils.reverseWords(" "));

    }

    public static void testShortJustify ()  {
        List<String> wordList = new ArrayList<String>();
        wordList.add("this");
        wordList.add("could");
        wordList.add("very");
        wordList.add("likely");
        wordList.add("be");
        wordList.add("a");
        wordList.add("nifty");
        wordList.add("test");
        wordList.add("indubitably"); 
        wordList.add("insufficiently");


        List<String> lines = WordUtils.leftRightTextJustification(new String[] {"this", "is"}, 8);

        lines = WordUtils.leftRightTextJustification(new String[] {""}, 2);

        int lineLength = 20;
        lines = WordUtils.leftRightTextJustification(wordList, lineLength);

        for (int i = 0; i < lines.size() - 1; i++) {
        	assert(lines.get(i).length() == lineLength);
        }
        assert(lines.size() == 4);
        
        String [] words = new String("this is a very long sentence that will be undubitably good for testing. I'm very confident it will be a great test of things.").split(" ");

        lines = WordUtils.leftRightTextJustification(Arrays.asList(words), lineLength);
        
        for (int i = 0; i < lines.size() - 1; i++) {
        	//assert(lines.get(i).length() == lineLength);
        	System.out.println(lines.get(i) + "|length: " + lines.get(i).length());
        }
    }

    public static void testLonger () {
        String text = "Welcome, dear Rosencrantz and Guildenstern!\n" +
                "Moreover that we much did long to see you,\n" +
                "The need we have to use you did provoke\n" +
                "Our hasty sending. Something have you heard\n" +
                "Of Hamlet's transformation; so call it,\n" +
                "Sith nor the exterior nor the inward man\n" +
                "Resembles that it was. What it should be,\n" +
                "More than his father's death, that thus hath put him\n" +
                "So much from the understanding of himself,\n" +
                "I cannot dream of: I entreat you both,\n" +
                "That, being of so young days brought up with him,\n" +
                "And sith so neighbour'd to his youth and havior,\n" +
                "That you vouchsafe your rest here in our court\n" +
                "Some little time: so by your companies\n" +
                "To draw him on to pleasures, and to gather,\n" +
                "So much as from occasion you may glean,\n" +
                "Whether aught, to us unknown, afflicts him thus,\n" +
                "That, open'd, lies within our remedy.\n";

        System.out.println();
        System.out.println("ORIGINAL TEXT: ");
        System.out.println();
        System.out.println(text);

        List<String> lines = WordUtils.leftRightTextJustificationParagraphs(Arrays.asList(text), 50);

        System.out.println();
        System.out.println();
        System.out.println("LEFT + RIGHT JUSTIFIED:");
        System.out.println();
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(lines.get(i));
        }
    }


    public static void testAutocorrect () throws Exception {

        List<String> dictionary = FileUtils.getLines("C:/Users/jsanchez/Downloads/mostcommonwords.csv");

        float frequency = .1f;

        HashMap<String, Float> wordtoFrequencyMap = new HashMap<String, Float>();

        for (String word : dictionary) {
            wordtoFrequencyMap.put(word, frequency);

            if (frequency > .0001f)
                frequency = frequency * .99f;
        }

        Trie dictionaryTrie = new Trie(dictionary);
        HashMap<String, String> pastSuggestionsMap = new HashMap<String, String>();

        String word = "test";
        String correction = WordUtils.autocorrect(word, wordtoFrequencyMap, dictionaryTrie, pastSuggestionsMap);

        System.out.println("original: " + word + " correction: " + correction);
        pastSuggestionsMap.put(word, correction);

        word = "blah";
        correction = WordUtils.autocorrect(word, wordtoFrequencyMap, dictionaryTrie, pastSuggestionsMap);
        System.out.println("original: " + word + " correction: " + correction);

        word = "thr";
        correction = WordUtils.autocorrect(word, wordtoFrequencyMap, dictionaryTrie, pastSuggestionsMap);
        System.out.println("original: " + word + " correction: " + correction);

        word = "couldnt";
        correction = WordUtils.autocorrect(word, wordtoFrequencyMap, dictionaryTrie, pastSuggestionsMap);
        System.out.println("original: " + word + " correction: " + correction);

        word = "bother";
        correction = WordUtils.autocorrect(word, wordtoFrequencyMap, dictionaryTrie, pastSuggestionsMap);
        System.out.println("original: " + word + " correction: " + correction);

        pastSuggestionsMap.put(word, "bother");   // reject their suggestion of "both"

        // second time around it should be "learned"
        word = "bother";
        correction = WordUtils.autocorrect(word, wordtoFrequencyMap, dictionaryTrie, pastSuggestionsMap);
        System.out.println("original: " + word + " correction: " + correction);

        assert(word.equals(correction));
    }
}
