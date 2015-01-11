package alg.graphics;

import alg.math.MathUtils;
import alg.math.Vector;
import alg.misc.InterestingAlgorithm;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/26/14
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class Polygon {

    private Point2D []  points;

    public Polygon (Point2D [] points) {
        this.points = points;
    }

    @InterestingAlgorithm
    public boolean  isSimple () {
        /**
         * approach: need to see if there are any intersecting line segments
         * Should probably line sweep the line segments
         */
        return (false); // TODO
    }

    @InterestingAlgorithm
    public boolean  isConvex () {
        if (points == null || points.length < 4)
            return (false);

        boolean seenNeg = false;
        boolean seenPos = false;
        for (int i = 2; i < points.length; i++) {
            Point2D first = points [i - 2];
            Point2D second = points [i - 1];
            Point2D third = points [i];

            double angleBetween = MathUtils.angleBetweenVectors2D(Vector.fromPoints(second, first), Vector.fromPoints(third, second));
            if (angleBetween < 0)
                seenNeg = true;
            else if (angleBetween > 0)
                seenPos = true;

            // hmm not sure about this logic:
            if (seenNeg && seenPos)
                return (true);
        }
        return (false);
    }

    public Point2D[] getPoints() {
        return points;
    }

    public void setPoints(Point2D[] points) {
        this.points = points;
    }

    @InterestingAlgorithm
    public boolean  selfIntersects () {
        return (!isSimple());
    }

    @InterestingAlgorithm
    public boolean  containsPoint (Point2D point) {
        if (points == null || points.length < 1)
            return (false);

        PriorityQueue   pointsQueue = new PriorityQueue(0, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Point2D first = (Point2D)o1;
                Point2D second = (Point2D)o2;
                if (first.getX() < second.getX())
                    return (-1);
                else if (first.getX() > second.getX())
                    return (1);
                else
                    return (0);
            }
        });
        Line2D line = new Line2D(0, point.getY());

        for (int i = 1; i < points.length; i++) {
            LineSegment2D segment = new LineSegment2D(points [i - 1], points[i]);
            Object intersection = segment.lineIntersect(line);
            if (intersection instanceof LineSegment2D)
                return (true);
            else if (intersection instanceof Point2D) {
                pointsQueue.add((Point2D)intersection);
            }
        }

        boolean inside = false;
        while (pointsQueue.size() > 0) {
            Point2D next = (Point2D)pointsQueue.poll();
            if (next.getX() <= point.getX())
                inside = !inside;
        }
        return (inside);
    }
}
