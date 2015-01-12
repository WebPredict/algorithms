package alg.graphics;

import alg.math.MathUtils;
import alg.math.Vector;
import alg.misc.InterestingAlgorithm;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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
    public static void paintfill (ImageMap image, int row, int col, int fillColorVal, int paintableColorVal) {

        int rows = image.getRows();
        int cols = image.getCols();

        if (row >= rows || col >= cols)
            return;

        int color = image.getColorAt(row, col);
        if (color == fillColorVal || color != paintableColorVal)
            return;

        Queue<Integer> rowQueue = new LinkedList<Integer>();
        Queue<Integer> colQueue = new LinkedList<Integer>();

        rowQueue.add(row);
        colQueue.add(col);

        while (!rowQueue.isEmpty()) {
            int nextRow = rowQueue.poll();
            int nextCol = colQueue.poll();

            image.setColorAt(nextRow, nextCol, fillColorVal);

            if (nextRow > 0 && image.getColorAt(nextRow - 1, nextCol) == paintableColorVal) {
                rowQueue.add(nextRow - 1);
                colQueue.add(nextCol);
            }
            if (nextRow < rows - 1 && image.getColorAt(nextRow + 1, nextCol) == paintableColorVal) {
                rowQueue.add(nextRow + 1);
                colQueue.add(nextCol);
            }
            if (nextCol > 0 && image.getColorAt(nextRow, nextCol - 1) == paintableColorVal) {
                rowQueue.add(nextRow);
                colQueue.add(nextCol - 1);
            }
            if (nextCol < cols - 1 && image.getColorAt(nextRow, nextCol + 1) == paintableColorVal) {
                rowQueue.add(nextRow);
                colQueue.add(nextCol + 1);
            }
        }
    }

    public double   totalArea (Rectangle [] rects) {
        if (rects == null)
            return (Double.NaN);

        double total = 0;

        /**
         * put in priority queue based on sorted X for efficient line sweep
         */
        PriorityQueue queue = new PriorityQueue(0, new Comparator<Rectangle>() {

            @Override
            public int compare(Rectangle o1, Rectangle o2) {
                if (o1.getLowerLeft().getX() < o2.getLowerLeft().getX())
                    return (-1);
                else if (o1.getLowerLeft().getX() > o2.getLowerLeft().getX())
                    return (1);
                else
                    return (0);
            }
        });

        for (Rectangle rect : rects)
            queue.add(rect);

        Rectangle prev = null;
        for (int i = 0; i < rects.length; i++) {
            if (prev != null) {

            }

            prev = rects [i];
        }
        return (total);
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

    public static double linePointDist(Line2D line, Point2D point) {

        double lineA = line.getA();
        double lineB = line.getB();

        /**
         * 3x = 2 == 3x - 2 = 0
         * x = 0, y = 2
         * x = 1, y = 1
         *
         * ax + b = y
         */
        Point2D first = new Point2D.Double(0, lineB); // TODO vertical lines
        Point2D second = new Point2D.Double(lineA, lineB + lineA);

        Vector lineV = Vector.fromPoints(first, second);
        Vector pointP = Vector.fromPoints(first, point);

        double dist = lineV.cross2d(pointP) / distance(first, second);

        return Math.abs(dist);
    }

    public static double linePointDist(Point2D first, Point2D second, Point2D point) {
        Vector lineV = Vector.fromPoints(first, second);
        Vector pointP = Vector.fromPoints(first, point);

        double dist = lineV.cross2d(pointP) / distance(first, second);

        return Math.abs(dist);
    }

    public static double distance (Point2D first, Point2D second) {
        double xDiff = first.getX() - second.getX();
        double yDiff = first.getY() - second.getY();
        return (Math.sqrt(xDiff * xDiff + yDiff * yDiff));
    }

    public static double lineSegmentPointDist(LineSegment2D segment, Point2D point){

        // TODO: optimize to not have to create all these vectors
        double dot1 = Vector.fromPoints(point, segment.getEnd()).dot(Vector.fromPoints(segment.getEnd(), segment.getStart()));//(C-B)*(B-A);
        if(dot1 > 0)
            return Math.sqrt(Vector.fromPoints(point, segment.getEnd()).dot(Vector.fromPoints(point, segment.getEnd())));

        double dot2 = Vector.fromPoints(point, segment.getStart()).dot(Vector.fromPoints(segment.getEnd(), segment.getStart())); //(C-A)*(A-B);
        if(dot2 > 0)
            return Math.sqrt(Vector.fromPoints(point, segment.getStart()).dot(Vector.fromPoints(point, segment.getStart())));

        double dist = linePointDist(segment.getStart(), segment.getEnd(), point);
        return Math.abs(dist);
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
