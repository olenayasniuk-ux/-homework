package com.shpp.p2p.cs.oyasniuk.assignment8;

import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The main application class that handles the snowfall animation.
 * Snowflakes smoothly fall down, accelerating due to gravity and swaying side to side.
 * Clicking the mouse toggles or reverses the wind force affecting the snow trajectory.
 */
public class Assignment8Part1 extends WindowProgram {
    /** The default width of the application window. */
    public static final int APPLICATION_WIDTH = 800;
    /** The default height of the application window. */
    public static final int APPLICATION_HEIGHT = 600;
    /** The minimum diameter of a generated snowflake. */
    private static final int MIN_SIZE = 15;
    /** The maximum diameter of a generated snowflake. */
    private static final int MAX_SIZE = 40;
    /** The minimum horizontal swaying amplitude. */
    private static final int MIN_AMPLITUDE = 20;
    /** The maximum horizontal swaying amplitude. */
    private static final int MAX_AMPLITUDE = 30;
    /** The minimum initial falling speed. */
    private static final int MIN_SPEED = 10;
    /** The maximum initial falling speed. */
    private static final int MAX_SPEED = 30;
    /** The pause duration between animation frames in milliseconds. */
    private static final double PAUSE_TIME = 1000.0 / 10;
    /** The force applied by the wind along the X-axis. */
    private static final int WIND_FORCE = 15;
    /** The number of snowflakes created during a single generation step. */
    private static final int SNOW_DENSITY = 5;

    /** Flag indicating whether the wind is currently blowing. */
    private boolean isWind = false;
    /** Flag for the wind direction: {@code true} for left, {@code false} for right. */
    private boolean isLeftWind = false;

    /** Random generator used to assign randomized properties to snowflakes. */
    private final RandomGenerator rgen = RandomGenerator.getInstance();
    /** List storing all active snowflakes currently displayed on the screen. */
    private final ArrayList<Snowflake> snowflakes = new ArrayList<>();

    /**
     * The entry point of the WindowProgram.
     * Initializes mouse listeners and starts the infinite loop animating the snowfall.
     */
    public void run() {
        addMouseListeners();
        while (true) {
            generateSnowflakes();
            moveSnowflakes();
            pause(PAUSE_TIME);
        }
    }

    /**
     * Updates the position of all active snowflakes on the screen.
     * Applies gravity acceleration, computes displacements, moves objects, and removes
     * snowflakes that have fully fallen below the bottom boundary of the window.
     */
    private void moveSnowflakes() {
        Iterator<Snowflake> iterator = snowflakes.iterator();
        while (iterator.hasNext()) {
            Snowflake snowflake = iterator.next();

            // Remove the snowflake if it passes the bottom window edge
            if ((snowflake.getY() + snowflake.getHeight()) >= getHeight()) {
                remove(snowflake);
                iterator.remove();
            }

            // Apply gravity acceleration to the speed
            snowflake.setSpeed(snowflake.getSpeed() + snowflake.getGravity());
            double deltaX = findDeltaX(snowflake);
            double deltaY = findDeltaY(snowflake);
            snowflake.move(deltaX, deltaY);
        }
    }

    /**
     * Computes the vertical displacement (delta Y) for a snowflake during the current frame.
     *
     * @param snowflake the snowflake object
     * @return the vertical shift amount, matching the current speed
     */
    private double findDeltaY(Snowflake snowflake) {
        return snowflake.getSpeed();
    }

    /**
     * Computes the horizontal displacement (delta X) for a snowflake during the current frame.
     * If the wind is active, shifts the snowflake by a fixed wind force factor.
     * Otherwise, calculates a natural sinusoidal swaying movement.
     *
     * @param snowflake the snowflake object
     * @return the horizontal shift amount
     */
    private double findDeltaX(Snowflake snowflake) {
        double deltaX;

        if (isWind) {
            deltaX = (isLeftWind) ? -WIND_FORCE : WIND_FORCE;
        } else {
            deltaX = snowflake.getAmplitude() *
                    (Math.sin(snowflake.getY() + snowflake.getSpeed()) - Math.sin(snowflake.getY()));
        }
        return deltaX;
    }

    /**
     * Generates a batch of new snowflakes based on the {@link #SNOW_DENSITY} constant,
     * adding them both to the canvas and the tracking list.
     */
    private void generateSnowflakes() {
        Snowflake newSnowflake;
        for (int i = 0; i < SNOW_DENSITY; i++) {
            newSnowflake = generateSnowflake();
            add(newSnowflake);
            snowflakes.add(newSnowflake);
        }
    }

    /**
     * Creates a single {@link Snowflake} instance with randomized properties
     * (size, X-coordinate, color, initial speed, amplitude) within the predefined bounds.
     *
     * @return a new snowflake object with randomized attributes
     */
    private Snowflake generateSnowflake() {
        int size = rgen.nextInt(MIN_SIZE, MAX_SIZE);
        int x0 = rgen.nextInt(getWidth() - size) + 1;
        Color color = rgen.nextColor();
        int speed = rgen.nextInt(MIN_SPEED, MAX_SPEED);
        int amplitude = rgen.nextInt(MIN_AMPLITUDE, MAX_AMPLITUDE);
        return new Snowflake(size, x0, color, speed, amplitude);
    }

    /**
     * Mouse click event handler.
     * Toggles the wind state (on/off). If the wind is toggled on,
     * it also reverses the wind direction (from left to right or vice versa).
     *
     * @param me the mouse event object
     */
    public void mouseClicked(MouseEvent me) {
        isWind = !isWind;
        if (isWind) {
            isLeftWind = !isLeftWind;
        }
    }

}
