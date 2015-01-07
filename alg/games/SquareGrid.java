package alg.games;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/7/15
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SquareGrid {
    private int [][]    values;

    public SquareGrid(int size, int initValue) {
        values = new int[size][size];
        for (int j = 0; j < values.length; j++) {
            for (int k = 0; k < values[j].length; k++) {
                values [j][k] = initValue;
            }
        }
    }

    public void rotate (int times, boolean clockwise) {

    }

    public int getValueAt (int row, int col) {
        return (values [row][col]);
    }

    public void setValueAt (int row, int col, int value) {
        values [row][col] = value;
    }
}
