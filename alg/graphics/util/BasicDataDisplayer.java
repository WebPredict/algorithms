package alg.graphics.util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/16/15
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicDataDisplayer  extends JFrame {

    private Double [][][] dataPoints;
    private Double minX;
    private Double minY;
    private Double maxX;
    private Double maxY;
    private int sideOffset = 20;
    private double zoom = 1.5;
    private boolean labels;

    public BasicDataDisplayer(Double [][][] dataPoints, int width, int height, double zoom) {
        super("Basic Line Drawing for " + dataPoints.length + " line(s) with zoom factor " + zoom);

        this.dataPoints = dataPoints;
        this.zoom = zoom;

        for (int i = 0; i < dataPoints.length; i++) {
            for (int j = 0; j < dataPoints [i].length; j++) {
                if (minX == null)
                    minX = dataPoints[i][j][0];
                else if (minX > dataPoints[i][j][0])
                    minX = dataPoints[i][j][0];

                if (minY == null)
                    minY = dataPoints[i][j][1];
                else if (minY > dataPoints[i][j][1])
                    minY = dataPoints[i][j][1];

                if (maxX == null)
                    maxX = dataPoints[i][j][0];
                else if (maxX < dataPoints[i][j][0])
                    maxX = dataPoints[i][j][0];

                if (maxY == null)
                    maxY = dataPoints[i][j][1];
                else if (maxY < dataPoints[i][j][1])
                    maxY = dataPoints[i][j][1];
            }
        }
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        double xRange = maxX - minX;
        double yRange = maxY - minY;

        int     width = getWidth();
        int     height = getHeight();
        int     xOffset = width / 2;
        int     yOffset = height / 2;

        double  scaleX = (double)(width - sideOffset * 2) / (xRange * (1d / zoom));
        double  scaleY = (double)(height - sideOffset * 2) / (yRange * (1d / zoom));

        if (labels)
            g2d.drawString("ORIGIN", xOffset, yOffset);

        Color [] colors = new Color[] {Color.black, Color.blue, Color.red, Color.orange, Color.yellow, Color.green, Color.cyan, Color.pink, Color.magenta};
        for (int i = 0; i < dataPoints.length; i++) {
            double xPrev = dataPoints [i][0][0] * scaleX;
            double yPrev = dataPoints [i][0][1] * scaleY;
            for (int j = 1; j < dataPoints[i].length; j++) {

                double xCur = dataPoints[i][j][0] * scaleX;
                double yCur = dataPoints[i][j][1] * scaleY;

                if (labels)
                    g2d.drawString(String.valueOf(i), xOffset + (int)xPrev, yOffset + (int)yPrev);

                //System.out.println("ith line: " + i + "from X: " + xPrev + " from Y: " + yPrev + " to X: " + xCur + " to Y: " + yCur);

                g2d.setColor(colors [i % colors.length]);
                g2d.drawLine(xOffset + (int)xPrev, yOffset + (int)yPrev, xOffset + (int)xCur, yOffset + (int)yCur);

                if (labels && j == dataPoints.length - 1)
                   g2d.drawString("END " + i, xOffset + (int)xCur, yOffset + (int)yCur);

                xPrev = xCur;
                yPrev = yCur;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BasicDataDisplayer(new Double[][][] {{{0d, 0d}, {1d, 1d}, {2d, 4d}, {1.5d, 8d}}}, 480, 480, 2).setVisible(true);
            }
        });
    }
}