package com.shpp.p2p.cs.oyasniuk.assignment2;

import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part6 extends WindowProgram {

    // number of circles
    private static final int NUM_CIRCLES = 8;

    // radius or circles
    private static final double CIRCLE_RADIUS = 50;

    // The center of the first circle
    private static final double X0 = 50;
    private static final double Y0 = 200;

    //The displacement of the center of the second circle
    // relative to the center of the first circle
    private static final double DELTA_X = 60;
    private static final double DELTA_Y = -60;

    //The border and fill color of the circles
    private static final Color COLOR_CIRCLE = Color.GREEN;
    private static final Color COLOR_BORDER = Color.RED;

    public void run() {

        double r = CIRCLE_RADIUS;

        for (int i = 0; i < NUM_CIRCLES; i++) {
            if (i % 2 == 0) {
                drawCircle(X0 + i * DELTA_X, Y0, r);
            } else {
                drawCircle(X0 + i * DELTA_X, Y0 + DELTA_Y, r);
            }
        }
    }

    // The function draws a circle with the center
    // at (x, y) and radius r.
    private void drawCircle(double x0, double y0, double r) {
        GOval circle = new GOval(x0 - r, y0 - r, 2 * r, 2 * r);
        circle.setColor(COLOR_BORDER);
        circle.setFilled(true);
        circle.setFillColor(COLOR_CIRCLE);
        add(circle);
    }

}
