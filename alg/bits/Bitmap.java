package alg.bits;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/6/14
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bitmap {

    private byte [][]   data;

    public Bitmap (int width, int height) {
        data = new byte[height][width];
    }

    public void setAt(int i, int j, byte value) {
        data[i][j] = value;
    }

    public byte  getAt(int i, int j) {
        return (data [i][j]);
    }

    public void setAtBoolean(int i, int j, boolean value) {
        data[i][j] = value ? (byte)1 : (byte)0;
    }

    public boolean  getAtBoolean(int i, int j) {
        return (data [i][j] != 0);
    }

    public int getHeight() {
        return (data.length);
    }

    public int getWidth() {
        return (data [0].length);
    }
}
