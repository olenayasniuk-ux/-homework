package com.shpp.p2p.cs.oyasniuk.assignment10;

import java.util.Map;

/**
 * Represents a generic node in the Abstract Syntax Tree (AST) of a mathematical expression.
 * Every component of the expression (constants, variables, operators, functions) implements
 * this interface to evaluate its mathematical value.
 */
public interface Node {

    /**
     * Evaluates the current node's value based on the provided variable values.
     *
     * @param variables a map containing variable names as keys and their corresponding
     *                  numerical values as values
     * @return the resulting numerical value of the node evaluation
     * @throws IllegalArgumentException if a required variable is missing in the map
     *                                  or  a mathematical error occurs (e.g., division by zero)
     */
    double calculate(Map<String, Double> variables);
}
