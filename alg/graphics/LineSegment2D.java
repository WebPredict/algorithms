package alg.graphics;

import alg.math.MathUtils;
import alg.misc.InterestingAlgorithm;

import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/26/14
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class LineSegment2D {

    private Point2D start;
    private Point2D end;

    public LineSegment2D(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
    }

    public Point2D getStart() {
        return start;
    }

    public void setStart(Point2D start) {
        this.start = start;
    }

    public Point2D getEnd() {
        return end;
    }

    public void setEnd(Point2D end) {
        this.end = end;
    }

    public double   getSlope () {
        return ((end.getY() - start.getY()) / (end.getX() - start.getY()));
    }

    @InterestingAlgorithm
    public Object       lineIntersect (Line2D other) {
        if (other == null)
            return (null);

        double slope1 = getSlope();

        double slope2 = other.getA();

        double offset1 = start.getY();
        double offset2 = other.getB();

        if (MathUtils.closeEnough(slope1, slope2)) {
            if (MathUtils.closeEnough(offset1, offset2))
                return (this); // same
            else
                return (null); // parallel
        }

        Point2D point = new Point2D.Double((offset2 - offset1) / (slope1 - slope2), (slope1 * offset2 - slope2 * offset1) / (slope1 - slope2));
        double lowestX;
        double highestX;
        double lowestY;
        double highestY;

        if (start.getX() <= end.getX()) {
            lowestX = start.getX();
            highestX = end.getX();
        }
        else {
            lowestX = end.getX();
            highestX = start.getX();
        }
        if (start.getY() <= end.getY()) {
            lowestY = start.getY();
            highestY = end.getY();
        }
        else {
            lowestY = end.getY();
            highestY = start.getY();
        }

        if (point.getX() >= lowestX && point.getY() >= lowestY && point.getX() <= highestX && point.getY() <= highestY)
            return (point);
        else
            return (null);
    }
}
