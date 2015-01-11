package alg.graphics;

import alg.math.MathUtils;
import alg.misc.InterestingAlgorithm;

import java.awt.geom.Point2D;

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

    // Could return a point or line or nothing if parallel
    @InterestingAlgorithm
    public Object       lineIntersect (Line2D l1, Line2D l2) {
        if (l1 == null || l2 == null)
            return (null);

        double slope1 = l1.getA();
        double slope2 = l2.getA();

        double offset1 = l1.getB();
        double offset2 = l2.getB();

        if (MathUtils.closeEnough(slope1, slope2)) {
            if (MathUtils.closeEnough(offset1, offset2))
                return (l1); // same
            else
                return (null); // parallel
        }

        Point2D point = new Point2D.Double((offset2 - offset1) / (slope1 - slope2), (slope1 * offset2 - slope2 * offset1) / (slope1 - slope2));

        return (point);
    }
}


