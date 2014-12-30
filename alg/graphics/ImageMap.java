package alg.graphics;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/6/14
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImageMap {

    private int maxColors;
    private int rows;
    private int cols;
    private int [][] data;

    public ImageMap (int maxColors, int rows, int cols) {
        this.maxColors = maxColors;
        this.rows = rows;
        this.cols = cols;
        data = new int[rows][cols]; // TODO  more efficient storage based on maxColors
    }

    public int getColorAt (int row, int col) {
        return (data [row][col]);
    }

    public void setColorAt (int row, int col, int color) {
        if (color == TMP_SET_COLOR)
            throw new RuntimeException("Cannot use color: " + TMP_SET_COLOR);
        data [row][col] = color;
    }

    public int getMaxColors() {
        return maxColors;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public static final int TMP_SET_COLOR = -1;

    public  ImageMap erode () {
        // TODO: need to rewrite this to support multiple colors beyond B&W!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value == 0) {
                    if (i > 0 && data [i - 1][j] != 0)
                        data [i - 1][j] = TMP_SET_COLOR;
                    if (j > 0 && data [i][j - 1] != 0)
                        data [i][j - 1] = TMP_SET_COLOR;
                    if (i + 1 < rows && data [i + 1][j] != 0)
                        data [i + 1][j] = TMP_SET_COLOR;
                    if (j + 1 < cols && data [i][j + 1] != 0)
                        data [i][j + 1] = TMP_SET_COLOR;
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value == TMP_SET_COLOR) {
                    data[i][j] = 0;
                }
            }
        }
        return (this);
    }

    public  ImageMap dilate () {

        // TODO: need to rewrite this to support multiple colors beyond B&W!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value != 0 && value != TMP_SET_COLOR) {
                    if (i > 0 && data [i - 1][j] == 0)
                        data [i - 1][j] = TMP_SET_COLOR;
                    if (j > 0 && data [i][j - 1] == 0)
                        data [i][j - 1] = TMP_SET_COLOR;
                    if (i + 1 < rows && data [i + 1][j] == 0)
                        data [i + 1][j] = TMP_SET_COLOR;
                    if (j + 1 < cols && data [i][j + 1] == 0)
                        data [i][j + 1] = TMP_SET_COLOR;
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = data [i][j];
                if (value == TMP_SET_COLOR) {
                     data[i][j] = 1;
                }
            }
        }
        return (this);
    }

    public ImageMap dilate (int k) {
        convertToManhattanDistance();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (data [i][j] <= k ? 1 : 0);
            }
        }
        return (this);
    }

    public ImageMap erode (int k) {
        convertToManhattanDistance();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (data [i][j] <= k ? 0 : 1);
            }
        }
        return (this);
    }

    public ImageMap  clone () {
        ImageMap copy = new ImageMap(maxColors, rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy.setColorAt(i, j, data [i][j]);
            }
        }
        return (copy);
    }

    public ImageMap  convertToManhattanDistance () {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (getColorAt(i, j) != 0) {
                    setColorAt(i, j, 0);
                }
                else {
                    setColorAt(i, j, rows + cols);
                    if (i > 0)
                        setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i - 1, j) + 1));
                    if (j > 0)
                        setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i, j - 1) + 1));
                }
            }
        }

        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                if (i + 1 < rows)
                    setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i + 1, j) + 1));
                if (j + 1 < cols)
                    setColorAt(i, j, Math.min(getColorAt(i, j), getColorAt(i, j + 1) + 1));
            }
        }
        return (this);
    }
}
