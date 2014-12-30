package alg.graphics;

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

    public static void paintfill (ImageMap image, int colorVal, Point2D start) {
         // TODO
    }

    public int maxPointsOnLine (Point2D [] points) {
        return (0); // TODO
    }

    /**
     * Uses the shoelace method
     * @param points
     * @return
     */
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

    public boolean  isSimplePolygon (Point2D [] points) {
        return (false); // TODO
    }

    public Point2D []   smooth (Point2D [] line) {
        return (line); // TODO
    }

    // Could return a point, line segment, or line
    public Object       lineIntersect (Line2D l1, Line2D l2) {
        return (null);
    }
}
