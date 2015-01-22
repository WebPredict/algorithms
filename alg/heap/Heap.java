package alg.heap;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class Heap<T extends Comparable> {

    private HeapNode<T> root;

    public Heap () {
    }

    public T    extractMax () {
        if (root == null)
            return (null);

        HeapNode<T> ret = root;

        if (root.getLeft() == null) {
            root = null;
        }
        else if (root.getRight() == null) {
            root = root.getLeft();
        }
        else {
            T left = root.getLeft().getData();
            T right = root.getRight().getData();

            if (left.compareTo(right) < 0) {
                HeapNode<T> rightmost = removeRightmost(root.getLeft());
                root.setData(rightmost.getData());
            }
            else {
                HeapNode<T> leftmost = removeLeftmost(root.getRight());
                root.setData(leftmost.getData());
            }
            heapify();
        }

        return (root.getData());
    }

    private HeapNode<T>  removeRightmost(HeapNode<T> node) {
        if (node == null)
            return (null);
        else if (node.getRight() != null)
            return (removeRightmost(node.getRight()));
        else if (node.getLeft() != null)
            return (removeRightmost(node.getLeft()));
        else {
            HeapNode<T> parent = node.getParent();
            if (node == parent.getLeft())
                parent.setLeft(null);
            else
                parent.setRight(null);
            node.setParent(null);
            return (node);
        }
    }

    private HeapNode<T>  removeLeftmost(HeapNode<T> node) {
        if (node == null)
            return (null);
        else if (node.getLeft() != null)
            return (removeLeftmost(node.getLeft()));
        else if (node.getRight() != null)
            return (removeLeftmost(node.getRight()));
        else {
            HeapNode<T> parent = node.getParent();
            if (node == parent.getLeft())
                parent.setLeft(null);
            else
                parent.setRight(null);
            node.setParent(null);
            return (node);
        }
    }

    public void insert (T value) {
        pushDown(value);
    }

    private void pushDown (T data) {
        if (root == null)
            root = new HeapNode<T>(data);
        else {

            HeapNode<T> current = root;

            while (current.getLeft() != null && current.getRight() != null) {
                current = current.getLeft();
            }

            if (current.getLeft() == null) {
                current.setLeft(new HeapNode<T>(data));
            }
            else if (current.getRight() == null) {
                current.setRight(new HeapNode<T>(data));
            }

            HeapNode<T> parent = current.getParent();
            // Now: bubble up to root and swap if needed
            while (parent != null) {
                if ( current.getData().compareTo(parent.getData()) > 0) {
                    // need swap, then go up
                    T tmp = parent.getData();
                    parent.setData(current.getData());
                    current.setData(tmp);
                }

                current = parent;
                parent = parent.getParent();
            }

        }
    }

    private void heapify () {
        if (root != null) {
            HeapNode<T> current = root;

            // Find the right place to put it:
            while (current != null) {
                HeapNode<T> left = current.getLeft();
                HeapNode<T> right = current.getRight();

                if (left != null && current.getData().compareTo(left.getData()) < 0) {
                    // need swap, then go up
                    T tmp = left.getData();
                    left.setData(current.getData());
                    current.setData(tmp);
                    current = left;
                }
                else if (right !=null && current.getData().compareTo(right.getData()) < 0) {
                    // need swap, then go up
                    T tmp = right.getData();
                    right.setData(current.getData());
                    current.setData(tmp);
                    current = right;
                }
                else
                    break; // in the right place
            }

        }
    }

    public T    checkMax () {
        return (root == null ? null : root.getData());
    }
}
