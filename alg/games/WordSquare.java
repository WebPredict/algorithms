package alg.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 2/6/15
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class WordSquare {

    private List<String> sizedWords;
    private int size;

    public WordSquare(List<String> dictionary, int size) {
        this.size = size;

        sizedWords = new ArrayList<String>();
        for (String s : dictionary) {
            if (s.length() == size)
                sizedWords.add(s);
        }
    }

    public void generate () {

        char [][] square = new char[size][size];

        /**
         * Approach:
         *
         * pick word at random to start.
         * Need a trie to do this efficiently
         *
         */

        int rowIdx = 0;
        int colIdx = 0;

        ArrayList<String> wordsToTry = new ArrayList<String>();
        wordsToTry.addAll(sizedWords);       // TODO can we avoid this copy?

        while (rowIdx < size && colIdx < size && wordsToTry.size() > 0) {

            if (rowIdx == 0) {
                int nextIdx = new Random().nextInt(wordsToTry.size());

                String word = wordsToTry.remove(nextIdx);

                for (int i = 0; i < size; i++) {
                    square [rowIdx][i] = word.charAt(i);
                }
            }
            else {
                for (int i = 0; i < size; i++) {
                    String prefix = "";
                    for (int j = 0; j < rowIdx + 1; j++) {
                        prefix += square[j][i];
                    }

                }
            }
        }
    }
}
