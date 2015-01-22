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
    private int writeIdx;
    private int readIdx;
    private boolean writeFlipped;
    private boolean readFlipped;

    public CircularBuffer(int size) {
        buffer = new int[size];
    }

    public void write (int data) {
        if (isFull())
            throw new RuntimeException("Buffer is full");

        buffer[writeIdx++] = data;
        if (writeIdx >= buffer.length) {
            writeIdx = 0;
            writeFlipped = !writeFlipped;
        }
    }

    public int read () {
        if (isEmpty())
            throw new RuntimeException("Buffer is empty");

        int ret = buffer[readIdx++];
        if (readIdx >= buffer.length) {
            readIdx = 0;
            readFlipped = !readFlipped;
        }
        return (ret);
    }

    public boolean  isEmpty () {
        return (readIdx == writeIdx && writeFlipped == readFlipped);
    }

    public boolean isFull () {
        return (readIdx == writeIdx && writeFlipped != readFlipped);
    }
}
