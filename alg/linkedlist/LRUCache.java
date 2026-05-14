package alg.linkedlist;

import alg.misc.InterestingAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        Node (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> map;
    private Node<K, V> head;
    private Node<K, V> tail;

    public LRUCache (int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public V get (K key) {
        Node<K, V> node = map.get(key);
        if (node == null)
            return (null);

        removeNode(node);
        addToFront(node);
        return (node.value);
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public void put (K key, V value) {
        Node<K, V> existing = map.get(key);
        if (existing != null) {
            existing.value = value;
            removeNode(existing);
            addToFront(existing);
            return;
        }

        if (map.size() == capacity) {
            Node<K, V> lru = tail.prev;
            removeNode(lru);
            map.remove(lru.key);
        }

        Node<K, V> newNode = new Node<>(key, value);
        map.put(key, newNode);
        addToFront(newNode);
    }

    private void removeNode (Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToFront (Node<K, V> node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
