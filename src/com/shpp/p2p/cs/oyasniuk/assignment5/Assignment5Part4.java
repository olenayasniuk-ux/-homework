package com.shpp.p2p.cs.oyasniuk.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * A console application designed to extract a specific column from a CSV (Comma-Separated Values)
 * formatted file.
 * This class reads a structured text table file line by line, parses each row into individual fields
 * while attempting to respect double quotes, and compiles data from the requested column index.
 */
public class Assignment5Part4 extends TextProgram {
    /** The relative file path to the CSV/text table document to be processed. */
    private static final String TABLE_CSV = "src/com/shpp/p2p/cs/oyasniuk/assignment5/table.txt";

    public void run() {

        println(extractColumn(TABLE_CSV, 0));
    }
    /**
     * Reads the specified file and extracts all entries belonging to the given column index.
     * Each row in the file is processed sequentially to gather the targeted column data.
     *
     * @param filename    The path to the source text file.
     * @param columnIndex The zero-based index of the column to extract.
     * @return An {@link ArrayList} of strings containing the elements extracted from the column.
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    br.close();
                    break;
                }
                result.add(fieldsIn(line).get(columnIndex));
            }
        } catch (IOException e) {
        }
        return result;
    }
    /**
     * Parses a single line of CSV text into an ordered list of individual field values.
     * <p>
     * The method incorporates specific custom rules to handle basic CSV nuances:
     * <ul>
     *   <li>Characters enclosed within double quotes ({@code "..."}) are treated as part of a single field value.</li>
     *   <li>Comas ({@code ,}) serve as cell delimiters, provided they do not immediately follow a closing quote.</li>
     *   <li>The end of the line automatically flushes and registers the final field.</li>
     * </ul>
     * </p>
     *
     * @param line A single raw string row retrieved from the CSV file.
     * @return An {@link ArrayList} of strings representing each parsed cell/field from the row.
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> array = new ArrayList<>();
        String value = "";
        for (int i = 0; i < line.length(); i++) {
            if (i == line.length() - 1) {
                value = value + line.charAt(i);
                array.add(value);
                value = "";
            } else if (line.charAt(i) == '"') {
                for (int j = i + 1; line.charAt(j) != '"'; j++) {
                    value = value + line.charAt(j);
                    i++;
                }
                array.add(value);
                value = "";
                i++;
            } else if (line.charAt(i) == ',') {
                if (i==0){
                    array.add(value);
                    value = "";
                } else{
                    if (line.charAt(i-1)!='"'){
                        array.add(value);
                        value = "";
                    }
                }
            } else {
                value = value + line.charAt(i);
            }
        }
        return array;
    }
}
