package alg.graph;

import alg.misc.InterestingAlgorithm;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphUtils {
    /**
     * Ford-fulkerson
     * flow algorithms
     * clustering algorithms
     */

    @InterestingAlgorithm(timeComplexity = "O(V + E)", spaceComplexity = "O(V)")
    public static void breadthFirstSearch (Graph input, VertexVisitor visitor) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null)
            return;

        HashSet<Vertex> seen = new HashSet<Vertex>();
        LinkedList<Vertex> queue = new LinkedList<Vertex>();

        for (int i = 0; i < vertexes.size(); i++) {
            Vertex current = vertexes.get(i);

            if (!seen.contains(current)) {
                queue.add(current);
                seen.add(current);

                while (!queue.isEmpty()) {
                    Vertex v = queue.poll();
                    visitor.visit(v);

                    Vertex [] neighbors = v.getNeighbors();
                    if (neighbors != null) {
                        for (int j = 0; j < neighbors.length; j++) {
                            if (!seen.contains(neighbors [j])) {
                                seen.add(neighbors [j]);
                                queue.add(neighbors [j]);
                            }
                        }
                    }
                }
            }
        }
    }

    @InterestingAlgorithm(timeComplexity = "O(V + E)", spaceComplexity = "O(V)")
    public static void depthFirstSearch (Graph input, VertexVisitor visitor) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null)
            return;

        HashSet<Vertex> seen = new HashSet<Vertex>();

        for (int i = 0; i < vertexes.size(); i++) {
            Vertex current = vertexes.get(i);

            if (!seen.contains(current)) {
                dfsHelper(current, seen, visitor);
            }
        }
    }

    private static void dfsHelper (Vertex current, HashSet<Vertex> seen, VertexVisitor visitor) {

        seen.add(current);
        visitor.visit(current);

        Vertex [] neighbors = current.getNeighbors();
        if (neighbors != null) {
            for (int i = 0; i < neighbors.length; i++) {
                if (!seen.contains(neighbors [i])) {
                    dfsHelper(neighbors [i], seen, visitor);
                }
            }
        }
    }

    @InterestingAlgorithm(timeComplexity = "O((V + E) log V)", spaceComplexity = "O(V)")
    public static Map<Vertex, Integer> dijkstraShortestPath (Graph input, Vertex source) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null)
            return (new HashMap<Vertex, Integer>());

        Map<Vertex, Integer> distances = new HashMap<Vertex, Integer>();
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(vertexes.size(), new Comparator<Vertex>() {
            public int compare (Vertex a, Vertex b) {
                int distA = distances.containsKey(a) ? distances.get(a) : Integer.MAX_VALUE;
                int distB = distances.containsKey(b) ? distances.get(b) : Integer.MAX_VALUE;
                return (Integer.compare(distA, distB));
            }
        });

        HashSet<Vertex> visited = new HashSet<Vertex>();

        for (int i = 0; i < vertexes.size(); i++) {
            distances.put(vertexes.get(i), Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        pq.add(source);

        while (!pq.isEmpty()) {
            Vertex current = pq.poll();

            if (visited.contains(current))
                continue;

            visited.add(current);

            Vertex [] neighbors = current.getNeighbors();
            if (neighbors != null) {
                for (int i = 0; i < neighbors.length; i++) {
                    Vertex neighbor = neighbors [i];
                    if (!visited.contains(neighbor)) {
                        int edgeWeight = current.getDistanceToNeighbor(neighbor);
                        int newDist = distances.get(current) + edgeWeight;
                        if (newDist < distances.get(neighbor)) {
                            distances.put(neighbor, newDist);
                            pq.add(neighbor);
                        }
                    }
                }
            }
        }

        return (distances);
    }

    @InterestingAlgorithm(timeComplexity = "O(V * E)", spaceComplexity = "O(V)")
    public static Map<Vertex, Integer> bellmanFord (Graph input, Vertex source) {

        List<Vertex> vertexes = input.getVertices();
        List<Edge> edges = input.getEdges();
        if (vertexes == null)
            return (new HashMap<Vertex, Integer>());

        Map<Vertex, Integer> distances = new HashMap<Vertex, Integer>();

        for (int i = 0; i < vertexes.size(); i++) {
            distances.put(vertexes.get(i), Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        int vertexCount = vertexes.size();

        for (int i = 0; i < vertexCount - 1; i++) {
            for (int j = 0; j < edges.size(); j++) {
                Edge edge = edges.get(j);
                Vertex u = edge.getStart();
                Vertex v = edge.getEnd();
                Integer weight = (Integer) edge.getValue();
                int distU = distances.get(u);

                if (distU != Integer.MAX_VALUE && distU + weight < distances.get(v)) {
                    distances.put(v, distU + weight);
                }

                if (!edge.isDirected()) {
                    int distV = distances.get(v);
                    if (distV != Integer.MAX_VALUE && distV + weight < distances.get(u)) {
                        distances.put(u, distV + weight);
                    }
                }
            }
        }

        return (distances);
    }

    @InterestingAlgorithm(timeComplexity = "O(V + E)", spaceComplexity = "O(V)")
    public static List<Vertex> topologicalSort (Graph input) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null)
            return (new ArrayList<Vertex>());

        HashSet<Vertex> seen = new HashSet<Vertex>();
        LinkedList<Vertex> result = new LinkedList<Vertex>();

        for (int i = 0; i < vertexes.size(); i++) {
            Vertex current = vertexes.get(i);
            if (!seen.contains(current)) {
                topologicalSortHelper(current, seen, result);
            }
        }

        return (result);
    }

    private static void topologicalSortHelper (Vertex current, HashSet<Vertex> seen, LinkedList<Vertex> result) {

        seen.add(current);

        Vertex [] neighbors = current.getNeighbors();
        if (neighbors != null) {
            for (int i = 0; i < neighbors.length; i++) {
                if (!seen.contains(neighbors [i])) {
                    topologicalSortHelper(neighbors [i], seen, result);
                }
            }
        }

        result.addFirst(current);
    }

    @InterestingAlgorithm(timeComplexity = "O(V + E)", spaceComplexity = "O(V)")
    public static boolean hasCycle (Graph input) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null)
            return (false);

        // 0 = unvisited, 1 = in current stack, 2 = fully processed
        HashMap<Vertex, Integer> state = new HashMap<Vertex, Integer>();

        for (int i = 0; i < vertexes.size(); i++) {
            state.put(vertexes.get(i), 0);
        }

        for (int i = 0; i < vertexes.size(); i++) {
            Vertex current = vertexes.get(i);
            if (state.get(current) == 0) {
                if (hasCycleHelper(current, state)) {
                    return (true);
                }
            }
        }

        return (false);
    }

    private static boolean hasCycleHelper (Vertex current, HashMap<Vertex, Integer> state) {

        state.put(current, 1);

        Vertex [] neighbors = current.getNeighbors();
        if (neighbors != null) {
            for (int i = 0; i < neighbors.length; i++) {
                Vertex neighbor = neighbors [i];
                int neighborState = state.get(neighbor);

                if (neighborState == 1) {
                    return (true);
                }

                if (neighborState == 0 && hasCycleHelper(neighbor, state)) {
                    return (true);
                }
            }
        }

        state.put(current, 2);
        return (false);
    }

    @InterestingAlgorithm(timeComplexity = "O(E log E)", spaceComplexity = "O(V)")
    public static List<Edge> kruskalMST (Graph input) {

        List<Edge> edges = input.getEdges();
        List<Vertex> vertexes = input.getVertices();
        if (edges == null || vertexes == null)
            return (new ArrayList<Edge>());

        List<Edge> sortedEdges = new ArrayList<Edge>(edges);
        Collections.sort(sortedEdges, new Comparator<Edge>() {
            public int compare (Edge a, Edge b) {
                return (Integer.compare((Integer) a.getValue(), (Integer) b.getValue()));
            }
        });

        // Simple union-find using maps
        HashMap<Vertex, Vertex> parent = new HashMap<Vertex, Vertex>();
        HashMap<Vertex, Integer> rank = new HashMap<Vertex, Integer>();

        for (int i = 0; i < vertexes.size(); i++) {
            parent.put(vertexes.get(i), vertexes.get(i));
            rank.put(vertexes.get(i), 0);
        }

        List<Edge> result = new ArrayList<Edge>();

        for (int i = 0; i < sortedEdges.size(); i++) {
            Edge edge = sortedEdges.get(i);
            Vertex rootStart = find(parent, edge.getStart());
            Vertex rootEnd = find(parent, edge.getEnd());

            if (rootStart != rootEnd) {
                result.add(edge);
                union(parent, rank, rootStart, rootEnd);
            }
        }

        return (result);
    }

    private static Vertex find (HashMap<Vertex, Vertex> parent, Vertex v) {

        if (parent.get(v) != v) {
            parent.put(v, find(parent, parent.get(v)));
        }
        return (parent.get(v));
    }

    private static void union (HashMap<Vertex, Vertex> parent, HashMap<Vertex, Integer> rank, Vertex a, Vertex b) {

        int rankA = rank.get(a);
        int rankB = rank.get(b);

        if (rankA < rankB) {
            parent.put(a, b);
        } else if (rankA > rankB) {
            parent.put(b, a);
        } else {
            parent.put(b, a);
            rank.put(a, rankA + 1);
        }
    }

    @InterestingAlgorithm(timeComplexity = "O((V + E) log V)", spaceComplexity = "O(V)")
    public static List<Edge> primMST (Graph input) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null || vertexes.isEmpty())
            return (new ArrayList<Edge>());

        HashSet<Vertex> inMST = new HashSet<Vertex>();
        List<Edge> result = new ArrayList<Edge>();

        // Priority queue of edges, sorted by weight
        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {
            public int compare (Edge a, Edge b) {
                return (Integer.compare((Integer) a.getValue(), (Integer) b.getValue()));
            }
        });

        Vertex start = vertexes.get(0);
        inMST.add(start);
        addEdgesToQueue(start, pq, inMST);

        while (!pq.isEmpty() && inMST.size() < vertexes.size()) {
            Edge edge = pq.poll();
            Vertex end = edge.getEnd();

            if (inMST.contains(end)) {
                // For undirected edges, try the other end
                if (!edge.isDirected() && !inMST.contains(edge.getStart())) {
                    end = edge.getStart();
                } else {
                    continue;
                }
            }

            inMST.add(end);
            result.add(edge);
            addEdgesToQueue(end, pq, inMST);
        }

        return (result);
    }

    private static void addEdgesToQueue (Vertex v, PriorityQueue<Edge> pq, HashSet<Vertex> inMST) {

        Edge [] edges = v.getEdges();
        if (edges != null) {
            for (int i = 0; i < edges.length; i++) {
                Vertex other = edges [i].getEnd();
                if (!edges [i].isDirected() && inMST.contains(other)) {
                    other = edges [i].getStart();
                }
                if (!inMST.contains(other)) {
                    pq.add(edges [i]);
                }
            }
        }
    }

    @InterestingAlgorithm(timeComplexity = "O(V + E)", spaceComplexity = "O(V)")
    public static List<List<Vertex>> stronglyConnectedComponents (Graph input) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null)
            return (new ArrayList<List<Vertex>>());

        // Kosaraju's algorithm
        // Step 1: Fill order by finish time using DFS
        HashSet<Vertex> seen = new HashSet<Vertex>();
        LinkedList<Vertex> stack = new LinkedList<Vertex>();

        for (int i = 0; i < vertexes.size(); i++) {
            if (!seen.contains(vertexes.get(i))) {
                sccFillOrder(vertexes.get(i), seen, stack);
            }
        }

        // Step 2: Build reverse adjacency map
        HashMap<Vertex, List<Vertex>> reverseAdj = new HashMap<Vertex, List<Vertex>>();
        for (int i = 0; i < vertexes.size(); i++) {
            reverseAdj.put(vertexes.get(i), new ArrayList<Vertex>());
        }
        for (int i = 0; i < vertexes.size(); i++) {
            Vertex v = vertexes.get(i);
            Vertex [] neighbors = v.getNeighbors();
            if (neighbors != null) {
                for (int j = 0; j < neighbors.length; j++) {
                    reverseAdj.get(neighbors [j]).add(v);
                }
            }
        }

        // Step 3: Process vertices in reverse finish order on transposed graph
        seen.clear();
        List<List<Vertex>> result = new ArrayList<List<Vertex>>();

        while (!stack.isEmpty()) {
            Vertex v = stack.pop();
            if (!seen.contains(v)) {
                List<Vertex> component = new ArrayList<Vertex>();
                sccDFS(v, seen, reverseAdj, component);
                result.add(component);
            }
        }

        return (result);
    }

    private static void sccFillOrder (Vertex current, HashSet<Vertex> seen, LinkedList<Vertex> stack) {

        seen.add(current);

        Vertex [] neighbors = current.getNeighbors();
        if (neighbors != null) {
            for (int i = 0; i < neighbors.length; i++) {
                if (!seen.contains(neighbors [i])) {
                    sccFillOrder(neighbors [i], seen, stack);
                }
            }
        }

        stack.push(current);
    }

    private static void sccDFS (Vertex current, HashSet<Vertex> seen, HashMap<Vertex, List<Vertex>> reverseAdj, List<Vertex> component) {

        seen.add(current);
        component.add(current);

        List<Vertex> neighbors = reverseAdj.get(current);
        if (neighbors != null) {
            for (int i = 0; i < neighbors.size(); i++) {
                if (!seen.contains(neighbors.get(i))) {
                    sccDFS(neighbors.get(i), seen, reverseAdj, component);
                }
            }
        }
    }

    @InterestingAlgorithm(timeComplexity = "O(E log V)", spaceComplexity = "O(V)")
    public static Graph minimumSpanningTree (Graph input) {
        // Kruskal's algorithm - need set utils for disjoint set data structures
        return (null); // TODO
    }

    @InterestingAlgorithm(timeComplexity = "O(V + E)", spaceComplexity = "O(V)")
    public static List<Graph> separateIntoClusters (Graph input) {
        return (null); // TODO
    }

    @InterestingAlgorithm(timeComplexity = "O(V * (V + E))", spaceComplexity = "O(V)")
    public static int computeDiameter (Graph input) {
        return (0); // TODO
    }
}
