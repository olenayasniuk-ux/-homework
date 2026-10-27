package com.shpp.p2p.cs.oyasniuk.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Assignment3Part3 provides functionality to calculate the power of a number.
 * It manually implements exponentiation for a real number (double) raised to an
 * integer exponent (int), supporting positive, negative, and zero exponents
 * without using standard Math.pow() library functions.
 */
public class Assignment3Part3 extends TextProgram {
    /**
     * Prompt message to ask the user for the base (a real number).
     */
    public static final String ASK_USER_BASE = "Введіть основу степення (дійсне число): ";
    /**
     * Prompt message to ask the user for the exponent (an integer number).
     */
    public static final String ASK_USER_EXPONENT = "Введіть показник степеня (ціле число): ";
    /**
     * Label string used before displaying the final calculated result.
     */
    public static final String ANSWER = "Відповідь: ";

    /**
     * Execution entry point for the program. It reads the base and exponent
     * from the user, invokes the manual exponentiation algorithm, and prints
     * the final result.
     */
    public void run() {
        double base = readDouble(ASK_USER_BASE);
        int exponent = readInt(ASK_USER_EXPONENT);
        println(ANSWER + raiseToPower(base, exponent));
    }

    /**
     * Calculates the value of a base raised to the power of an exponent.
     * Handles positive exponents, negative exponents (by inverting the reciprocal result),
     * and a zero exponent (which mathematically yields 1.0).
     *
     * @param base     the base number to be multiplied.
     * @param exponent the power to which the base is raised.
     * @return the result of raising the base to the specified exponent.
     */
    private double raiseToPower(double base, int exponent) {
        double result = 1;
        if (exponent > 0) {
            for (int i = 0; i < exponent; i++) {
                result *= base;
            }
        } else if (exponent < 0) {
            for (int i = 0; i < -exponent; i++) {
                result *= base;
            }
            result = 1 / result;
        }
        return result;
    }
}
