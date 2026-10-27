package com.shpp.p2p.cs.oyasniuk.assignment6.tm;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        for (int row = 0; row < toneMatrix.length; row++) {
            if (toneMatrix[row][column]) {
                for (int j = 0; j < result.length; j++) {
                    result[j] += samples[row][j];
                }
            }
        }
        double normalizationCoefficient = 1;
        double temp;
        for (int i = 0; i < result.length; i++) {
            temp = Math.abs(result[i]);
            if (temp > normalizationCoefficient) {
                normalizationCoefficient = temp;
            }
        }
        for (int i = 0; i < result.length; i++) {
            result[i] /= normalizationCoefficient;
        }
        return result;
    }
}
