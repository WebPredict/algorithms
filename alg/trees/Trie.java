package alg.trees;

import alg.misc.InterestingAlgorithm;
import alg.util.Node;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Trie {

    private HashMap<Character, Trie> charToChildrenMap = new HashMap<Character, Trie>();
    private HashMap<String, Set<String>> wordsStartingWithCache = new HashMap<String, Set<String>>();

    /**
     * insert word
     * word exists?
     * all words starting with
     * remove word
     *
     */

    public Trie () { }

    public Trie (Set<String> words) {
        if (words != null) {
            for (String word : words) {
                insertWord(word);
            }
        }
    }

    public Trie (List<String> words) {
        if (words != null) {
            for (String word : words) {
                insertWord(word);
            }
        }
    }

    @InterestingAlgorithm
    public void insertWord (String word) {
    	if (word == null || word.length() == 0)
            return;

        char c = word.charAt(0);

        Trie subtrie = charToChildrenMap.get(c);
        if (subtrie == null) {
            subtrie = new Trie();
            charToChildrenMap.put(c, subtrie);
        }
        subtrie.insertWord(word.substring(1));

        wordsStartingWithCache.remove(word);
    }

    @InterestingAlgorithm
    public boolean wordExists (String word) {
        if (word == null || word.length() == 0)
            return (true);

        char c = word.charAt(0);

        Trie subtrie = charToChildrenMap.get(c);

        if (subtrie == null)
            return (false);
        else
            return (subtrie.wordExists(word.substring(1)));

    }

    @InterestingAlgorithm
    public Set<String> wordsStartingWith (String prefix) {

        if (wordsStartingWithCache.containsKey(prefix)) {
            return (wordsStartingWithCache.get(prefix));
        }

        Set<String> set = new HashSet<String>();
        if (prefix == null || prefix.length() == 0) {

            Set<Character> keys = charToChildrenMap.keySet();

            for (Character key : keys) {

                Set<String> moreWords = charToChildrenMap.get(key).wordsStartingWith(prefix.substring(1));

                if (moreWords != null) {
                    for (String word : moreWords) {
                       set.add(key + word);
                    }
                }
            }
            return (null);
        }

        wordsStartingWithCache.put(prefix, set);
        return (set);
    }

    public void removeWord (String word) {
    	// TODO

        wordsStartingWithCache.remove(word);
    }

    public void removeWordsStartingWith (String prefix) {
    	// TODO

        wordsStartingWithCache.remove(prefix);
    }
}
