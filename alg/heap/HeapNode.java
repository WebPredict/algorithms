package alg.heap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/22/15
 * Time: 9:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class HeapNode<T> {

    private HeapNode<T> parent;
    private HeapNode<T> left;
    private HeapNode<T> right;
    private T data;

    public HeapNode (T data) {
        this.data = data;
    }

    public HeapNode (HeapNode<T> parent, T data) {
        this.parent = parent;
        this.data = data;
    }

    public void setParent (HeapNode<T> parent) {
        this.parent = parent;
    }

    public HeapNode<T>  getParent () {
        return (parent);
    }

    public void setData (T data) {
        this.data = data;
    }

    public T getData () {
        return (data);
    }

    public HeapNode<T> getLeft() {
        return left;
    }

    public void setLeft(HeapNode<T> left) {
        this.left = left;
        if (left != null)
            left.parent = this;
    }

    public HeapNode<T> getRight() {
        return right;
    }

    public void setRight(HeapNode<T> right) {
        this.right = right;
        if (right != null)
            right.parent = this;
    }
}
