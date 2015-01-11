package alg.graphics;

import alg.math.MathUtils;
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

    public double   totalArea (Rectangle [] rects) {
        /**
         * TODO: put point in priority queue based on sorted X for efficient line sweep
         */
        return (0);
    }

    public double   totalPerimeter (Rectangle [] rects) {
        return (0);
    }

    public double   maxIntersectingArea (Rectangle [] rects) {
        return (0);
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
    public static Polygon   computeConvexHull (Point2D [] points) {
        Polygon ret = null;
        // TODO
        return (null);
    }

    @InterestingAlgorithm
    public Point2D []   smooth (Point2D [] line) {
        // Ramer-Douglas-Peucker
        // rectangular smooth
        return (line); // TODO
    }

}
