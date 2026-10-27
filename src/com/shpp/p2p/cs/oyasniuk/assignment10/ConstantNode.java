package com.shpp.p2p.cs.oyasniuk.assignment10;

import java.util.Map;

/**
 * An AST node that represents a fixed numerical constant (e.g., 5, 3.14).
 * Always returns its predefined value regardless of the variable context.
 */
public class ConstantNode implements Node {
    private final double value;

    /**
     * Constructs a new {@code ConstantNode} with the specified numerical value.
     *
     * @param value the concrete numerical value of this constant
     */
    public ConstantNode(double value) {
        this.value = value;
    }

    /**
     * Returns the constant value.
     *
     * @param vars the variable context map (ignored by this node)
     * @return the value of this constant
     */
    @Override
    public double calculate(Map<String, Double> vars) {
        return value;
    }

}
