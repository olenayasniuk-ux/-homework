package com.shpp.p2p.cs.oyasniuk.assignment3;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

import static java.lang.Math.*;

/**
 * Assignment3Part6 animates a circular progress indicator (Progress Ring) with a percentage
 * counter centered in the middle of the screen.
 * The program utilizes trigonometry to sequentially plot smaller progress dots along the perimeter
 * of a larger virtual ring, while dynamically managing timing intervals to ensure the entire
 * animation completes as closely as possible to the target duration.
 */
public class Assignment3Part6 extends WindowProgram {
    /**
     * The outer radius of the progress ring track in pixels.
     */
    private static final double RING_RADIUS = 100;  //100
    /**
     * The thickness (width) of the progress ring in pixels.
     */
    private static final double RING_WIDTH = 20;   //20
    /**
     * Flag determining whether the boundary borders of the ring track should be drawn.
     */
    private static final boolean RING_BOUNDARY = true;  //false
    /**
     * The color of the loading ring and its progress circles.
     */
    private static final Color RING_COLOR = Color.GREEN;  //Color.GREEN
    /**
     * The total number of small circles that will compose the completed progress track.
     */
    private static final int NUMBER_PROGRESS_CIRCLES = 100;  //100
    /**
     * The color of the percentage text label.
     */
    private static final Color TEXT_COLOR = Color.GREEN;   //Color.GREEN
    /**
     * The font family name used for the percentage label.
     */
    private static final String TEXT_FONT = "Arial";   //"Arial"
    /**
     * The font size in points for the percentage text.
     */
    private static final int TEXT_SIZE = 50; //50
    /**
     * The default initial width of the application window in pixels.
     */
    public static final int APPLICATION_WIDTH = 600;   //600
    /**
     * The default initial height of the application window in pixels.
     */
    public static final int APPLICATION_HEIGHT = 500;   //500
    /**
     * Total desired duration for the full animation sequence in milliseconds (5 seconds).
     */
    private static final double TIME_SECONDS = 5000;   //5000
    /**
     * The initial starting angle in radians (equal to -90 degrees, which points straight up).
     */
    private static final double THETA_0 = -0.5 * PI;   //-0.5 * PI
    /**
     * Calculated horizontal center point of the current graphics canvas.
     */
    double centerX;
    /**
     * Calculated vertical center point of the current graphics canvas.
     */
    double centerY;

    /**
     * Execution entry point for the animation program. It configures the canvas layout,
     * initializes window center vectors, tracks accurate execution metrics, and loops through
     * mathematical angles to draw the progress path and update the percentage text.
     */
    public void run() {
        double theta = THETA_0;
        double finishTheta = THETA_0 + 2.0 * PI;
        double currentPercentageProgress = 0;
        long startTimeCurrentCycle;
        double deltaTime = 0;
        double pause = TIME_SECONDS / NUMBER_PROGRESS_CIRCLES;
        //long timeStart = System.currentTimeMillis();

        setBackground(Color.BLACK);
        centerX = getWidth() / 2.0;
        centerY = getHeight() / 2.0;

        if (RING_BOUNDARY) {
            drawRingBoundary(RING_RADIUS, RING_WIDTH);
        }

        while (theta < finishTheta) {
            startTimeCurrentCycle = System.currentTimeMillis();
            GLabel textLabel = createPercentageLabel((int) currentPercentageProgress);
            add(textLabel);
            drawProgressCircle(theta);
            theta = theta + 2 * PI / NUMBER_PROGRESS_CIRCLES;
            if (pause >= deltaTime) {
                pause(round(pause - deltaTime));
            }
            remove(textLabel);
            currentPercentageProgress = currentPercentageProgress + 100.0 / NUMBER_PROGRESS_CIRCLES;
            deltaTime = (System.currentTimeMillis() - startTimeCurrentCycle) - (pause - deltaTime);
        }

        GLabel textLabel = createPercentageLabel(100);
        add(textLabel);
        //println(System.currentTimeMillis() - timeStart);
        //exit();
    }

    /**
     * Renders a basic circle centered around a given point (x0, y0).
     *
     * @param x0     the X coordinate of the circle's center point.
     * @param y0     the Y coordinate of the circle's center point.
     * @param r      the radius of the circle.
     * @param color  the color applied to the line or fill of the circle.
     * @param filled true if the shape should be solid; false if it is just a wireframe outline.
     */
    private void drawCircle(double x0, double y0, double r, Color color, boolean filled) {
        GOval circle = new GOval(x0 - r, y0 - r, 2 * r, 2 * r);
        circle.setColor(color);
        circle.setFilled(filled);
        circle.setFillColor(color);
        add(circle);
    }

    /**
     * Draws the structural inner and outer wireframe boundaries of the track ring.
     *
     * @param radius the absolute outer radius of the track boundary.
     * @param width  the width defining the track gap between the two concentric borders.
     */
    private void drawRingBoundary(double radius, double width) {
        drawCircle(centerX, centerY, radius, RING_COLOR, false);
        drawCircle(centerX, centerY, radius - width, RING_COLOR, false);
    }

    /**
     * Calculates the vector coordinates for a milestone on the track using trigonometry,
     * and plots a small filled progress indicator bubble.
     *
     * @param theta the current geometric angle in radians representing progress.
     */
    private void drawProgressCircle(double theta) {
        double x = centerX + (RING_RADIUS - RING_WIDTH / 2) * cos(theta);
        double y = centerY + (RING_RADIUS - RING_WIDTH / 2) * sin(theta);
        drawCircle(x, y, RING_WIDTH / 2, RING_COLOR, true);
    }

    /**
     * Instantiates and configures a text label displaying a specific numerical progress percentage.
     *
     * @param i the numeric percentage value (0 to 100) to render inside the label.
     * @return a configured GLabel instance positioned inside the canvas boundary.
     */
    private GLabel createPercentageLabel(int i) {
        GLabel label = new GLabel(i + "%", 0, 0);
        label.setFont(TEXT_FONT + "-" + TEXT_SIZE);
        label.setColor(TEXT_COLOR);
        centeredLabel(label);
        return label;
    }

    /**
     * Dynamically shifts the layout location of a GLabel object so that its text bounding box
     * aligns perfectly with the true center of the application screen.
     *
     * @param label the target GLabel graphical text component to move.
     */
    private void centeredLabel(GLabel label) {
        label.setLocation(centerX - label.getWidth() / 2,
                centerY - label.getHeight() / 2 + label.getFont().getSize());
    }

}
