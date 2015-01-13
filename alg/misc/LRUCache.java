package alg.misc;

import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/23/14
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class LRUCache {

    private int capacity;

    private PriorityQueue   queue;

    public LRUCache(int capacity) {
        queue = new PriorityQueue(capacity);
        this.capacity = capacity;
    }

    public int get (int key) {
        // return -1 if doesn't exist
        return (0); // TODO
    }

    public void set (int key, int value) {
        // TODO: add to queue with priority as timestamp
    }
}
