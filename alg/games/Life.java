package alg.games;

import alg.misc.InterestingAlgorithm;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Life {

    byte [][]   board;

    public Life (int rows, int cols) {
        board = new byte [rows][cols];
    }

    public void setCell (int row, int col, boolean on) {

        board [row][col] = on ? (byte)1 : (byte)0;
    }

    public int getNumRows () {
        return (board.length);
    }

    public int getNumCols () {
        return (board.length == 0 ? 0 : board.length);
    }

    @InterestingAlgorithm
    public void nextGeneration (int generations) {

        // TODO: is there a way to do this more efficiently, a la manhattan distance for image erode/dilate?
        for (int i = 0; i < generations; i++)
            nextGeneration();
    }

    public void nextGeneration () {
        /**
         * If a cell has 2 or 3 neighbors it turns on, if 4 neighbors dies out, 1 neighbor dies out?
         */

    }
}
