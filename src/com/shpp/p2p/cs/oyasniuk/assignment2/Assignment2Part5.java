package com.shpp.p2p.cs.oyasniuk.assignment2;

import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part5 extends WindowProgram {

    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 40;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    public static final int APPLICATION_WIDTH = 500;
    public static final int APPLICATION_HEIGHT = 400;

    public void run() {

        // Centering the image: finding the top-left corner
        double x0 = (getWidth() - NUM_COLS * BOX_SIZE - (NUM_COLS - 1) * BOX_SPACING) / 2;
        double y0 = (getHeight() - NUM_ROWS * BOX_SIZE - (NUM_ROWS - 1) * BOX_SPACING) / 2;
        double BOX_AND_SPACING_WIDTH=BOX_SIZE +  BOX_SPACING;

        // Draw a matrix ixj of black boxes separated by "streets"
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                drawBox(x0 + j * BOX_AND_SPACING_WIDTH, y0 + i * BOX_AND_SPACING_WIDTH, BOX_SIZE);
            }
        }
    }

    // The function draws a square with its
    // top-left vertex at (x, y), having a specific size.
    private void drawBox(double x, double y, double size) {
        GRect box = new GRect(x, y, size, size);
        box.setColor(Color.BLACK);
        box.setFilled(true);
        box.setFillColor(Color.BLACK);
        add(box);

    }
}
