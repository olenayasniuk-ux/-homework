package com.shpp.p2p.cs.oyasniuk.assignment11;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the entire image as a matrix of Pixel objects.
 * Contains algorithms for background estimation, image thresholding,
 * and counting distinct silhouettes using depth-first search (DFS).
 */
public class PixelsMatrix {
    /**
     * The width of the image matrix.
     */
    private final int width;
    /**
     * The height of the image matrix.
     */
    private final int height;
    /**
     * The 2D array storing pixel data.
     */
    private final Pixel[][] imageMatrix;
    /**
     * Sensitivity threshold for background detection.
     */
    private final static int SENSITIVITY = 40;
    /**
     * Percentage filter coefficient to ignore small noise or artifacts.
     */
    private final static double GARBAGE_FILTER = 0.01;

    /**
     * Constructs a PixelsMatrix from a {@link BufferedImage}.
     * Converts standard RGB values into brightness using a standard grayscale formula.
     *
     * @param image the source image to analyze.
     */
    public PixelsMatrix(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        imageMatrix = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                double brightness = 0.299 * red + 0.587 * green + 0.114 * blue;
                imageMatrix[x][y] = new Pixel(brightness);
            }
        }
    }

    /**
     * Counts the total number of silhouettes in the image matrix.
     * It filters out background pixels, groups adjacent object pixels,
     * and filters out small noise using the garbage filter threshold.
     *
     * @return the total number of identified silhouettes.
     */
    public int countSilhouettes() {
        List<Integer> countAreas = new ArrayList<>();
        separateBgSilhouette();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!imageMatrix[x][y].isBackground() && !imageMatrix[x][y].isGrouped()) {
                    countAreas.add(areaOfSilhouette(x, y));
                }
            }
        }

        if (countAreas.isEmpty()) {
            return 0;
        }

        final int maxArea = Collections.max(countAreas);
        countAreas.removeIf(area -> area < GARBAGE_FILTER * maxArea);

        return countAreas.size();
    }

    /**
     * Separates the background from silhouettes based on estimated background brightness.
     * Pixels close to the background brightness within the sensitivity range are marked as background.
     */
    private void separateBgSilhouette() {
        double bgBrightness = findBgBrightness();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double delta = Math.abs(imageMatrix[x][y].getBrightness() - bgBrightness);
                if (delta < SENSITIVITY) {
                    imageMatrix[x][y].setBackground(true);
                    imageMatrix[x][y].setGrouped(true);
                }
            }
        }
    }

    /**
     * Estimates the background brightness by sampling all four borders of the image.
     * It collects the brightness values of all border pixels and finds the median.
     *
     * @return the median brightness value of the image borders.
     */
    private double findBgBrightness() {
        List<Double> brightnessList = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            brightnessList.add(imageMatrix[x][0].getBrightness());
            brightnessList.add(imageMatrix[x][height - 1].getBrightness());
        }

        for (int y = 1; y < height - 1; y++) {
            brightnessList.add(imageMatrix[0][y].getBrightness());
            brightnessList.add(imageMatrix[width - 1][y].getBrightness());
        }

        Collections.sort(brightnessList);

        return brightnessList.get(brightnessList.size() / 2);
    }

    /**
     * Calculates the total area (pixel count) of a single silhouette using a recursive flood-fill approach.
     * It marks all discovered pixels as grouped to avoid infinite recursion.
     *
     * @param x the starting X coordinate.
     * @param y the starting Y coordinate.
     * @return the total number of connected pixels belonging to this silhouette.
     */
    private int areaOfSilhouette(int x, int y) {
        if (x < 0 || x >= width
                || y < 0 || y >= height
                || imageMatrix[x][y].isBackground()
                || imageMatrix[x][y].isGrouped()) {
            return 0;
        }

        imageMatrix[x][y].setGrouped(true);
        int area = 1;
        area += areaOfSilhouette(x + 1, y);
        area += areaOfSilhouette(x - 1, y);
        area += areaOfSilhouette(x, y + 1);
        area += areaOfSilhouette(x, y - 1);

        return area;
    }

}
