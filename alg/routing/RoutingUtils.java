package alg.routing;

import java.util.HashMap;
import java.util.List;

import alg.graph.Graph;
import alg.misc.InterestingAlgorithm;
import alg.queues.MinPriorityQueue;
import alg.graph.Vertex;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoutingUtils {

    /**
     * Traveling salesman
     * Vehicle routing problem
     * Dijkstra's
     */
	
	/**
	 * Single source shortest path, with non-negative integer value distances
	 * @param g
	 * @param source
	 * @return
	 */
    @InterestingAlgorithm
	public static PathInfo	dijkstra (Graph g, Vertex source) {
		
		HashMap<Vertex, Integer> vertexDistanceMap = new HashMap<Vertex, Integer>();
		HashMap<Vertex, Vertex> vertexPathMap = new HashMap<Vertex, Vertex>();
		vertexDistanceMap.put(source, 0);
		
		MinPriorityQueue<Vertex> 	queue = new MinPriorityQueue<Vertex>();
		
		for (Vertex v : g.getVertices()) {
			if (v != source) {
				vertexDistanceMap.put(v, Integer.MAX_VALUE);
				// don't need to set previous
			}
			queue.insertWithPriority(v, Integer.MAX_VALUE);
		}
		
		while (!queue.isEmpty()) {
			
			Vertex u = queue.getLowestPriority();
			Vertex [] neighbors = u.getNeighbors();
			
			if (neighbors != null) {
				for (Vertex neighbor : neighbors) {
					int altDistance = vertexDistanceMap.get(u);
					if (altDistance != Integer.MAX_VALUE) {
						altDistance += u.getDistanceToNeighbor(neighbor);
					}
					if (altDistance < vertexDistanceMap.get(neighbor)) {
						vertexDistanceMap.put(neighbor, altDistance);
						vertexPathMap.put(neighbor, u);
					}
				}
			}
			
		}
		return (new PathInfo(vertexDistanceMap, vertexPathMap, source));
	}

    /**
     *  Uses heuristics to come up with 95% optimal solution in n^2 time?
     * @param g
     * @param source
     * @return
     */
    @InterestingAlgorithm
    public static PathInfo  travelingSalesman (List<Vertex> toVisit) {
        if (toVisit == null)
            return (null);

        // Naive approach: just pick the next closest city that's in the list


        return (null); // TODO
    }
}
