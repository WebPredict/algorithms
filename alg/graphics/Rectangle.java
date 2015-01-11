package alg.graphics;

import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/11/15
 * Time: 7:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle {

    private Point2D lowerLeft;
    private Point2D lowerRight;
    private Point2D upperLeft;
    private Point2D upperRight;

    public Rectangle (Point2D lowerLeft, double width, double height) {
        this.lowerLeft = lowerLeft;
        lowerRight = new Point2D.Double(lowerLeft.getX() + width, lowerLeft.getY());
        upperLeft = new Point2D.Double(lowerLeft.getX(), lowerLeft.getY() + height);
        upperRight = new Point2D.Double(lowerLeft.getX() + width, lowerLeft.getY() + height);
    }

    public Rectangle (Point2D lowerLeft, Point2D lowerRight, Point2D upperLeft, Point2D upperRight) {
        this.lowerLeft = lowerLeft;
        this.lowerRight = lowerRight;
        this.upperLeft = upperLeft;
        this.upperRight = upperRight;
    }

    public Point2D getLowerLeft() {
        return lowerLeft;
    }

    public void setLowerLeft(Point2D lowerLeft) {
        this.lowerLeft = lowerLeft;
    }

    public Point2D getLowerRight() {
        return lowerRight;
    }

    public void setLowerRight(Point2D lowerRight) {
        this.lowerRight = lowerRight;
    }

    public Point2D getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Point2D upperLeft) {
        this.upperLeft = upperLeft;
    }

    public Point2D getUpperRight() {
        return upperRight;
    }

    public void setUpperRight(Point2D upperRight) {
        this.upperRight = upperRight;
    }

    public double   area () {
        return (Math.abs(lowerLeft.getX() - lowerRight.getX()) * Math.abs(upperLeft.getX() - lowerLeft.getX()));
    }

    public Rectangle intersect (Rectangle other) {

        Point2D otherLowerLeft = other.getLowerLeft();
        Point2D otherLowerRight = other.getLowerRight();
        Point2D otherUpperLeft = other.getUpperLeft();
        Point2D otherUpperRight = other.getUpperRight();

        Point2D interLowerLeft = null;
        Point2D interLowerRight = null;
        Point2D interUpperLeft = null;
        Point2D interUpperRight = null;

        // TODO fix this ain't correct
        if (otherLowerLeft.getX() >= lowerLeft.getX() && otherLowerLeft.getX() <= lowerRight.getX()) {
            // x is in range
            interLowerLeft = otherLowerLeft;

            if (otherLowerRight.getX() <= lowerRight.getX())
                interLowerRight = otherLowerRight;
            else
                interLowerRight = lowerRight;
        }
        else if (otherLowerLeft.getX() < lowerLeft.getX() && otherLowerRight.getX() >= lowerLeft.getX()) {
            // either it intersects from left, or completely contains:
            if (otherLowerRight.getX() >= lowerRight.getX()) {
                // contains, on X axis
                interLowerLeft = lowerLeft;
                interLowerRight = lowerRight;
            }
            else {
                interLowerLeft = lowerLeft;
                interLowerRight = otherLowerRight;
            }
        }

        if (otherUpperLeft.getY() >= upperLeft.getY() && otherLowerLeft.getY() <= upperLeft.getY()) {
            // y is in range
            interUpperLeft = upperLeft;
            if (otherLowerRight.getX() <= lowerRight.getX())
                interLowerRight = otherLowerRight;
            else
                interLowerRight = lowerRight;
        }
        else if (otherLowerLeft.getX() < lowerLeft.getX() && otherLowerRight.getX() >= lowerLeft.getX()) {
            // either it intersects from left, or completely contains:
            if (otherLowerRight.getX() >= lowerRight.getX()) {
                // contains, on X axis
                interLowerLeft = lowerLeft;
                interLowerRight = lowerRight;
            }
            else {
                interLowerLeft = lowerLeft;
                interLowerRight = otherLowerRight;
            }
        }

        if (interLowerLeft != null && interLowerRight != null && interUpperLeft != null && interUpperRight != null)
            return (new Rectangle(interLowerLeft, interLowerRight, interUpperLeft, interUpperRight));
        else
            return (null);
    }
}
