package alg.bits;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/7/15
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BitUtils {

    public int  getLowestBits (int value, int numBits) {

        return (value); // TODO
    }

    public boolean getBitAt (int value, int bit) {
        return (((1 << bit) & value) == 1);
    }

    public void setBitAt (int value, int bit, boolean on) {
        if (on)
            value |= (bit << 1);
        else
            value &= ~(bit << 1);
    }
}
