package com.romanov_v.parser;

/**
 * Created by vlad on 14/03/16.
 */
public class ParserException extends Exception {

    private String statement;
    private int position;

    public ParserException(String message, String statement, int position) {
        super(message);

        this.statement = statement;
        this.position = position;
    }

    @Override
    public String getMessage() {
        StringBuilder result = new StringBuilder();

        result
                .append(super.getMessage())
                .append("\n")
                .append(statement)
                .append("\n");

        for (int i = 0; i < position - 1; i++) {
            result.append(" ");
        }

        result.append("^");
        return result.toString();
    }

}
