package alg.graphics;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/26/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Line2D {

    private double a;
    private double b;

    // ax + b

    public Line2D(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}
