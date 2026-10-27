package com.shpp.p2p.cs.oyasniuk.assignment10;

import java.util.Map;

/**
 * An AST node that represents a mathematical function call with a single argument.
 * Supported functions include: sin, cos, tan, atan, log10, log2, and sqrt.
 */
public class FunctionNode implements Node {
    private static final String UNKNOWN_FUNCTION = "Невідома функція: ";
    private static final String LOG_NON_POSITIVE_ARGUMENT = "Не можна порахувати логарифм недодатного числа";
    private static final String SQRT_NEGATIVE_ARGUMENT = "Не можна взяти корінь з від'ємного числа!";

    private final String func;
    private final Node argument;

    /**
     * Constructs a {@code FunctionNode} with the function name and its argument expression.
     * The function name is automatically converted to lowercase.
     *
     * @param func     the name of the mathematical function (e.g., "sin", "sqrt")
     * @param argument the node representing the function's inner argument expression
     */
    public FunctionNode(String func, Node argument) {
        this.func = func.toLowerCase();
        this.argument = argument;
    }

    /**
     * Evaluates the inner argument node and applies the corresponding mathematical function.
     *
     * @param vars a map containing the current values of the variables
     * @return the numerical result of the function execution
     * @throws IllegalArgumentException if the function name is not recognized or supported
     */
    @Override
    public double calculate(Map<String, Double> vars) {
        double arg = argument.calculate(vars);
        return switch (func) {
            case "sin" -> Math.sin(arg);
            case "cos" -> Math.cos(arg);
            case "tan" -> Math.tan(arg);
            case "atan" -> Math.atan(arg);
            case "log10" -> {
                if (arg <= 0) throw new IllegalArgumentException(LOG_NON_POSITIVE_ARGUMENT);
                yield Math.log10(arg);
            }
            case "log2" -> {
                if (arg <= 0) throw new IllegalArgumentException(LOG_NON_POSITIVE_ARGUMENT);
                yield Math.log(arg) / Math.log(2);
            }
            case "sqrt" -> {
                if (arg < 0) throw new IllegalArgumentException(SQRT_NEGATIVE_ARGUMENT);
                yield Math.sqrt(arg);
            }
            default -> throw new IllegalArgumentException(UNKNOWN_FUNCTION + func);
        };
    }

}
