package com.shpp.p2p.cs.oyasniuk.assignment2;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part4 extends WindowProgram {

    // The program draws a flag with three vertical stripes

    public static final int FLAG_WIDTH = 301;
    public static final int FLAG_HEIGHT = 200;
    public static final String COUNTRY="Andorra";

    public static final int APPLICATION_WIDTH = 600;
    public static final int APPLICATION_HEIGHT = 500;


    public void run() {

        double widthOneColor = FLAG_WIDTH / 3;

        drawRectangle(Color.BLUE, (getWidth() - FLAG_WIDTH) / 2,
                (getHeight() - FLAG_HEIGHT) / 2, widthOneColor, FLAG_HEIGHT);
        drawRectangle(Color.YELLOW, (getWidth() - FLAG_WIDTH) / 2 + widthOneColor,
                (getHeight() - FLAG_HEIGHT) / 2, widthOneColor, FLAG_HEIGHT);
        drawRectangle(Color.RED, (getWidth() - FLAG_WIDTH) / 2 + 2 * widthOneColor,
                (getHeight() - FLAG_HEIGHT) / 2, widthOneColor, FLAG_HEIGHT);
        drawText("Flag of "+COUNTRY);
    }

    // The function draws a rectangle with its
    // top-left vertex at (x, y), having a specific width and height.

    private void drawRectangle(Color color, double x, double y, double width, double height) {
        GRect rect = new GRect(x, y, width, height);
        rect.setColor(color);
        rect.setFilled(true);
        rect.setFillColor(color);
        add(rect);
    }

    //The function draws text at the bottom-right corner

    private void drawText(String text) {
        GLabel label = new GLabel(text, 0, 0);
        label.setFont("Comic Sans-20");
        label.setLocation(getWidth() - label.getWidth(),
                getHeight() - label.getHeight() + label.getFont().getSize());
        add(label);
    }

}
