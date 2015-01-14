package alg.distributed;

import alg.misc.InterestingAlgorithm;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class DistributedHashTable<K, V> {

    @InterestingAlgorithm
    public V get (K key) {
        return (null); // TODO
    }

    @InterestingAlgorithm
    public void put (K key, V value) {
        // TODO
    }

    // TODO: how to indicate how this is distributed across machines, and across which machines?

    // Error detection, failover, etc.

    // key-based routing: each hope to the next machine should bring us closer to the machine with the value
    // duplicate key/value pairs for redundancy

}
