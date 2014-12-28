package alg.routing;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

import alg.graph.Graph;
import alg.queues.MinPriorityQueue;
import alg.util.Vertex;

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
     *
     */
	
	/**
	 * 
	 * @param g
	 * @param source
	 * @return
	 */
	public static PathInfo	dijkstra (Graph g, Vertex source) {
		
		HashMap<Vertex, Integer> vertexDistanceMap = new HashMap<Vertex, Integer>();
		HashMap<Vertex, Vertex> vertexPathMap = new HashMap<Vertex, Vertex>();
		vertexDistanceMap.put(source, 0);
		
		// TODO: change to priority queue
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
		return (new PathInfo(vertexDistanceMap, vertexPathMap));	
	}
}
