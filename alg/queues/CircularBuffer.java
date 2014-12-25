package alg.queues;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class CircularBuffer {

    private int []  buffer;
    private int currentIdx;

    public CircularBuffer(int size) {
        buffer = new int[size];
    }

    public void write (int data) {
        // TODO
    }

    public void delete () {
        // TODO
    }

    public int read () {
        return (0); // TODO
    }

    public boolean  isEmpty () {
        return (false); // TODO
    }

    public boolean isFull () {
         return (false); // TODO
    }
}
