package alg.graphics;

import alg.misc.InterestingAlgorithm;

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

    /**
     * Intersection with another rectangle. Returns null if no intersection. Rectangle may be a line
     * if intersection is on an edge
     * @param other
     * @return
     */
    @InterestingAlgorithm
    public Rectangle intersect (Rectangle other) {

        Point2D otherLowerLeft = other.getLowerLeft();
        Point2D otherLowerRight = other.getLowerRight();
        Point2D otherUpperLeft = other.getUpperLeft();
        Point2D otherUpperRight = other.getUpperRight();

        Double  xLeft = null;
        Double  xRight = null;
        Double  yLower = null;
        Double  yUpper = null;

        if (otherLowerLeft.getX() >= lowerLeft.getX() && otherLowerLeft.getX() <= lowerRight.getX()) {
            // x is in range
            xLeft = otherLowerLeft.getX();

            if (otherLowerRight.getX() <= lowerRight.getX())
                xRight = otherLowerRight.getX();
            else
                xRight = lowerRight.getX();
        }
        else if (otherLowerLeft.getX() < lowerLeft.getX() && otherLowerRight.getX() >= lowerLeft.getX()) {
            // either it intersects from left, or completely contains:
            if (otherLowerRight.getX() >= lowerRight.getX()) {
                // contains, on X axis
                xLeft = lowerLeft.getX();
                xRight = lowerRight.getX();
            }
            else {
                xLeft = lowerLeft.getX();
                xRight = otherLowerRight.getX();
            }
        }

        if (otherUpperLeft.getY() >= lowerLeft.getY() && otherUpperLeft.getY() <= upperLeft.getY()) {
            // y is in range
            yUpper = otherUpperLeft.getY();
            if (otherLowerLeft.getY() >= lowerLeft.getY())
                yLower = otherLowerLeft.getY();
            else
                yLower = lowerLeft.getY();
        }
        else if (otherUpperLeft.getY() > upperLeft.getY() && otherLowerLeft.getY() <= upperLeft.getY()) {
            // either it intersects from top, or completely contains on Y axis:
            if (otherLowerLeft.getY() <= lowerLeft.getY()) {
                // contains, on Y axis
                yUpper = upperLeft.getY();
                yLower = lowerLeft.getY();
            }
            else {
                yLower = otherLowerLeft.getY();
                yUpper = upperLeft.getY();
            }
        }

        if (xLeft != null && xRight != null && yLower != null && yUpper != null)
            return (new Rectangle(new Point2D.Double(xLeft, yLower), new Point2D.Double(xLeft, yUpper),
                    new Point2D.Double(xRight, yLower), new Point2D.Double(xRight, yUpper)));
        else
            return (null);
    }
}
