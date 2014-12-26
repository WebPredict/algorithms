package alg.graph;

import alg.util.Edge;
import alg.util.Vertex;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/26/14
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Graph {

    private List<Edge>  edges;
    private List<Vertex> vertices;

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }
}
