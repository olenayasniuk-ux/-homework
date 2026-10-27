package com.shpp.p2p.cs.oyasniuk.assignment10;

import java.util.HashMap;
import java.util.Map;

/**
 * The main entry point for the expression calculator application.
 * Parses a mathematical expression and evaluates it using provided variable values
 * passed via command-line arguments.
 * <p>
 * Expected argument format: {@code <expression> [variable=value ...]}
 * Example usage: {@code java Assignment10Part1 "1+a/b" a=5 b=2}
 * </p>
 */
public class Assignment10Part1 {
    private static final String ABOUT_PROGRAM =
            "Програма для обчислення математичних виразів";
    private static final String EXAMPLE_EXPRESSION =
            "Приклад: 1+(2+3-(4+5-sin(45*cos(a))))/b a=2 b=7";
    private static final String INCORRECT_VARIABLE_VALUE =
            "Помилка. Неправильне числове значення для змінної %s\n";
    private static final String INVALID_ARGUMENT_FORMAT =
            "Помилка. Неправильний формат аргументу %s. Очікується формат змінна=значення (наприклад, a=2)\n";
    private static final String ERROR = "Помилка. ";

    /**
     * Main method that handles command-line arguments, initializes the parser,
     * builds the AST, and prints the calculation result or error messages.
     *
     * @param args command-line arguments where the first argument is the mathematical expression,
     *             and subsequent arguments define variable assignments in {@code name=value} format.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(ABOUT_PROGRAM);
            System.out.println(EXAMPLE_EXPRESSION);
            return;
        }

        String expressionStr = args[0].trim();

        Map<String, Double> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            String[] parts = arg.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                try {
                    double varValue = Double.parseDouble(parts[1].trim());
                    variables.put(varName, varValue);
                } catch (NumberFormatException e) {
                    System.out.printf(INCORRECT_VARIABLE_VALUE, varName);
                    return;
                }
            } else {
                System.out.printf(INVALID_ARGUMENT_FORMAT, arg);
                return;
            }
        }

        try {
            ExpressionTree parser = new ExpressionTree(expressionStr);
            Node expressionTree = parser.parse();
            double result = expressionTree.calculate(variables);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println(ERROR + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
