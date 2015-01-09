package alg.games;

import alg.misc.InterestingAlgorithm;

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

    @InterestingAlgorithm
    public void rotate (int times, boolean clockwise) {

        int actualTimes = times % 4;
        if (actualTimes == 0)
            return;
        else if (actualTimes == 3) {
            actualTimes = 1;
            clockwise = !clockwise;
        }

        int rowLen = values [0].length;
        if (clockwise) {
            for (int k = 0; k < actualTimes; k++) { // max should be 2
                for (int j = 0; j < rowLen / 2; j++) {
                    for (int i = rowLen - 1; i > 0; i--) {
                        int tmp = values [j][rowLen - j - 1 - i];

                        values [j][rowLen - j - 1 - i] = values [j][i - j];

                        int nextTmp = values [rowLen - 1 - j][i - j];
                        values [rowLen - 1 - j][i - j] = tmp;

                        tmp = values [i - j][rowLen - 1 - j];

                        values[i - j][rowLen - 1 - j] = nextTmp;

                        nextTmp = values[j][i - j];
                        values [j][i - j] = tmp;
                    }
                }
            }
        }
        else {
            // TODO
        }
    }

    public int getValueAt (int row, int col) {
        return (values [row][col]);
    }

    public void setValueAt (int row, int col, int value) {
        values [row][col] = value;
    }
}
