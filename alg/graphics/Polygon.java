package alg.graphics;

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

    public boolean  isSimple () {
        return (false); // TODO
    }

    public boolean  isConvex () {
        return (false); // TODO
    }

    public Point2D[] getPoints() {
        return points;
    }

    public void setPoints(Point2D[] points) {
        this.points = points;
    }

    public boolean  selfIntersects () {
        return (false); // TODO hmm how to do this in less than O(n^2) time?
    }
}
