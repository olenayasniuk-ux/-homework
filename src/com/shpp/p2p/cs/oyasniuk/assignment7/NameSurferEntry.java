package com.shpp.p2p.cs.oyasniuk.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

public class NameSurferEntry implements NameSurferConstants {
    /** The original name value associated with this entry */
    private String name;

    /** The array array representing sequence index rankings across recorded intervals */
    private int[] ranks = new int[NDECADES];

    /* Constructor: NameSurferEntry(line) */

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        String[] a = line.split(" ");
        name = a[0];
        for (int i = 1; i < a.length; i++) {
            ranks[i - 1] = Integer.parseInt(a[i]);
        }
    }

    /* Method: getName() */

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the complete array containing popularity ranks for all decades.
     *
     * @return An integer array containing rank values.
     */
    public int[] getRanks() {
        return ranks;
    }

    /* Method: getRank(decade) */

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        return ranks[decade];
    }

    /* Method: toString() */

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        String result = name + " [";
        for (int i = 0; i < ranks.length; i++) {
            result += ranks[i];
            if (i != ranks.length - 1) {
                result += " ";
            }
        }
        result += "]";

        return result;
    }

}

