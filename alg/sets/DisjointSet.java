package alg.sets;

import alg.misc.InterestingAlgorithm;

public class DisjointSet {

    private int [] parent;
    private int [] rank;
    private int componentCount;

    public DisjointSet (int size) {
        parent = new int [size];
        rank = new int [size];
        componentCount = size;

        for (int i = 0; i < size; i++) {
            parent [i] = i;
            rank [i] = 0;
        }
    }

    @InterestingAlgorithm(timeComplexity = "O(alpha(n))", spaceComplexity = "O(1)")
    public int find (int x) {
        if (parent [x] != x) {
            parent [x] = find(parent [x]);
        }
        return (parent [x]);
    }

    @InterestingAlgorithm(timeComplexity = "O(alpha(n))", spaceComplexity = "O(1)")
    public boolean union (int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY)
            return (false);

        if (rank [rootX] < rank [rootY]) {
            parent [rootX] = rootY;
        }
        else if (rank [rootX] > rank [rootY]) {
            parent [rootY] = rootX;
        }
        else {
            parent [rootY] = rootX;
            rank [rootX]++;
        }

        componentCount--;
        return (true);
    }

    @InterestingAlgorithm(timeComplexity = "O(alpha(n))", spaceComplexity = "O(1)")
    public boolean connected (int x, int y) {
        return (find(x) == find(y));
    }

    @InterestingAlgorithm(timeComplexity = "O(1)", spaceComplexity = "O(1)")
    public int getComponentCount () {
        return (componentCount);
    }
}
