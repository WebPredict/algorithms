package alg.util;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Vertex<T> {
	
	private Edge<Integer> []	edges;
	private T data;
	private Vertex [] neighbors;
	private HashMap<Vertex, Integer> neighborDistances;
	
	public Vertex (Edge<Integer> [] edges, T data) {
		
		if (edges != null) {
			neighbors = new Vertex[edges.length];
			for (int i = 0; i < edges.length; i++) {
				neighbors [i] = edges [i].getEnd();
			}
		}
	}
	
	public Vertex [] getNeighbors () {
		return (neighbors); 		
	}
	
	public Edge []	getEdges () {
		return (edges);
	}
	
	public int	getDistanceToNeighbor (Vertex v) {
		Integer	distance = neighborDistances.get(v);
		if (distance == null)
			throw new RuntimeException ("Not a neighbor: " + v);
		return (distance);
	}
	
	public String toString () {
		return ("Vertex: " + data);
	}
}
