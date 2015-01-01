package test.alg.words;

import alg.words.WordUtils;

import java.util.ArrayList;
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

        List<String> lines = WordUtils.textJustification(wordList, 20);

        assert(lines.size() == 3);
    }
}
