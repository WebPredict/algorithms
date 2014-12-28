package alg.routing;

import java.util.Map;

import alg.util.Vertex;

public class PathInfo {

	private Map<Vertex, Integer> pathDistances;
	private Map<Vertex, Vertex> previousVertexPath;
	
	public PathInfo (Map<Vertex, Integer> pathDistances, Map<Vertex, Vertex> previousVertexPath) {
		this.pathDistances = pathDistances;
		this.previousVertexPath = previousVertexPath;
	}
	
	public Map<Vertex, Integer> getPathDistances() {
		return pathDistances;
	}
	public void setPathDistances(Map<Vertex, Integer> pathDistances) {
		this.pathDistances = pathDistances;
	}
	public Map<Vertex, Vertex> getPreviousVertexPath() {
		return previousVertexPath;
	}
	public void setPreviousVertexPath(Map<Vertex, Vertex> previousVertexPath) {
		this.previousVertexPath = previousVertexPath;
	}
	
}
