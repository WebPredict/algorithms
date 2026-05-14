package alg.linkedlist;

import alg.misc.InterestingAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class DoublyLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node (T data) {
            this.data = data;
        }

        public T getData () {
            return (data);
        }

        public Node<T> getPrev () {
            return (prev);
        }

        public Node<T> getNext () {
            return (next);
        }
    }

    public DoublyLinkedList () {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public void addFirst (T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public void addLast (T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public T removeFirst () {
        if (head == null)
            return (null);

        T data = head.data;
        if (head == tail) {
            head = null;
            tail = null;
        }
        else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return (data);
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public T removeLast () {
        if (tail == null)
            return (null);

        T data = tail.data;
        if (head == tail) {
            head = null;
            tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return (data);
    }

    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public boolean remove (T value) {
        Node<T> current = head;
        while (current != null) {
            if ((value == null && current.data == null) || (value != null && value.equals(current.data))) {
                if (current.prev != null)
                    current.prev.next = current.next;
                else
                    head = current.next;

                if (current.next != null)
                    current.next.prev = current.prev;
                else
                    tail = current.prev;

                size--;
                return (true);
            }
            current = current.next;
        }
        return (false);
    }

    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public boolean contains (T value) {
        Node<T> current = head;
        while (current != null) {
            if ((value == null && current.data == null) || (value != null && value.equals(current.data)))
                return (true);
            current = current.next;
        }
        return (false);
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public int size () {
        return (size);
    }

    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public T get (int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return (current.data);
    }

    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(1)")
    public void reverse () {
        Node<T> current = head;
        Node<T> temp = null;

        while (current != null) {
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev;
        }

        temp = head;
        head = tail;
        tail = temp;
    }

    @InterestingAlgorithm(timeComplexity = "O(n)", spaceComplexity = "O(n)")
    public List<T> toList () {
        List<T> result = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return (result);
    }
}
