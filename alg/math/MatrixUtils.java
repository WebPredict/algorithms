package alg.math;

/**
 * All of these methods return a Matrix copy as a result
 */
public class MatrixUtils {

    public static Matrix add (Matrix m1, Matrix m2) {

        int numRows = m1.getNumRows();
        int numCols = m2.getNumCols();

        if (numRows != m2.getNumRows() || numCols != m2.getNumCols())
            throw new RuntimeException("Incompatible matrix sizes for Matrix add");

        Matrix ret = new Matrix(m1.getNumRows(), m2.getNumCols());

        for (int i = 0; i < m1.getNumRows(); i++) {
            for (int j = 0; j < m1.getNumCols(); j++) {
                ret.setAt(i, j, m1.getAt(i, j) + m2.getAt(i, j));
            }
        }
       return (ret);
    }

    public static Matrix multiply (Matrix m1, Matrix m2) {

        int numRows = m2.getNumRows();
        int numCols = m1.getNumCols();

        if (m1.getNumCols() != m2.getNumRows() || m1.getNumRows() != m2.getNumCols())
            throw new RuntimeException("Incompatible matrix sizes for matrix multiply");

        Matrix ret = new Matrix(m2.getNumRows(), m1.getNumCols());

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {

                double val = 0;

                for (int k = 0; k < numCols; k++) {
                    val += m1.getAt(i, k) * m2.getAt(k, i);
                }

                ret.setAt(i, j, val);
            }
        }
        return (ret);
    }

    public static Matrix multiply (Matrix m, double scalar) {

        Matrix ret = new Matrix(m.getNumRows(), m.getNumCols());

        for (int i = 0; i < m.getNumRows(); i++) {
            for (int j = 0; j < m.getNumCols(); j++) {
                 ret.setAt(i, j, m.getAt(i, j) * scalar);
            }
        }
        return (ret);
    }

    public static Matrix transpose (Matrix m) {
        Matrix  ret = new Matrix(m.getNumCols(), m.getNumRows());

        for (int i = 0; i < m.getNumRows(); i++) {
            for (int j = 0; j < m.getNumCols(); j++) {
                ret.setAt(j, i, m.getAt(i, j));
            }
        }
        return (ret);
    }

    public static Matrix submatrix (Matrix m, int startRow, int startCol, int rowLen, int colLen) {
        Matrix ret = new Matrix(rowLen, colLen);

        for (int i = startRow; i < startRow + rowLen; i++) {
            for (int j = startCol; j < startCol + colLen; j++) {
                ret.setAt(i, j, m.getAt(i, j));
            }
        }
        return (ret);
    }

    public static Matrix identity (int size) {

        Matrix  m = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            m.setAt(i, i, 1);
        }
        return (m);
    }


    public static Matrix    rotate (Matrix m, Axis axis, double degrees) {

        double radians = Math.toRadians(degrees);

        return (m); // TODO
    }
}
