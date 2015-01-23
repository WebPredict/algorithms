package alg.misc;

import alg.queues.MinPriorityQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackWithMin<T extends Comparable> {

    private List<T> list = new ArrayList<T>();
    private Stack<Integer> minIdxes = new Stack<Integer>();

    public StackWithMin () {
    }

    @InterestingAlgorithm
    public T    min () {
        if (minIdxes.empty())
            throw new RuntimeException("Empty stack");

        return (list.get(minIdxes.peek()));
    }

    @InterestingAlgorithm
    public T    pop () {
        int     size = list.size();
        if (size == 0)
            throw new RuntimeException("Empty stack");

        T value = list.remove(size - 1);

        if (minIdxes.peek() == size - 1) {
            minIdxes.pop();
        }
        return (value);
    }

    @InterestingAlgorithm
    public void push (T value) {
        if (minIdxes.empty())
            minIdxes.push(0);
        else {
            if (list.get(minIdxes.peek()).compareTo(value) > 0) {
                minIdxes.push(list.size());
            }
        }
        list.add(value);
    }
}
