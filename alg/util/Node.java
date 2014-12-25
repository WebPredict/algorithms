package alg.util;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node<T> {

    private T data;
    private Node<T> parent;
    private List<Node<T>> children;

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }
}
