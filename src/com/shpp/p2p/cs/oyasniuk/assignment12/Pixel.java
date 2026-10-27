package com.shpp.p2p.cs.oyasniuk.assignment12;

/**
 * Represents a single pixel in the image processing matrix.
 * It stores brightness data and tracking states used for image segmentation.
 */
public class Pixel {
    /**
     * The calculated brightness level of the pixel.
     */
    private final double brightness;
    /**
     * Indicates whether this pixel belongs to the image background.
     */
    private boolean isBackground = false;
    /**
     * Indicates whether this pixel has already been processed or grouped into a silhouette.
     */
    private boolean isGrouped = false;
    /**
     * Indicates whether this pixel lies on the outer boundary of a silhouette.
     */
    private boolean isBorder = false;

    /**
     * Constructs a new Pixel with the specified brightness level.
     *
     * @param brightness the brightness value of the pixel.
     */
    public Pixel(double brightness) {
        this.brightness = brightness;
    }

    public double getBrightness() {
        return brightness;
    }

    public boolean isBackground() {
        return isBackground;
    }

    public void setBackground(boolean background) {
        isBackground = background;
    }

    public boolean isGrouped() {
        return isGrouped;
    }

    public void setGrouped(boolean grouped) {
        isGrouped = grouped;
    }

    public boolean isBorder() {
        return isBorder;
    }

    public void setBorder(boolean border) {
        isBorder = border;
    }
}
