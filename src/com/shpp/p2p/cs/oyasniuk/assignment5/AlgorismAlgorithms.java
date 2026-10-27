package com.shpp.p2p.cs.oyasniuk.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * A console application that performs column-addition on arbitrarily large
 * non-negative integers represented as strings.
 * This class circumvents the overflow limitations of standard primitive data types
 * like {@code int} or {@code long} by processing numbers digit by digit.
 */
public class AlgorismAlgorithms extends TextProgram {
    /**
     * A flag indicating whether a carry-over value of 1 should be added
     * to the next column during addition.
     */
    boolean withOne = false;

    /**
     * The entry point of the program execution.
     * Continuously prompts the user to enter two numeric strings, adds them together,
     * and prints the mathematical equation to the console.
     */
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        int diff = n1.length() - n2.length();
        if (diff > 0) {
            n2 = addLeadingZeros(n2, diff);
        } else if (diff < 0) {
            n1 = addLeadingZeros(n1, -diff);
        }
        return addTwoNumbersSameLength(n1, n2);
    }

    /**
     * Pads a given numeric string with a specified number of leading zeros.
     * This is used to align numbers of different lengths for column addition.
     *
     * @param num  The numeric string to be padded.
     * @param diff The number of leading zeros to add.
     * @return The padded numeric string.
     */
    private String addLeadingZeros(String num, int diff) {
        for (int i = 0; i < diff; i++) {
            num = "0" + num;
        }
        return num;
    }

    /**
     * Adds two character digits together, optionally including a carry-over value.
     *
     * @param ch1     The character representing the first digit.
     * @param ch2     The character representing the second digit.
     * @param withOne {@code true} if a carry-over of 1 should be added; {@code false} otherwise.
     * @return A string representation of the sum of the digits (can be 1 or 2 digits long).
     */
    private String addTwoChars(char ch1, char ch2, boolean withOne) {
        int value1 = ch1 - '0';
        int value2 = ch2 - '0';
        if (withOne) {
            value1++;
        }
        return String.valueOf(value1 + value2);
    }

    /**
     * Adds two numeric strings of identical length using column addition.
     * Iterates from the last digit to the first, tracking carry-overs via
     * the {@code withOne} field.
     *
     * @param num1 The first padded numeric string.
     * @param num2 The second padded numeric string.
     * @return A string representing the final sum of both numbers.
     */
    private String addTwoNumbersSameLength(String num1, String num2) {
        String result = "";
        String interimResult = "";
        for (int i = num1.length() - 1; i >= 0; i--) {
            interimResult = addTwoChars(num1.charAt(i), num2.charAt(i), withOne);
            if (withOne) {
                withOne = false;
            }
            if (interimResult.length() == 1) {
                result = interimResult + result;
            } else if (interimResult.length() == 2) {
                result = interimResult.charAt(1) + result;
                withOne = true;
            }
        }
        if (withOne) {
            result = "1" + result;
            withOne = false;
        }

        return result;
    }
}
