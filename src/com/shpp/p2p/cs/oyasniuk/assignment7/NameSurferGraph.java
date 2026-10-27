package com.shpp.p2p.cs.oyasniuk.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {
    /** The collection of name graphs currently chosen to be displayed on the canvas */
    LinkedHashMap<String, int[]> graphsToDraw;

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        graphsToDraw = new LinkedHashMap<>();
        addComponentListener(this);
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        graphsToDraw.clear();
        //update();
    }

    /* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        graphsToDraw.put(entry.getName(), entry.getRanks());
        //update();
    }

    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        addGridAndLabels();
        addGraphsWithLabels();
    }

    /**
     * Iterates through the list of entries and coordinates drawing
     * graphs and text labels for each active name graph on the canvas.
     */
    private void addGraphsWithLabels() {
        int colorCounter =0;

        for (String key : graphsToDraw.keySet()) {
            Color color = setGraphColor(colorCounter);
            addGraph(graphsToDraw.get(key), color);
            addLabelsOnGraph(key, graphsToDraw.get(key), color);
            colorCounter++;
        }
    }

    /**
     * Places textual labels indicating the name and its rank next
     * to the corresponding points on the graph for each decade.
     *
     * @param name  The name associated with the entry.
     * @param ranks The popularity ranks across decades.
     * @param color The text color matching the graph line.
     */
    private void addLabelsOnGraph(String name, int[] ranks, Color color) {
        double height = getHeight();
        double width = getWidth();
        double lineWidth = width / NDECADES;

        for (int i = 0; i < ranks.length; i++) {
            double x = lineWidth * i;
            double y = findY(ranks[i]);
            String textLabel = name + " " + ranks[i];

            if (ranks[i] == 0) {
                y = height - GRAPH_MARGIN_SIZE;
                textLabel = name + " *";
            }

            GLabel label = new GLabel(textLabel, x, y);
            label.setColor(color);
            add(label);
        }
    }

    /**
     * Draws connecting graphical lines representing the popularity
     * trend across the decades for a specific name entry.
     *
     * @param ranks An array of ranks to be plotted.
     * @param color The color of the line.
     */
    private void addGraph(int[] ranks, Color color) {
        double width = getWidth();
        double height = getHeight();
        double lineWidth = width / NDECADES;

        for (int i = 0; i < ranks.length - 1; i++) {
            double x1 = lineWidth * i;
            double y1 = findY(ranks[i]);
            double x2 = lineWidth * (i + 1);
            double y2 = findY(ranks[i + 1]);

            if (ranks[i] == 0) {
                y1 = height - GRAPH_MARGIN_SIZE;
            }
            if (ranks[i + 1] == 0) {
                y2 = height - GRAPH_MARGIN_SIZE;
            }

            GLine line = new GLine(x1, y1, x2, y2);
            line.setColor(color);
            add(line);
        }
    }

    /**
     * Calculates the explicit Y-coordinate for a given rank, taking
     * the top and bottom canvas margins into consideration.
     *
     * @param rank The rank integer value from the entry.
     * @return The proportional y-pixel coordinate on the canvas.
     */
    private double findY(int rank) {
        double height = getHeight();
        return GRAPH_MARGIN_SIZE + rank * (height - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
    }

    /**
     * Draws the background grid consisting of horizontal margin bounds,
     * vertical decade divider lines, and text labels for each decade year.
     */
    private void addGridAndLabels() {
        double width = getWidth();
        double height = getHeight();
        double lineWidth = width / NDECADES;

        double x1 = 0;
        double x2 = width;
        double y = GRAPH_MARGIN_SIZE;
        add(new GLine(x1, y, x2, y));

        y = height - GRAPH_MARGIN_SIZE;
        add(new GLine(x1, y, x2, y));

        for (int i = 0; i < NDECADES; i++) {
            double x = lineWidth * i;
            double y1 = 0;
            double y2 = height;
            add(new GLine(x, y1, x, y2));

            String labelYear = String.valueOf(START_DECADE + i * YEARS_IN_ONE_DECADE);
            add(new GLabel(labelYear, x, y2));
        }
    }

    /**
     * Determines a cycle of colors (Blue, Red, Magenta, Black) to
     * uniquely identify consecutively drawn name graphs.
     *
     * @param graphNumber The sequence index number of the current graph.
     * @return The specific Color object allocated for this index.
     */
    public Color setGraphColor(int graphNumber) {
        Color[] colors = {
                Color.BLUE,
                Color.RED,
                Color.MAGENTA,
                Color.BLACK};
        return colors[graphNumber % colors.length];
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
