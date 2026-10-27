package com.shpp.p2p.cs.oyasniuk.assignment10;

import java.util.Map;

/**
 * An AST node that represents a mathematical variable (e.g., x, yT35, T5y7).
 * Looks up its value dynamically from the provided variable context map during evaluation.
 */
public class VariableNode implements Node {

    private static final String VARIABLE_HAS_NO_VALUE = "Змінна %s не передана через консоль";
    private final String name;

    /**
     * Constructs a new {@code VariableNode} with the specified variable name.
     *
     * @param name the name of the variable
     */
    public VariableNode(String name) {
        this.name = name;
    }

    /**
     * Retrieves the variable's value from the provided map.
     *
     * @param vars a map containing variable names and their current values
     * @return the double value associated with this variable name
     * @throws IllegalArgumentException if the variable name is not present in the map
     */
    @Override
    public double calculate(Map<String, Double> vars) {
        if (!vars.containsKey(name)) {
            throw new IllegalArgumentException(String.format(VARIABLE_HAS_NO_VALUE, name));
        }
        return vars.get(name);
    }

}
