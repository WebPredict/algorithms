package alg.util;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class LinkNode<T extends Comparable> {

    private T   value;
    private LinkNode<T> next;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkNode<T> getNext() {
        return next;
    }

    public void setNext(LinkNode<T> next) {
        this.next = next;
    }

    public String toString () {
        StringBuilder builder = new StringBuilder();
        if (value == null)
            builder.append("NULL");
        else
            builder.append(value.toString());

        LinkNode<T> iter = next;
        int counter = 100; // cutoff for loops
        while (iter != null && counter >= 0) {
            builder.append(" -> ");
            builder.append(iter.getValue());

            iter = iter.getNext();
            counter--;
        }

        return builder.toString();
    }
}
