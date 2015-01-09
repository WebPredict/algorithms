package alg.graphics;

import alg.misc.InterestingAlgorithm;

import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphicsUtils {

    /**
     *   line intersection
     *   paint fill
     *   projections
     *   line smoothing
     *
     */

    @InterestingAlgorithm
    public static void paintfill (ImageMap image, int colorVal, Point2D start) {
         // TODO
    }

    @InterestingAlgorithm
    public int maxPointsOnLine (Point2D [] points) {
        return (0); // TODO
    }

    /**
     * Uses the shoelace method
     * @param points
     * @return
     */
    @InterestingAlgorithm
    public double   simplePolygonArea (Point2D [] points) {
        double area = 0;

        if (points != null && points.length > 2) {
            for (int i = 0; i < points.length; i++) {

                Point2D cur = points [i];

                if (i == points.length - 1)
                    area += cur.getX() * points [0].getY();
                else
                    area += cur.getX() * points [i + 1].getY();
            }

            for (int i = 0; i < points.length; i++) {

                Point2D cur = points [i];

                if (i == points.length - 1)
                    area -= cur.getY() * points [0].getX();
                else
                    area -= cur.getY() * points [i + 1].getX();
            }

            area /= 2d;
        }
        return (area);
    }

    @InterestingAlgorithm
    public boolean  isSimplePolygon (Point2D [] points) {
        return (false); // TODO
    }

    @InterestingAlgorithm
    public Point2D []   smooth (Point2D [] line) {
        // Ramer-Douglas-Peucker
        // rectangular smooth
        return (line); // TODO
    }

    // Could return a point or line or nothing if parallel
    @InterestingAlgorithm
    public Object       lineIntersect (Line2D l1, Line2D l2) {
        if (l1 == null || l2 == null)
            return (null);
        else if (l1.equals(l2))
            return (l1);

        double slope1 = l1.getA();
        double slope2 = l2.getA();

        double offset1 = l1.getB();
        double offset2 = l2.getB();

        if (slope1 == slope2)
            return (null); // parallel

        Point2D point = new Point2D.Double((offset2 - offset1) / (slope1 - slope2), (slope1 * offset2 - slope2 * offset1) / (slope1 - slope2));

        return (point);
    }
}
