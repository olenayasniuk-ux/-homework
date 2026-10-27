package com.shpp.p2p.cs.oyasniuk.assignment9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The {@code Assignment9Part1} class parses and evaluates simple
 * mathematical expressions passed via command line arguments.
 * It supports numbers (including decimals), variables, arithmetic
 * operations (+, -, *, /), and exponentiation (^).
 */

public class Assignment9Part1 {
    /**
     * Error message when no mathematical expression is provided.
     */
    public static final String ERROR_MATH_EXP_IS_EMPTY = "Помилка: Не вказано математичний вираз.";
    /**
     * Error message when the expression contains invalid characters.
     */
    public static final String ERROR_NOT_VARIF_MATH_EXP = "Помилка: У математичному виразі є недопустимі символи.";
    /**
     * Error message when variable values are specified incorrectly.
     */
    public static final String ERROR_NOT_VARIF_VALUES = "Помилка: Неправильно задані значення змінних.";
    /**
     * Error message when a division by zero is attempted.
     */
    public static final String ERROR_DIVIDE_ZERO = "Помилка: На нуль ділити не можна!";
    /**
     * Error message when the undefined operation 0^0 is encountered.
     */
    public static final String POW_ZERO_ZERO = "Помилка: 0^0 невизначено.";
    /**
     * Format template for errors when power calculation results in NaN or Infinity.
     */
    public static final String NAN_INFINITY_POW = "Помилка: Не можна обчислити %s %s %s.";
    /**
     * Format template for errors when multiple values are assigned to the same variable.
     */
    public static final String TWO_VALUES_LETTER = "Помилка: У вас два значення для змінної %s.";
    /**
     * Error message when some variables in the expression lack assigned values.
     */
    public static final String NOT_ALL_VALUE = "Помилка: Ви не ввели значення для всіх змінних.";

    /**
     * Program entry point. Controls the application lifecycle: reads arguments,
     * validates them, triggers decomposition, handles variable substitution, and computes the result.
     *
     * @param args Command line arguments. First element is the expression, subsequent elements are variables (e.g., x=number).
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException(ERROR_MATH_EXP_IS_EMPTY);
            }
            String initialExp = clearString(args[0]);
            varifyMathExp(initialExp);
            ArrayList<String> decompArray = parse(initialExp);
            ArrayList<String> calcArray;

            if (args.length > 1) {
                varifyValues(args);
                HashMap<String, String> varValuesMap = fillVarValuesMap(args);
                calcArray = substituteVars(decompArray, varValuesMap);
            } else {
                calcArray = new ArrayList<>(decompArray);
            }

            System.out.println(calculate(calcArray));

        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sanitizes the input string by converting it to lowercase, removing all spaces,
     * and replacing commas with dots to ensure uniform decimal number handling.
     *
     * @param s The input string to sanitize.
     * @return The cleaned lowercase string without spaces or commas.
     */
    private static String clearString(String s) {
        s = s.toLowerCase();
        s = s.replace(" ", "");
        s = s.replace(",", ".");
        return s;
    }

    /**
     * Validates the mathematical expression for forbidden characters using a regular expression.
     * Allowed characters are lowercase Latin letters, digits, dots, and the operators: +, -, *, /, ^.
     *
     * @param initialExp The mathematical expression to validate.
     * @throws IllegalArgumentException If the expression contains any invalid character.
     */
    private static void varifyMathExp(String initialExp) {
//        if (!initialExp.matches("^[+-]*(?:[0-9]+(?:\\\\.[0-9]+)?|[a-z])(?:[+*/^\\\\-][+-]*(?:[0-9]+(?:\\\\.[0-9]+)?|[a-z]))*$")) {
//            throw new IllegalArgumentException(ERROR_NOT_VARIF_MATH_EXP);
//        }
        if (!initialExp.matches("^[a-z0-9-+*/^.]+$")) {
            throw new IllegalArgumentException(ERROR_NOT_VARIF_MATH_EXP);
        }
    }

