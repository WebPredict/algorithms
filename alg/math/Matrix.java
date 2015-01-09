package alg.math;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Matrix {

    private Vector []   data;

    // TODO: support for sparse matrices? some other data structure?

    /**
     * Support add, multiply, eigenvalues, normalize, rotational?
     * @param rows - vector length
     * @param cols - num vectors
     */
    public Matrix (int rows, int cols) {
        data = new Vector[cols];
        for (int i = 0; i < cols; i++) {
            data [i] = new Vector(rows);
        }
    }

    public int getNumRows () {
        return (data == null || data.length == 0 ? 0 : data [0].size());
    }

    public int getNumCols () {
        return (data == null ? 0 : data.length);
    }

    public void     setAt (int row, int col, double value) {
        data [col].set(row, value);
    }

    public double getAt (int row, int col) {
        return (data [col].get(row));
    }

    /**
     * Operates on this matrix and returns it
     * @param scalar
     * @return
     */
    public Matrix multiply (double scalar) {
        for (int i = 0; i < data.length; i++) {
            data [i].multiply(scalar);
        }

        return (this);
    }

    public  Matrix transpose () {
        // TODO

        return (this);
    }

    public Matrix add (Matrix add) {
        for (int i = 0; i < add.getNumCols(); i++) {
            Vector v = add.getVector(i);
            data[i].add(v);
        }

        return (this);
    }


    public Vector   getVector (int idx) {
        return (data [idx]);
    }

    public void     setVector (int idx, Vector v) {
        data [idx] = v;
    }

    public double determinant () {
        double det = 0d;
        // TODO
        return (det);
    }

    public double trace () {
        double ret = 0d;
        // TODO

        return (ret);
    }

    public Matrix clone () {
        Matrix ret = new Matrix(getNumRows(), getNumCols());

        for (int i = 0; i < getNumCols(); i++) {
            ret.setVector(i, getVector(i).clone());
        }
        return (ret);
    }
}
