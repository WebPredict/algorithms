package alg.sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/14/15
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class DisjointSet<T extends Comparable> {

    private List<Set<T>> sets;

    public DisjointSet (List<Set<T>> initialDisjointSets) {
        // TODO: verify they're disjoint?
        this.sets = initialDisjointSets;
    }

    public List<Set<T>>    getSets () {
        return (sets);
    }

    public Set  find (T value) {
        // TODO improve

        if (sets != null) {
            for (Set<T> set : sets) {
                if (set.contains(value))
                    return (set);
            }
        }

        return (null);
    }

    public int  findIdx (T value) {
        // TODO improve

        if (sets != null) {
            for (int i = 0; i < sets.size(); i++) {
                if (sets.get(i).contains(value))
                    return (i);
            }
        }

        return (-1);
    }

    public Set  makeSetAndAdd (T value) {
        Set<T> set = new HashSet<T>();
        set.add(value);
        sets.add(set);
        return (set);
    }

    public boolean union (T repValue1, T repValue2) {
        int firstIdx = findIdx(repValue1);
        int secondIdx = findIdx(repValue2);
        if (firstIdx == -1 || secondIdx == -1)
            return (false);

        Set first = sets.get(firstIdx);
        Set second = sets.get(secondIdx);

        first.addAll(second);
        sets.remove(secondIdx);
        return (false);
    }

}
