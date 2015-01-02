package alg.trees;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/2/15
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrieNode {

    private Character c;
    private HashMap<Character, TrieNode> charToChildrenMap = new HashMap<Character, TrieNode>();

    public TrieNode(Character c) {
        this.c = c;
    }

    public TrieNode get (Character theChar) {
        return (charToChildrenMap.get(theChar));
    }

    public void set (Character theChar, TrieNode tn) {
        charToChildrenMap.put(theChar, tn);
    }
}
