package alg.routing;

import java.util.Map;

import alg.graph.Vertex;

public class PathInfo {

	private Map<Vertex, Integer> pathDistances;
	private Map<Vertex, Vertex> previousVertexPath;
	private Vertex pathStart;

	public PathInfo (Map<Vertex, Integer> pathDistances, Map<Vertex, Vertex> previousVertexPath, Vertex pathStart) {
		this.pathDistances = pathDistances;
		this.previousVertexPath = previousVertexPath;
        this.pathStart = pathStart;
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

    public Vertex getPathStart () {
        return (pathStart);
    }

    public String toString () {

        if (previousVertexPath == null)
            return ("NULL");
        else {
            Vertex previous = previousVertexPath.get(pathStart);
            StringBuilder builder = new StringBuilder();

            do {
                builder.append(previous.toString());
                builder.append(" -> ");
                builder.append("(" + pathDistances.get(previous) + ") ");
                previous = previousVertexPath.get(previous);
            }
            while (previous != null);
            return (builder.toString());
        }

    }
}
