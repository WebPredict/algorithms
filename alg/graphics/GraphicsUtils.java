package alg.graphics;

import alg.math.MathUtils;
import alg.math.Vector;
import alg.misc.InterestingAlgorithm;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphicsUtils {

    /**
     * Fill with fillColorVal in cells with current color paintableColorVal.
     * @param image
     * @param row
     * @param col
     * @param fillColorVal
     * @param paintableColorVal
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

    /**
     * Computes total area of all rectangles (obviously, minus any intersections).
     * @param rects
     * @return
     */
    @InterestingAlgorithm
    public double   totalArea (Rectangle [] rects) {
        if (rects == null)
            return (Double.NaN);

        double total = 0;

        for (int i = 0; i < rects.length; i++)
            total += rects [i].area();

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

                Rectangle prevInterCur = prev.intersect(rects [i]);
                if (prevInterCur != null)
                    total -= prevInterCur.area();
                /**
                 * TODO: approach: as long as there are intersections with prev, don't change it?
                 */
            }

            prev = rects [i];
        }
        return (total);
    }

    /**
     *
     * @param rects
     * @return
     */
    @InterestingAlgorithm
    public double   totalPerimeter (Rectangle [] rects) {
        return (0);   // TODO
    }

    /**
     *
     * @param rects
     * @return
     */
    @InterestingAlgorithm
    public int   maxNumIntersectingRectangles (Rectangle [] rects) {
        return (0);  // TODO
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

    @InterestingAlgorithm
    public static double lineSegmentPointDist(LineSegment2D segment, Point2D point){

        double dot1 = MathUtils.dotProduct(segment.getEnd(), point, segment.getStart(), segment.getEnd()); //(C-B)*(B-A)
        if(dot1 > 0)
            return Math.sqrt(MathUtils.dotProduct(segment.getEnd(), point, segment.getEnd(), point));

        double dot2 = MathUtils.dotProduct(segment.getStart(), point, segment.getStart(), segment.getEnd()); //(C-A)*(A-B);
        if(dot2 > 0)
            return Math.sqrt(MathUtils.dotProduct(segment.getStart(), point, segment.getStart(), point));

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

    /**
     * Adapted from TopCoder notes - one way to compute a convex hull, assuming
     * we do not include colinear points
     * @param points
     * @return
     */
    @InterestingAlgorithm
    public static Polygon   computeConvexHull (Point2D [] points) {
        if (points == null)
            return (null);

        int          numPoints = points.length;
        int         leftmostPIdx = 0;
        boolean[]   used = new boolean [numPoints];

        //First find the leftmost point
        for(int i = 1; i < numPoints; i++) {
            if(points[i].getX() < points[leftmostPIdx].getX())
                leftmostPIdx = i;
        }

        int         start = leftmostPIdx;
        do {
            int     n = -1;
            double  dist = 0;

            for(int i = 0; i < numPoints; i++) {
                //Don't go back to the same point you came from
                if (i == leftmostPIdx)
                    continue;

                //Don't go to a visited point
                if (used[i])
                    continue;

                if(n == -1)
                    n = i;

                double cross = MathUtils.crossProduct(points[i], points [leftmostPIdx], points [n], points [leftmostPIdx]);

                //dot is the distance from leftmostPIdx to i
                double dot =  MathUtils.dotProduct(points[i], points [leftmostPIdx], points [i], points [leftmostPIdx]);

                if (cross < 0) {
                    n = i;
                    dist = dot;
                }
                else if (cross == 0) {

                    if (dot > dist) {
                        dist = dot;
                        n = i;
                    }
                }
            }
            leftmostPIdx = n;
            used[leftmostPIdx] = true;
        } while (start != leftmostPIdx);

        Polygon     ret = new Polygon();
        for (int i = 0; i < used.length; i++) {
            if (used [i])
                ret.getPoints().add(points[i]);
        }
        return (ret);
    }

    public List<Point2D> smooth (Point2D [] line, double epsilon) {
        if (line == null)
            return (null);

        return (smooth(line, 0, line.length, epsilon));
    }

    /**
     * The Ramer-Douglas-Peucker line smoothing algorithm
     * @param line
     * @param epsilon
     * @return
     */
    @InterestingAlgorithm
    public List<Point2D> smooth (Point2D [] line, int startIdx, int endIdx, double epsilon) {
        // Ramer-Douglas-Peucker

        double  maxDistance = 0;
        int     index = 0;
        int     end = line.length;
        for (int i = 2; i < end; i++) {
            double d = lineSegmentPointDist(new LineSegment2D(line [0], line [end - 1]), line [i]);
            if (d > maxDistance) {
                index = i;
                maxDistance = d;
            }
        }

        List<Point2D> results = new ArrayList<Point2D>();
        if (maxDistance > epsilon) {
            List<Point2D> results1 = smooth(line, 0, index, epsilon);
            List<Point2D> results2 = smooth(line, index, end, epsilon);

            results.addAll(results1);
            results.addAll(results2);
        }
        else {
            results.add(line [0]);
            if (end - 1 > 0)
                results.add(line [end - 1]);
        }
        return (results);
    }

    // TODO: rectangular smooth

    /**
     *
     * @param values
     * @return
     */
    @InterestingAlgorithm
    public List<Integer>    movingAverage (int [] values, int size) {

        if (values == null)
            return (null);

        List<Integer> ret = new ArrayList<Integer>();


        /**
         * 1, 3, 4, 9, 10, 8, 7, 6, 2, 1
         *
         *
         */
        ret.add(values [0]); // not sure about that
        for (int i = size; i < values.length; i += size * 2) {
            double avg = 0;
             for (int j = i - size; j <= i + size; j++) {
                 if (j < values.length)
                    avg += values [j];
                 else
                     break;
             }
             ret.add((int)(avg / (double)(size * 2 + 1)));
        }
        // TODO: add the last one?
        return (ret);
    }
}
