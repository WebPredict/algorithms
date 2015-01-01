package test.alg.words;

import alg.words.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/1/15
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordTest {

    public static void main (String [] args) {

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

        int lineLength = 20;
        List<String> lines = WordUtils.textJustification(wordList, lineLength);

        for (int i = 0; i < lines.size() - 1; i++) {
        	assert(lines.get(i).length() == lineLength);
        }
        assert(lines.size() == 4);
        
        String [] words = new String("this is a very long sentence that will be undubitably good for testing. I'm very confident it will be a great test of things.").split(" ");

        lines = WordUtils.textJustification(Arrays.asList(words), lineLength);
        
        for (int i = 0; i < lines.size() - 1; i++) {
        	//assert(lines.get(i).length() == lineLength);
        	System.out.println(lines.get(i) + "|length: " + lines.get(i).length());
        }
    }
}
