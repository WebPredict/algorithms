package alg.graph;

import alg.misc.InterestingAlgorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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


    @InterestingAlgorithm
    public static Graph minimumSpanningTree (Graph input) {
        // Kruskal's algorithm - need set utils for disjoint set data structures
        return (null); // TODO
    }

    @InterestingAlgorithm
    public static List<Graph> separateIntoClusters (Graph input)  {
        return (null); // TODO
    }

    @InterestingAlgorithm
    public static void depthFirstSearch (Graph input, VertexVisitor visitor) {

        List<Vertex> vertexes = input.getVertices();
        if (vertexes == null)
            return;

        HashSet<Vertex> seen = new HashSet<Vertex>();

       for (int i = 0; i < vertexes.size(); i++) {
           Vertex current = vertexes.get(i);

           if (!seen.contains(current)) {
               visitor.visit(current);
               seen.add(current);

               Vertex [] neighbors = current.getNeighbors();
               // TODO: go as deep as possible for each unseen neighbor
           }
       }

    }

    @InterestingAlgorithm
    public static int   computeDiameter (Graph input) {
        return (0); // TODO
    }

    @InterestingAlgorithm
    public static void breadthFirstSearch (Graph input, VertexVisitor visitor) {
    	// TODO
    }
    
}
