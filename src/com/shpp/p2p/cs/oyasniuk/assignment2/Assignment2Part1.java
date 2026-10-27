package com.shpp.p2p.cs.oyasniuk.assignment2;

import com.shpp.cs.a.console.TextProgram;

public class Assignment2Part1 extends TextProgram {
    public void run() {

        // This program solves a quadratic equation

        // input a
        // if a equals 0, then ask the user to enter a value not equal to 0
        double a = readDouble("Please enter a: ");
        while (a == 0) {
            a = readDouble("a can't be 0. Please enter a number not equal to 0: ");
        }

        // input b
        double b = readDouble("Please enter b: ");

        // input c
        double c = readDouble("Please enter c: ");

        // calculate the discriminant
        double discriminant = Math.pow(b, 2) - 4 * a * c;

        // find the roots and output the answer to the console
        if (discriminant > 0) {
            double r1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double r2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            println("There are two roots: " + r1 + " and " + r2);
        } else if (discriminant == 0) {
            double r = -b / (2 * a);
            println("There is one root: " + r);
        } else {
            println("There are no real roots");
        }

    }
}
