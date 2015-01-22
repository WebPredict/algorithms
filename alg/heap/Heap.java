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

        /**
         * TODO: pull the greatest child from either root's left or right as the new root, then recursively do that
         */
        HeapNode<T> newRoot = root;
        while (newRoot != null) {
            HeapNode next = newRoot.getLeft();
            if (next == null) {
                HeapNode<T> tmp = newRoot;
                newRoot = next;
                tmp.setLeft(null);
                break;
            }
            newRoot = next;
        }

        if (newRoot == null) {
            root = null;
            return (null);
        }

        root.setData(newRoot.getData());
        HeapNode<T> current = root;

        while (true) {
           HeapNode<T> left = current.getLeft();
           HeapNode<T> right = current.getRight();

           if (left != null && left.getData().compareTo(current.getData()) > 0) {
               T tmp = current.getData();
               current.setData(left.getData());
               left.setData(tmp);
               current = left;
           }
            else if (right != null && right.getData().compareTo(current.getData()) > 0) {
               T tmp = current.getData();
               current.setData(right.getData());
               right.setData(tmp);
               current = right;
           }
            else
               break;
       }

        return (root.getData());
    }

    public void insert (T value) {
        // TODO
    }

    private void pushDown (T data) {

        HeapNode<T> newNode = new HeapNode<T>(null, data);

        if (root == null)
            root = newNode;
        else {
            HeapNode<T> current = root;
            if (root.getData().compareTo(data) > 0) {
                // right place:
                if (current.getLeft() == null)
                    current.setLeft(newNode);
                else if (current.getRight() == null)
                    current.setRight(newNode);
                else {

                }
            }
            else {
                newNode.setLeft(root);
            }
        }

//        HeapNode<T> current = root;
//
//        while (true) {
//            HeapNode<T> left = current.getLeft();
//            HeapNode<T> right = current.getRight();
//
//            if (left != null && left.getData().compareTo(current.getData()) > 0) {
//                T tmp = current.getData();
//                current.setData(left.getData());
//                left.setData(tmp);
//                current = left;
//            }
//            else if (right != null && right.getData().compareTo(current.getData()) > 0) {
//                T tmp = current.getData();
//                current.setData(right.getData());
//                right.setData(tmp);
//                current = right;
//            }
//            else
//                break;
//        }
    }

    public T    checkMax () {
        return (root == null ? null : root.getData());
    }
}
