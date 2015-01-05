package alg.games;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Reversi {

    private int [][] board;

    public Reversi () {

        // TODO: make this a configurable (even?) size
        board = new int[8][8];
        board [3][3] = 1;
        board [4][4] = 1;

        board [3][4] = 2;
        board [4][3] = 2;
    }


    /**
     *
     * @param row
     * @param col
     * @param color
     * @return false if invalid move
     */
    public boolean  makeMove (int row, int col, int color) {

        // This method assumes a square board:

        int oppositeColor = color == 1 ? 2 : 1;

        // there must be a path with at least one opposite color piece in the way, ending with same color piece in
        // one or more directions
        boolean foundPath = false;
        if (row < board.length - 1) {
            boolean startedPath = false;

            // To the right:
            for (int i = row + 1; i < board.length; i++) {
                if (board [i][col] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [i][col] == color && startedPath) {
                    foundPath = true;
                    for (int j = row + 1; j < i; j++) {
                        board [j][col] = color;
                    }
                    break;
                }
                else
                    break;
            }
        }

        if (row > 0) {
            // To the left:
            boolean startedPath = false;
            for (int i = row - 1; i >= 0; i--) {
                if (board [i][col] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [i][col] == color && startedPath) {
                    foundPath = true;
                    for (int j = row - 1; j > i; j--) {
                        board [j][col] = color;
                    }
                    break;
                }
                else
                    break;
            }
        }

        if (col > 0) {
            // Up:
            boolean startedPath = false;
            for (int i = col - 1; i >= 0; i--) {
                if (board [row][i] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [row][i] == color && startedPath) {
                    foundPath = true;
                    for (int j = col - 1; j > i; j--) {
                        board [row][j] = color;
                    }
                    break;
                }
                else
                    break;
            }
        }

        if (col < board.length - 1) {
            // Down:
            boolean startedPath = false;
            for (int i = col + 1; i < board.length; i++) {
                if (board [row][i] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [row][i] == color && startedPath) {
                    foundPath = true;
                    for (int j = col + 1; j < i; j++) {
                        board [row][j] = color;
                    }
                    break;
                }
                else
                    break;
            }
        }

        if (col < board.length - 1 && row < board.length - 1) {
            // Down + right:
            boolean startedPath = false;
            for (int i = col + 1, j = row + 1; i < board.length && j < board.length; i++, j++) {
                if (board [j][i] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [j][i] == color && startedPath) {
                    foundPath = true;
                    for (int k = col + 1, l = row + 1; k < i && l < j; k++, l++) {
                        board [l][j] = color;
                    }
                    break;
                }
                else
                    break;

            }
        }

        if (col > 0 && row > 0) {
            // Up + left:
            boolean startedPath = false;
            for (int i = col - 1, j = row - 1; i >= 0 && j >= 0; i--, j--) {
                if (board [j][i] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [j][i] == color && startedPath) {
                    foundPath = true;
                    for (int k = col - 1, l = row - 1; k > i && l > j; k--, l--) {
                        board [l][j] = color;
                    }
                    break;
                }
                else
                    break;

            }
        }

        if (col < board.length - 1 && row > 0) {
            // Down + left:
            boolean startedPath = false;
            for (int i = col + 1, j = row - 1; i < board.length && j >= 0; i++, j--) {
                if (board [j][i] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [j][i] == color && startedPath) {
                    foundPath = true;
                    for (int k = col + 1, l = row - 1; k < i && l > j; k++, l--) {
                        board [l][j] = color;
                    }
                    break;
                }
                else
                    break;

            }
        }

        if (col > 0 && row < board.length - 1) {
            // Up + right:
            boolean startedPath = false;
            for (int i = col - 1, j = row - 1; i >= 0 && j < board.length; i--, j++) {
                if (board [j][i] == oppositeColor) {
                    startedPath = true;
                }
                else if (board [j][i] == color && startedPath) {
                    foundPath = true;
                    for (int k = col - 1, l = row + 1; k > i && l < j; k--, l++) {
                        board [l][j] = color;
                    }
                    break;
                }
                else
                    break;

            }
        }

        return (foundPath);
    }
}
