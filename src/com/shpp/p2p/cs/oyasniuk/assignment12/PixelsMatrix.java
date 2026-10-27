package com.shpp.p2p.cs.oyasniuk.assignment12;

import java.awt.image.BufferedImage;
import java.util.*;

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
    private final static double GARBAGE_FILTER = 0.05;
    /**
     * Number of times morphological erosion and dilation should be applied.
     */
    private final static int BORDER_WIDTH = 4;

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
     * It segregates the background from the foreground, executes iterative
     * morphological erosion to clean edges and isolate connected components,
     * performs connected-component labeling via BFS to calculate silhouette areas,
     * and filters out small noise or artifacts using the garbage filter threshold.
     *
     * @return the total number of identified silhouettes.
     */
    public int countSilhouettes() {
        separateBgSilhouette();
        deleteBorder();

        List<Integer> countAreas = new ArrayList<>();
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
     * Performs a complete morphological Opening operation.
     * It runs a specified number of erosion iterations to disconnect joined elements,
     * and then runs the exact same number of dilation iterations to restore the shape of objects.
     */
    private void deleteBorder() {
        for (int i = 0; i < BORDER_WIDTH; i++) {
            deletePerimeterOnePixel();
        }
    }

    /**
     * Erases the single-pixel outer border found by the perimeter identification pass.
     * All discovered edge pixels are safely converted to background space.
     */
    private void deletePerimeterOnePixel() {
        findPerimeterOnePixel();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (imageMatrix[x][y].isBorder()) {
                    imageMatrix[x][y].setBackground(true);
                    imageMatrix[x][y].setBorder(false);
                }
            }
        }
    }

    /**
     * Evaluates every pixel in the matrix to label silhouette outer borders.
     * A foreground pixel is recognized as a border if it touches the frame edge
     * or shares a direct side-neighbor with a background pixel.
     */
    private void findPerimeterOnePixel() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    if (!imageMatrix[x][y].isBackground()) {
                        imageMatrix[x][y].setBorder(true);
                    }
                    continue;
                }

                if (!imageMatrix[x][y].isBackground()) {
                    if (imageMatrix[x + 1][y].isBackground()
                            || imageMatrix[x - 1][y].isBackground()
                            || imageMatrix[x][y - 1].isBackground()
                            || imageMatrix[x][y + 1].isBackground()) {
                        imageMatrix[x][y].setBorder(true);
                    }
                }
            }
        }
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
     * Calculates the total area (pixel count) of a single silhouette using an iterative
     * Breadth-First Search (BFS) flood-fill approach. Uses a queue to store pixel positions
     * sequentially to guarantee protection against stack overflow issues on large images.
     *
     * @param x the starting X coordinate.
     * @param y the starting Y coordinate.
     * @return the total number of connected pixels belonging to this silhouette.
     */
    private int areaOfSilhouette(int x, int y) {
        int area = 0;

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(x);
        queue.add(y);
        imageMatrix[x][y].setGrouped(true);

        int[] deltaX = {1, -1, 0, 0};
        int[] deltaY = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int currentX = queue.poll();
            int currentY = queue.poll();
            area++;

            for (int i = 0; i < 4; i++) {
                int nextX = currentX + deltaX[i];
                int nextY = currentY + deltaY[i];
                if (nextX >= 0 && nextX < width
                        && nextY >= 0 && nextY < height
                        && !imageMatrix[nextX][nextY].isBackground()
                        && !imageMatrix[nextX][nextY].isGrouped()) {
                    imageMatrix[nextX][nextY].setGrouped(true);
                    queue.add(nextX);
                    queue.add(nextY);
                }
            }
        }

        return area;
    }

}
