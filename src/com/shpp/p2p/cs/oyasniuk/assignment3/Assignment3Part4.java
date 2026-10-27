package com.shpp.p2p.cs.oyasniuk.assignment3;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * Assignment3Part4 constructs a symmetrical pyramid of bricks using the ACM graphics library.
 * The pyramid is drawn from the top level down to the base. It starts with a single brick
 * at the top and increments by one brick for each subsequent row down to the base.
 * Every row is dynamically centered horizontally relative to the window width.
 */
public class Assignment3Part4 extends WindowProgram {
    /**
     * The height of an individual brick in pixels.
     */
    public static final int BRICK_HEIGHT = 30;
    /**
     * The width of an individual brick in pixels.
     */
    public static final int BRICK_WIDTH = 50;
    /**
     * The total number of bricks at the bottom-most row (the base of the pyramid).
     */
    public static final int BRICKS_IN_BASE = 9;

    /**
     * Execution entry point for the window program. It determines the window dimensions,
     * iterates through each row of the pyramid, calculates precise coordinates for centered
     * placement, and renders the bricks row by row.
     */
    public void run() {
        double windowWidth = getWidth();
        double windowHeight = getHeight();
        double x;
        double y;

        for (int i = 0; i < BRICKS_IN_BASE; i++) {
            for (int j = 0; j < i + 1; j++) {
                x = 0.5 * windowWidth - (0.5 * (i + 1) - j) * BRICK_WIDTH;
                y = windowHeight - BRICK_HEIGHT * (BRICKS_IN_BASE - i) - 1;
                drawBrick(x, y);
            }
        }
    }

    /**
     * Instantiates a single brick object (GRect) with the given coordinates,
     * configures its border color to black, and adds it to the canvas.
     *
     * @param x the top-left X coordinate where the brick should be positioned.
     * @param y the top-left Y coordinate where the brick should be positioned.
     */
    private void drawBrick(double x, double y) {
        GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        brick.setColor(Color.BLACK);
        add(brick);
    }
}
