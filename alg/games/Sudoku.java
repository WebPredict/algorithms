package alg.games;

import alg.arrays.ArrayUtils;
import alg.math.MathUtils;
import alg.math.prob.ProbUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/29/14
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sudoku {

    private int [][]    board;

    public Sudoku (int [][] board) {
        this.board = board;
    }

    public static Sudoku    generate () {

        int n = 3;
        int [][] initial = new int[n*n][n*n];

        // The root board:
        for (int i = 0; i < n*n; i++) {
            for (int j = 0; j < n*n; j++) {
                initial [i][j] = (i * n + i / n + j) % (n * n) + 1;
            }
        }

        // Hmm... we can randomly shuffle groups of 3 cols/rows at a time it seems and still maintain valid sudoku

        for (int i = 0; i < 10; i++) {
            int random = MathUtils.generateRandom(3);

            boolean rowsOrCols = ProbUtils.coinFlip();

            if (ProbUtils.coinFlip()) {
                int subsection = MathUtils.generateRandom(3);
                subsection *= 3;

                switch (random) {
                    case 0:
                        if (rowsOrCols)
                            ArrayUtils.swapRows(initial, subsection, subsection + 1);
                        else
                            ArrayUtils.swapCols(initial, subsection, subsection + 1);
                        break;

                    case 1:
                        if (rowsOrCols)
                            ArrayUtils.swapRows(initial, subsection, subsection + 2);
                        else
                            ArrayUtils.swapCols(initial, subsection, subsection + 2);
                        break;

                    case 2:
                        if (rowsOrCols)
                            ArrayUtils.swapRows(initial, subsection + 1, subsection + 2);
                        else
                            ArrayUtils.swapCols(initial, subsection + 1, subsection + 2);
                        break;
                }
            }
            else {
               switch (random) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        }
        Sudoku ret = new Sudoku(initial);



        return (ret);
    }
}
