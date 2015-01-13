package alg.misc;

import alg.queues.MinPriorityQueue;
import sun.rmi.log.LogInputStream;

import java.util.ArrayList;
import java.util.List;

public class StackWithMin<T extends Comparable> {

    private List<T> list = new ArrayList<T>();
    private int     minIdx = -1;

    public StackWithMin () {
    }

    @InterestingAlgorithm
    public T    min () {
        if (minIdx == -1)
            throw new RuntimeException("Empty stack");

        T value = list.remove(minIdx);
        return (value);
    }

    @InterestingAlgorithm
    public T    pop () {
        int     size = list.size();
        if (size == 0)
            throw new RuntimeException("Empty stack");

        T value = list.remove(size - 1);

        // TODO: can we easily eliminate this linear search?
        if (minIdx == size - 1) {
            T newMin = null;
            int newMinIdx = -1;
            for (int i = 0; i < list.size(); i++) {
                if (newMin == null) {
                    newMin = list.get(i);
                    newMinIdx = i;
                }
                else if (list.get(i).compareTo(newMin) < 0) {
                    newMin = list.get(i);
                    newMinIdx = i;
                }
            }
            minIdx = newMinIdx;
        }
        return (value);
    }

    @InterestingAlgorithm
    public void push (T value) {
        if (minIdx == -1)
            minIdx = 0;
        else {
            if (list.get(minIdx).compareTo(value) > 0) {
                minIdx = list.size();
            }
        }
        list.add(value);
    }
}
