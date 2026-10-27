package com.shpp.p2p.cs.oyasniuk.assignment10;

import java.util.Map;

/**
 * An AST node that represents a unary operation applied to a single sub-expression.
 * Currently, supports unary minus ('-') and unary plus ('+').
 */
public class UnaryOpNode implements Node {
    private final char op;
    private final Node child;

    /**
     * Constructs a {@code UnaryOpNode} with an operator and its single child operand.
     *
     * @param op    the unary operator character (e.g., '-' or '+')
     * @param child the sub-expression node to which the operator is applied
     */
    public UnaryOpNode(char op, Node child) {
        this.op = op;
        this.child = child;
    }

    /**
     * Evaluates the child operand and applies the unary operation.
     *
     * @param vars a map containing the current values of the variables
     * @return the result after applying the unary operator to the evaluated child
     */
    @Override
    public double calculate(Map<String, Double> vars) {
        if (op == '-') {
            return -child.calculate(vars);
        }
        return child.calculate(vars);
    }

}
