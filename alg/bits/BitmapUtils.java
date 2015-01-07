package alg.bits;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/6/14
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class BitmapUtils {

    public static Bitmap wipeOut(Bitmap bitmap, boolean toOnes) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bitmap.setAtBoolean(i, j, toOnes);
            }
        }

        return (bitmap);
    }

    public static Bitmap invert(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                bitmap.setAtBoolean(i, j, !bitmap.getAtBoolean(i, j));
            }
        }

        return (bitmap);
    }

    public static Bitmap diff (Bitmap b1, Bitmap b2) {
        if ((b1 == null && b2 != null) || (b2 == null && b1 != null) ||
                b1.getHeight() != b2.getHeight() || b1.getWidth() != b2.getWidth())
            throw new IllegalArgumentException("Incompatible sizes");

        Bitmap ret = new Bitmap(b1.getHeight(), b1.getWidth());
        for (int i = 0; i < b1.getHeight(); i++) {
            for (int j = 0; j < b1.getWidth(); j++) {
                boolean val = b1.getAtBoolean(i, j) ^ b2.getAtBoolean(i, j);
                ret.setAtBoolean(i, j, val);
            }
        }
        return (ret);
    }
}
