package alg.util;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryNode<T extends Comparable> {

    private T   data;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    public BinaryNode (T data) {
        this.data = data;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString () {
        return (data + " left->" + left + " right->" + right);
    }

    @SuppressWarnings("unchecked")
    public void insert (BinaryNode<T> newNode) {
        if (newNode == null)
            return;

        if (newNode.data.compareTo(this.data) <= 0) {
            if (left == null)
                left = newNode;
            else
                left.insert(newNode);
        }
        else {
            if (right == null)
                right = newNode;
            else
                right.insert(newNode);
        }
    }
}
