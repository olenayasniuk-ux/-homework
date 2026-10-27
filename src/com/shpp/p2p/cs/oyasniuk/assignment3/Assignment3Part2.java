package com.shpp.p2p.cs.oyasniuk.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Assignment3Part2 demonstrates the Collatz conjecture (also known as the "3n + 1" problem).
 * The program takes a positive integer from the user and applies a set of rules repeatedly
 * until the number becomes 1:
 * - If the number is even, it is divided by 2.
 * - If the number is odd, it is multiplied by 3 and increased by 1.
 * Each intermediate step of the calculation is printed to the console.
 */
public class Assignment3Part2 extends TextProgram {
    /**
     * Prompt message used to ask the user for an initial integer value.
     */
    public static final String ASK_USER_NUMBER = "Enter a number: ";
    /**
     * Format string template used when the current number is odd.
     */
    public static final String RESULT_NUMBER_ODD = "%d is odd so I make 3n + 1: ";
    /**
     * Format string template used when the current number is even.
     */
    public static final String RESULT_NUMBER_EVEN = "%d is even so I take half:  ";

    /**
     * Execution entry point for the program. It reads the initial number,
     * executes the main Collatz sequence loop, formats the calculations dynamically,
     * and signals the termination of the sequence when the value reaches 1.
     */
    public void run() {
        int number = readInt(ASK_USER_NUMBER);

        while (number != 1) {
            if (number % 2 == 0) {
                System.out.printf(RESULT_NUMBER_EVEN + (number / 2) + "\n", number);
                number = number / 2;
            } else {
                System.out.printf(RESULT_NUMBER_ODD + (3 * number + 1) + "\n", number);
                number = 3 * number + 1;
            }
        }

        println("The end");
    }
}
