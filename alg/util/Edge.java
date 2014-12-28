package alg.util;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Edge<T> {

    private boolean directed;
    private T value;
    private Vertex  start;
    private Vertex end;

    public Edge (boolean directed, T value, Vertex start, Vertex end) {
    	this.directed = directed;
    	this.value = value;
    	this.start = start;
    	this.end = end;
    }
    
    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    public String toString () {
    	return ("Edge: " + value);
    }
}
