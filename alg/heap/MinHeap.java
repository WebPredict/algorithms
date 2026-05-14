package alg.heap;

import alg.misc.InterestingAlgorithm;

import java.util.ArrayList;

public class MinHeap<T extends Comparable<T>> {

    private ArrayList<T> data;

    public MinHeap (int initialCapacity) {
        this.data = new ArrayList<>(initialCapacity);
    }

    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public void insert (T value) {
        data.add(value);
        bubbleUp(data.size() - 1);
    }

    @InterestingAlgorithm(timeComplexity = "O(log n)", spaceComplexity = "O(1)")
    public T extractMin () {
        if (data.isEmpty())
            return (null);

        T min = data.get(0);
        int lastIdx = data.size() - 1;
        data.set(0, data.get(lastIdx));
        data.remove(lastIdx);

        if (!data.isEmpty())
            heapifyDown(0);

        return (min);
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public T peek () {
        if (data.isEmpty())
            return (null);
        return (data.get(0));
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public int size () {
        return (data.size());
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public boolean isEmpty () {
        return (data.isEmpty());
    }

    private void bubbleUp (int index) {
        while (index > 0) {
            int parentIdx = (index - 1) / 2;
            if (data.get(index).compareTo(data.get(parentIdx)) < 0) {
                swap(index, parentIdx);
                index = parentIdx;
            }
            else {
                break;
            }
        }
    }

    private void heapifyDown (int index) {
        int size = data.size();

        while (true) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < size && data.get(left).compareTo(data.get(smallest)) < 0)
                smallest = left;
            if (right < size && data.get(right).compareTo(data.get(smallest)) < 0)
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
        T temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }
}
