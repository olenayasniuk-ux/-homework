package com.shpp.p2p.cs.oyasniuk.assignment7;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class NameSurferDataBase implements NameSurferConstants {
    /** Internal storage map mapping standardized lowercase names to NameSurferEntry data models */
    HashMap<String, NameSurferEntry> data;

    /* Constructor: NameSurferDataBase(filename) */

    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) {
        data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                NameSurferEntry entry = new NameSurferEntry(line);
                data.put(entry.getName().toLowerCase(), entry);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + filename, e); // або показати діалог
        }
    }

    /* Method: findEntry(name) */

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        return data.get(name.toLowerCase());
   }
}

