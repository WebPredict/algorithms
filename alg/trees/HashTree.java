package alg.trees;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class HashTree<T extends Comparable> {

    /**
     * aka Merkle tree
     * Hash of concatenated childen's hashes all the way up to the root
     */

    private T data;
    private int hash;
    private HashFunction hashFunction;

    public HashTree () {
    }

    public void insert (T value) {

    }

    public void remove (T value) {

    }

    public int getHash () {
        return (hash);
    }

    public static class HashFunction {
        public int hash (String value) {
            return (value.hashCode());
        }
    }

}
