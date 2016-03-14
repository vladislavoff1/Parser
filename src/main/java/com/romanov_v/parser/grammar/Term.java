package com.romanov_v.parser.grammar;

/**
 * Created by vlad on 11/03/16.
 */
public class Term {

    private String value;
    private boolean isRule = false;

    private Term(String str, boolean isRule) {
        this.value = str;
        this.isRule = isRule;
    }

    public static Term createTextTerm(String text) {
        return new Term(text, false);
    }

    public static Term createTextTerm(char text) {
        return new Term(text + "", false);
    }

    public static Term createRuleTerm(String rule) {
        return new Term(rule, true);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        char begin;
        char end;

        if (isRule) {
            begin = '<';
            end = '>';
        } else {
            if (value.indexOf('"') >= 0) {
                begin = end = '\'';
            } else {
                begin = end = '"';
            }
        }

        stringBuilder.append(begin);
        stringBuilder.append(value);
        stringBuilder.append(end);

        return stringBuilder.toString();
    }

    public String getValue() {
        return value;
    }

    public boolean isRule() {
        return isRule;
    }
}
