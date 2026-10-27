package com.shpp.p2p.cs.oyasniuk.assignment10;

/**
 * A parser that converts a mathematical expression string into an Abstract Syntax Tree (AST).
 * Utilizes the recursive descent parsing algorithm to process operator priorities and nested structures.
 * RULES:
 * Expression E → T | E+T | E-T
 * Term T → U | T*U | T/U
 * Unary U → −U | +U ∣ P
 * Power P → F^P ∣ F
 * Factor F → (E) ∣ Const ∣ var | func(E)
 */
public class ExpressionTree {
    private static final String UNEXPECTED_SYMBOL = "Неочікуваний символ: ";
    private static final String INVALID_NUMBER_FORMAT = "Неправильний формат числа: ";
    private static final String MISSING_CLOSING_PARENTHESIS = "Пропущена закриваюча дужка";
    private static final String MISSING_PARENTHESIS_AFTER_FUNCTION = "Пропущена дужка після функції ";

    private final String input;
    private int pos = -1;
    private int charCode;

    /**
     * Constructs a new parser for the given input mathematical expression.
     *
     * @param input the raw mathematical expression string to parse
     */
    public ExpressionTree(String input) {
        this.input = input;
    }

    /**
     * Advances the pointer to the next character in the input string and updates its code.
     * <p>
     * This method automatically skips all whitespace characters by recursively calling
     * itself until it encounters a non-space character or reaches the end of the input.
     * If the end of the expression is reached, {@code charCode} is set to {@code -1}.
     * </p>
     */
    private void nextChar() {
        charCode = (++pos < input.length()) ? input.charAt(pos) : -1;
        while (charCode == ' ') {
            nextChar();
        }
    }

    /**
     * Begins the parsing process of the input string and builds the AST.
     * Verifies that the entire input string was fully processed and contains no trailing garbage.
     *
     * @return the root {@link Node} of the constructed Abstract Syntax Tree
     * @throws IllegalArgumentException if the expression contains syntax errors or trailing unexpected symbols
     */
    public Node parse() {
        nextChar();
        Node root = parseExpression();

        if (charCode != -1) {
            throw new IllegalArgumentException(UNEXPECTED_SYMBOL + (char) charCode);
        }
        return root;
    }

    /**
     * Parses expressions dealing with lowest-priority additive operators ('+' and '-').
     * Implements left-associativity using a loop to correctly chain continuous additions and subtractions.
     *
     * @return the constructed node for an additive expression or a standalone term
     */
    private Node parseExpression() {
        Node left = parseTerm();
        while (true) {
            if (charCode == '+') {
                nextChar();
                Node right = parseTerm();
                left = new BinaryOpNode('+', left, right);
            } else if (charCode == '-') {
                nextChar();
                Node right = parseTerm();
                left = new BinaryOpNode('-', left, right);
            } else {
                return left;
            }
        }
    }

    /**
     * Parses terms dealing with mid-priority multiplicative operators ('*' and '/').
     * Implements left-associativity using a loop to correctly chain continuous multiplications and divisions.
     *
     * @return the constructed node for a multiplicative expression or a standalone unary block
     */
    private Node parseTerm() {
        Node left = parseUnary();
        while (true) {
            if (charCode == '*') {
                nextChar();
                Node right = parseUnary();
                left = new BinaryOpNode('*', left, right);
            } else if (charCode == '/') {
                nextChar();
                Node right = parseUnary();
                left = new BinaryOpNode('/', left, right);
            } else {
                return left;
            }
        }
    }


    /**
     * Parses high-priority unary operators ('-' and '+').
     * Implements right-associativity via pure recursion to support stacked unary operators like "--5".
     *
     * @return the constructed node for a unary operation or a standalone exponentiation power block
     */
    private Node parseUnary() {
        if (charCode == '-') {
            nextChar();
            Node argument = parseUnary();
            return new UnaryOpNode('-', argument);
        }
        if (charCode == '+') {
            nextChar();
            return parseUnary();
        }
        return parsePower();
    }

    /**
     * Parses the exponentiation operator ('^').
     * Implements right-associativity via pure recursion to evaluate stacked powers from right to left (e.g., 2^3^2 as 2^(3^2)).
     *
     * @return the constructed node for an exponentiation expression or a standalone factor
     */
    private Node parsePower() {
        Node left = parseFactor();
        if (charCode == '^') {
            nextChar();
            Node right = parsePower();
            left = new BinaryOpNode('^', left, right);
        }
        return left;
    }

    /**
     * Parses highest-priority factors including numeric constants, variables, mathematical functions,
     * and sub-expressions wrapped in parentheses.
     *
     * @return the constructed node for a single factor unit (Constant, Variable, Function, or parentheses subtree)
     * @throws IllegalArgumentException if an unexpected symbol, missing closing parenthesis,
     *                                  or malformed function call is encountered
     */
    private Node parseFactor() {
        if (charCode == '(') {
            nextChar();
            Node expression = parseExpression();
            if (charCode != ')') {
                throw new IllegalArgumentException(MISSING_CLOSING_PARENTHESIS);
            }
            nextChar();
            return expression;
        }

        if (charCode >= '0' && charCode <= '9') {
            StringBuilder numberBuilder = new StringBuilder();
            while ((charCode >= '0' && charCode <= '9') || charCode == '.') {
                numberBuilder.append((char) charCode);
                nextChar();
            }
            try {
                return new ConstantNode(Double.parseDouble(numberBuilder.toString()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(INVALID_NUMBER_FORMAT + numberBuilder);
            }
        }

        if ((charCode >= 'a' && charCode <= 'z') || (charCode >= 'A' && charCode <= 'Z')) {
            StringBuilder nameBuilder = new StringBuilder();
            while ((charCode >= 'a' && charCode <= 'z')
                    || (charCode >= 'A' && charCode <= 'Z')
                    || (charCode >= '0' && charCode <= '9')) {
                nameBuilder.append((char) charCode);
                nextChar();
            }
            String name = nameBuilder.toString();

            if (charCode == '(') {
                nextChar();
                Node argument = parseExpression();
                if (charCode != ')') {
                    throw new IllegalArgumentException(MISSING_PARENTHESIS_AFTER_FUNCTION + name);
                }
                nextChar();
                return new FunctionNode(name, argument);
            }

            return new VariableNode(name);
        }

        throw new IllegalArgumentException(UNEXPECTED_SYMBOL + (char) charCode);
    }

}
