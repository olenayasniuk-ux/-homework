package com.shpp.p2p.cs.oyasniuk.assignment2;

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part2 extends WindowProgram {

    /* The default W and H of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions. You should
     * not directly use these constants in your program; instead, use getWidth() and
     * getHeight(), which return the *exact* width and height of the window.
     */
    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 700;

    // radius of the circles
    public static final int CICLE_RADIUS = 50;

    public void run() {

        int r = CICLE_RADIUS;
        int d = CICLE_RADIUS * 2;

        drawCircle(0, 0, d, d);
        drawCircle(getWidth() - d, 0, d, d);
        drawCircle(0, getHeight() - d, d, d);
        drawCircle(getWidth() - d, getHeight() - d, d, d);
        drawRectangle(r, r, getWidth() - d, getHeight() - d);
    }

    // The function draws a circle inscribed in a rectangle
    // with its top-left vertex at (x, y), having a specific width and height.

    private void drawCircle(int x, int y, int width, int height) {
        GOval circle = new GOval(x, y, width, height);
        circle.setColor(Color.BLACK);
        circle.setFilled(true);
        circle.setFillColor(Color.BLACK);
        add(circle);
    }

    // The function draws a rectangle with its
    // top-left vertex at (x, y), having a specific width and height.

    private void drawRectangle(int x, int y, int width, int height) {
        GRect rect = new GRect(x, y, width, height);
        rect.setColor(Color.WHITE);
        rect.setFilled(true);
        rect.setFillColor(Color.WHITE);
        add(rect);

    }

}