    /**
     * Decomposes the mathematical expression into a list of separate tokens (numbers, variables, operators).
     * It simplifies sign sequences and inserts spaces around operators prior to splitting.
     *
     * @param exp The sanitized mathematical expression.
     * @return An {@link ArrayList} of strings representing individual tokens.
     */
    private static ArrayList<String> parse(String exp) {
        exp = removeExtraPlusMinus(exp);
        // number and action, add a space between them
        exp = exp.replaceAll("(\\d+)([+\\-*/^])", "$1 $2");
        // letter and action, add a space between them
        exp = exp.replaceAll("([a-z]+)([+\\-*/^])", "$1 $2");
        // space and action, add a space
        exp = exp.replaceAll("\\s+([+\\-*/^])", " $1 ");
        // plus number, plus remove
        exp = exp.replaceAll("\\+(\\d+)", "$1");
        // plus letter, plus remove
        exp = exp.replaceAll("\\+([a-z]+)", "$1");

        return new ArrayList<>(Arrays.asList(exp.split(" ")));
    }

    /**
     * Simplifies redundant sequences of plus and minus signs into a single equivalent operator
     * based on standard algebraic rules (e.g., "--" becomes "+", and "+-" becomes "-").
     *
     * @param exp The mathematical expression string.
     * @return The string with simplified sign operators.
     */
    private static String removeExtraPlusMinus(String exp) {
        while (exp.contains("--")
                || exp.contains("++")
                || exp.contains("+-")
                || exp.contains("-+")) {
            exp = exp.replace("--", "+");
            exp = exp.replace("++", "+");
            exp = exp.replace("+-", "-");
            exp = exp.replace("-+", "-");
        }
        return exp;
    }

    /**
     * Validates the variable definitions passed as application parameters.
     * Expected format: a single lowercase Latin letter, an equals sign, and an optional signed integer or decimal.
     *
     * @param args The full array of command line arguments, starting examination from index 1.
     * @throws IllegalArgumentException If any variable parameter does not match the validation pattern.
     */
    private static void varifyValues(String[] args) {
        for (int i = 1; i < args.length; i++) {
            String value = clearString(args[i]);
            if (!value.matches("^[a-z]=[+-]?[0-9]+(\\.[0-9]+)?$")) {
                throw new IllegalArgumentException(ERROR_NOT_VARIF_VALUES);
            }
        }
    }

