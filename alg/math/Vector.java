package alg.math;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Vector {

    private double []   data;

    public Vector (double [] data) {
        this.data = data;
    }

    public Vector (int size) {
        data = new double[size];
    }

    public Vector add (Vector add) {
        for (int i = 0; i < add.size(); i++) {
            data [i] += add.get(i);
        }
        return (this);
    }

    public Vector subtract (Vector sub) {
        for (int i = 0; i < sub.size(); i++) {
            data [i] -= sub.get(i);
        }
        return (this);
    }

    public void multiply (double scalar) {
        for (int i = 0; i < data.length; i++) {
            data [i] *= scalar;
        }
    }

    public double   dot (Vector other) {
        double sum = 0;
        for (int i = 0; i < other.size(); i++) {
            sum += get(i) * other.get(i);
        }
        return (sum);
    }

    public Vector   cross3D (Vector other) {
        if (other.size() != 3 || size() != 3)
            throw new RuntimeException("Unimplemented: cross product on vectors of size = " + other.size());

        double first = get(1) * other.get(2) - get(2) * other.get(1);
        double second = get(2) * other.get(0) - get(0) * other.get(2);
        double third = get(0) * other.get(1) - get(1) * other.get(0);
        set(0, first);
        set(1, second);
        set(2, third);
        return (this);
    }

    public Vector   xyzzy (Vector other) {
        return (cross3D(other));
    }

    public double   cross2d (Vector other) {
        if (size() != 2 || other.size() != 2)
            throw new RuntimeException("Unimplemented: cross product on vectors of size = " + other.size());

        return (get(0) * other.get(1) - other.get(0) * get(1));
    }

    public int size () {
        return (data == null ? 0 : data.length);
    }

    public double get (int idx) {
        return (data [idx]);
    }

    public void set (int idx, double value) {
        data [idx] = value;
    }

    public Vector clone () {
        double [] clonedData = null;
        if (data != null) {
            clonedData = new double[data.length];
            System.arraycopy(data, 0, clonedData, 0, data.length);
        }
        Vector ret = new Vector(clonedData);

        return (ret);
    }
}
