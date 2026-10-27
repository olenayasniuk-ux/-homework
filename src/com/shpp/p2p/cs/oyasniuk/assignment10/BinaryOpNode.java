package com.shpp.p2p.cs.oyasniuk.assignment10;

import java.util.Map;

/**
 * An AST node that represents a binary operation between two sub-expressions.
 * Supported operators include addition (+), subtraction (-), multiplication (*),
 * division (/), and exponentiation (^).
 */
public class BinaryOpNode implements Node {
    private static final String DIVISION_BY_ZERO = "Ділення на нуль!";
    private static final String UNKNOWN_OPERATOR = "Невідомий оператор: ";
    private static final String INVALID_POW = "%s^%s невизначено.";
    private static final String POW_ZERO_ZERO = "0^0 невизначено.";

    private final char op;
    private final Node left;
    private final Node right;

    /**
     * Constructs a {@code BinaryOpNode} with the specified operator and operands.
     *
     * @param op    the binary operator character ('+', '-', '*', '/', '^')
     * @param left  the left-hand operand node
     * @param right the right-hand operand node
     */
    public BinaryOpNode(char op, Node left, Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates both left and right child nodes and applies the binary operator.
     *
     * @param vars a map containing the current values of the variables
     * @return the result of the binary operation
     * @throws IllegalArgumentException if the operator is '/' and the right operand evaluates to zero
     *                                  or an unrecognized operator character is encountered
     */
    @Override
    public double calculate(Map<String, Double> vars) {
        double l = left.calculate(vars);
        double r = right.calculate(vars);
        return switch (op) {
            case '+' -> l + r;
            case '-' -> l - r;
            case '*' -> l * r;
            case '/' -> {
                if (r == 0) {
                    throw new IllegalArgumentException(DIVISION_BY_ZERO);
                }
                yield l / r;
            }
            case '^' -> {
                if (l == 0 && r == 0) {
                    throw new IllegalArgumentException(POW_ZERO_ZERO);
                }
                double result = Math.pow(l, r);
                if (Double.isInfinite(result) || Double.isNaN(result)) {
                    throw new IllegalArgumentException(String.format(INVALID_POW, l, r));
                }
                yield result;
            }
            default -> throw new IllegalArgumentException(UNKNOWN_OPERATOR + op);
        };
    }

}
