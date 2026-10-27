package com.shpp.p2p.cs.oyasniuk.assignment8;

import acm.graphics.GOval;

import java.awt.*;

/**
 * Represents an individual snowflake on the graphics screen.
 * Inherits from {@link GOval} and maintains its unique physical attributes
 * required to simulate falling behavior (size, speed, gravity, and swaying amplitude).
 */
public class Snowflake extends GOval {
    /** The initial X-coordinate where the snowflake was generated. */
    private int x0;

    /** The size (diameter) of the snowflake in pixels. */
    private int size;

    /** The color of the snowflake. */
    private Color color;

    /** The current vertical falling speed of the snowflake. */
    private double speed;

    /** The amplitude of the horizontal swaying (sinusoidal motion). */
    private int amplitude;

    /** The gravity force that accelerates the fall, calculated based on the snowflake's size. */
    private double gravity;

    /**
     * Constructs a new snowflake object with the specified physical and visual characteristics.
     * The snowflake is initialized just above the top edge of the screen.
     *
     * @param size      the size (diameter) of the snowflake in pixels
     * @param x0        the initial X-coordinate on the screen
     * @param color     the color of the snowflake
     * @param speed     the initial falling speed
     * @param amplitude the amplitude of horizontal oscillations
     */
    public Snowflake(int size, int x0, Color color, int speed, int amplitude) {
        super(x0, -size, size, size);
        this.x0 = x0;
        this.size = size;
        this.color = color;
        this.speed = speed;
        this.amplitude = amplitude;
        this.gravity = size / 100.0;
        setFilled(true);
        setColor(color);
    }

    /**
     * Returns the current vertical falling speed of the snowflake.
     *
     * @return the current speed along the Y-axis
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets a new vertical falling speed for the snowflake. Used to simulate acceleration.
     *
     * @param speed the new falling speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Returns the horizontal swaying amplitude of the snowflake.
     *
     * @return the amplitude of oscillations
     */
    public int getAmplitude() {
        return amplitude;
    }

    /**
     * Returns the gravity (acceleration) value calculated based on the snowflake's size.
     *
     * @return the gravitational acceleration value
     */
    public double getGravity() {
        return gravity;
    }
}
