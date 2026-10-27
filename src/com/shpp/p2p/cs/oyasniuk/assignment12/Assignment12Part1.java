package com.shpp.p2p.cs.oyasniuk.assignment12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The main entry point of the silhouette counting application.
 * This class handles command-line arguments, reads an image file,
 * and initiates the silhouette recognition and counting process.
 */
public class Assignment12Part1 {
    /**
     * Error message format string used when the specified image file cannot be found.
     * Contains a {@code %s} placeholder for the file name and ends with a newline character.
     */
    private static final String FILE_NOT_FOUND = "Error: File %s not found.\n";

    /**
     * The default image file name used for silhouette analysis
     * if no path argument is provided via the command line.
     */
    private static final String DEFAULT_FILE = "test.jpg";

    /**
     * Error message prefix used when an {@link IOException} occurs
     * during the image reading process.
     */
    private static final String ERROR_READ_FILE = "Error reading the image file: ";

    /**
     * Standard main method. It loads an image file (either from arguments or a default path)
     * and prints the total number of detected silhouettes to the console.
     *
     * @param args command-line arguments, where the first argument can be the image file path.
     */
    public static void main(String[] args) {

        String fileName = DEFAULT_FILE;

        if (args.length > 0) {
            fileName = args[0];
        }

        try {
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.printf(FILE_NOT_FOUND, fileName);
                return;
            }

            BufferedImage image = ImageIO.read(file);
            PixelsMatrix pixelsMatrix = new PixelsMatrix(image);

            System.out.println(pixelsMatrix.countSilhouettes());

        } catch (IOException e) {
            System.out.println(ERROR_READ_FILE + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

