package alg.queues;

import alg.misc.InterestingAlgorithm;

import java.util.ArrayList;

public class MinPriorityQueue<T> implements Queue<T> {

    private static class Entry<T> implements Comparable<Entry<T>> {
        T value;
        int priority;

        Entry (T value, int priority) {
            this.value = value;
            this.priority = priority;
        }

        @Override
        public int compareTo (Entry<T> other) {
            return (Integer.compare(this.priority, other.priority));
        }
    }

    private ArrayList<Entry<T>> heap;

    public MinPriorityQueue () {
        this.heap = new ArrayList<>();
    }

    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public void insertWithPriority (T value, int priority) {
        heap.add(new Entry<>(value, priority));
        bubbleUp(heap.size() - 1);
    }

    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public T getLowestPriority () {
        if (heap.isEmpty())
            return (null);

        Entry<T> min = heap.get(0);
        int lastIdx = heap.size() - 1;
        heap.set(0, heap.get(lastIdx));
        heap.remove(lastIdx);

        if (!heap.isEmpty())
            heapifyDown(0);

        return (min.value);
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public T peekLowestPriority () {
        if (heap.isEmpty())
            return (null);
        return (heap.get(0).value);
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public boolean isEmpty () {
        return (heap.isEmpty());
    }

    @Override
    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public T getFirst () {
        return (getLowestPriority());
    }

    @Override
    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public void insert (T value) {
        insertWithPriority(value, 0);
    }

    @Override
    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public T peekFirst () {
        return (peekLowestPriority());
    }

    private void bubbleUp (int index) {
        while (index > 0) {
            int parentIdx = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIdx)) < 0) {
                swap(index, parentIdx);
                index = parentIdx;
            }
            else {
                break;
            }
        }
    }

    private void heapifyDown (int index) {
        int size = heap.size();

        while (true) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < size && heap.get(left).compareTo(heap.get(smallest)) < 0)
                smallest = left;
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0)
                smallest = right;

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            }
            else {
                break;
            }
        }
    }

    private void swap (int i, int j) {
        Entry<T> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