    /**
     * Parses variable definitions from command line parameters and loads them into a lookup map.
     * Verifies that no variable is defined more than once.
     *
     * @param args The command line arguments where variable values start at index 1.
     * @return A {@link HashMap} linking variable letters (keys) to their string numeric values.
     * @throws IllegalArgumentException If duplicate values are detected for the same variable.
     */
    private static HashMap<String, String> fillVarValuesMap(String[] args) {
        HashMap<String, String> varValuesMap = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String value = clearString(args[i]);
            String letter = value.substring(0, 1);
            String number = value.substring(2);
            if (!varValuesMap.containsKey(letter)) {
                varValuesMap.put(letter, number);
            } else {
                throw new IllegalArgumentException(String.format(TWO_VALUES_LETTER, letter));
            }
        }
        return varValuesMap;
    }

    /**
     * Iterates through the token array and substitutes all matching variables
     * (including those prefixed with a negative sign, e.g., "-x") with their defined numeric values.
     *
     * @param decompArray The array list of decomposed expression tokens.
     * @param varValues   The lookup map containing valid variable values.
     * @return A new token list where all variable tokens are replaced with numbers.
     */
    private static ArrayList<String> substituteVars(ArrayList<String> decompArray, HashMap<String, String> varValues) {
        ArrayList<String> calcArray = new ArrayList<>();
        for (String s : decompArray) {
            if (s.matches("-?[a-z]")) {
                s = substitute(s, varValues);
            }
            calcArray.add(s);
        }
        return calcArray;
    }

    /**
     * Substitutes a specific variable letter within a token with its matching numeric value from the map.
     * Guarantees that values are supplied for all variables present.
     *
     * @param s         The current token containing a variable (potentially with a leading minus).
     * @param varValues The lookup map containing valid variable values.
     * @return A string token with the variable replaced by its numeric value and signs simplified.
     * @throws IllegalArgumentException If no value was provided for the variable.
     */
    private static String substitute(String s, HashMap<String, String> varValues) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                if (varValues.get(String.valueOf(ch)) != null) {
                    s = s.substring(0, i) + varValues.get(String.valueOf(ch));
                    s = removeExtraPlusMinus(s);
                } else {
                    throw new IllegalArgumentException(NOT_ALL_VALUE);
                }
            }
        }
        return s;
    }

    /**
     * Primary evaluation coordinator. Processes arithmetic tokens sequentially based on mathematical precedence:
     * power operations first, followed by multiplication/division, and lastly addition/subtraction.
     *
     * @param calcArray The token list ready for evaluation (all variables are replaced by numbers).
     * @return A string representing the final numeric result of the calculation.
     */
    private static String calculate(ArrayList<String> calcArray) {
        calcPows(calcArray);
        calcBasicOps(calcArray, "*", "/");
        calcBasicOps(calcArray, "+", "-");
        return calcArray.get(0);
    }

    /**
     * Searches for and evaluates all power operations (^) within the token array.
     * The processing is conducted right-to-left to respect the right-associative rule of exponentiation.
     *
     * @param calcArray The expression token array list.
     */
    private static void calcPows(ArrayList<String> calcArray) {
        for (int i = calcArray.size() - 1; i >= 0; i--) {
            String op = calcArray.get(i);
            if (op.equals("^")) {
                calcReplaceClean(calcArray, op, i);
            }
        }
    }

    /**
     * Replaces an operator and its two adjacent operands inside the token list with the computed evaluation result,
     * dynamically shrinking the array size.
     *
     * @param calcArray The mutable token list modified in place.
     * @param op        The mathematical operator being processed.
     * @param i         The current index of the operator within the array list.
     */
    private static void calcReplaceClean(ArrayList<String> calcArray, String op, int i) {
        calcArray.set(i - 1, calcOp(calcArray.get(i - 1), op, calcArray.get(i + 1)));
        calcArray.remove(i);
        calcArray.remove(i);
    }

    /**
     * Locates and computes basic binary operations (such as multiplication/division or addition/subtraction)
     * left-to-right following their appearance order.
     *
     * @param calcArray The expression token array list.
     * @param op1       The primary operator to search for (e.g., "*").
     * @param op2       The secondary operator to search for (e.g., "/").
     */
    private static void calcBasicOps(ArrayList<String> calcArray, String op1, String op2) {
        for (int i = 0; i < calcArray.size(); i++) {
            String op = calcArray.get(i);
            if (op.equals(op1) || op.equals(op2)) {
                calcReplaceClean(calcArray, op, i);
                i--;
            }
        }
    }

    /**
     * Executes individual mathematical calculations between two numeric string operands for a given operator.
     * Monitors mathematical violations such as zero division or invalid power limits.
     *
     * @param a  The left numeric operand represented as a string.
     * @param op The operator symbol (+, -, *, /, ^).
     * @param b  The right numeric operand represented as a string.
     * @return The evaluation result as a string containing a {@code double} value.
     * @throws IllegalArgumentException If division by zero occurs, 0^0 is evaluated, or powers yield NaN/Infinity.
     */
    private static String calcOp(String a, String op, String b) {
        double numA = Double.parseDouble(a);
        double numB = Double.parseDouble(b);
        String result = "";
        switch (op) {
            case "^":
                if (numA == 0 && numB == 0) {
                    throw new IllegalArgumentException(POW_ZERO_ZERO);
                }
                if (numA < 0) {
                    result = "-" + Math.pow(-numA, numB);
                } else {
                    result = String.valueOf(Math.pow(numA, numB));
                }
                if (result.equals("NaN") || result.equals("Infinity")) {
                    throw new IllegalArgumentException(String.format(NAN_INFINITY_POW, a, op, b));
                }
                break;
            case "*":
                result = String.valueOf(numA * numB);
                break;
            case "/":
                if (numB == 0) {
                    throw new IllegalArgumentException(ERROR_DIVIDE_ZERO);
                }
                result = String.valueOf(numA / numB);
                break;
            case "+":
                result = String.valueOf(numA + numB);
                break;
            case "-":
                result = String.valueOf(numA - numB);
                break;
        }
        return result;
    }
}
