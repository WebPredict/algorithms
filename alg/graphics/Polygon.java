package alg.graphics;

import alg.misc.InterestingAlgorithm;

import java.awt.geom.Point2D;

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
        return (false); // TODO
    }

    @InterestingAlgorithm
    public boolean  isConvex () {
        return (false); // TODO
    }

    public Point2D[] getPoints() {
        return points;
    }

    public void setPoints(Point2D[] points) {
        this.points = points;
    }

    @InterestingAlgorithm
    public boolean  selfIntersects () {
        return (false); // TODO hmm how to do this in less than O(n^2) time?  Need to sort them
    }

    @InterestingAlgorithm
    public boolean  containsPoint (Point2D point) {
        // TODO: draw a line that contains the point, look at intersections even odd
        return (false);
    }
}
