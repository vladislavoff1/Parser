package bnf;

import grammar.Grammar;
import grammar.Rule;
import grammar.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 11/03/16.
 */
public class SimpleBnfParser {

    private String statement;
    private int position = 0;

    private boolean debug = false;

    private SimpleBnfParser(String statement) {
        this.statement = statement;
    }


    // <rule> | <rule> <syntax>

    public static Grammar parse(String statement) {
        String[] statements = statement.split("\n");

        List<Rule> rules = new ArrayList<>();

        for (String stmnt : statements) {
            SimpleBnfParser simpleBnfParser = new SimpleBnfParser(stmnt);
            List<Rule> rule = simpleBnfParser.rule();
            rules.addAll(rule);
        }

        return new Grammar(rules);
    }


    // <opt-whitespace> "<" <rule-name> ">" <opt-whitespace> "::=" <opt-whitespace> <expression> <line-end>

    private List<Rule> rule() {
        if (debug)
            System.out.println("rule: " + position + " '" + statement.charAt(position) + "'.");
        // Rule name
        skipWhitespaces();

        List<Rule> rules = new ArrayList<>();

        String ruleName = getTextBetween('<', '>');

        skipWhitespaces();

        match("::=");
        skipWhitespaces();

        rules.addAll(list(ruleName));

        return rules;
    }

    // <list> | <list> <opt-whitespace> "|" <opt-whitespace> <expression>
    private List<Rule> list(String name) {
        if (debug)
            System.out.println("list: " + position + " '" + statement.charAt(position) + "'.");
        List<Rule> expressions = new ArrayList<>();
        expressions.add(new Rule(name, expression()));

        skipWhitespaces();
        while (hasNext() && statement.charAt(position) == '|') {
            next();
            skipWhitespaces();
            expressions.add(new Rule(name, expression()));
            skipWhitespaces();
        }
        return expressions;
    }

    // <term> | <term> <opt-whitespace> <expression>
    private List<Term> expression() {
        if (debug)
            System.out.println("expression: " + position + " '" + statement.charAt(position) + "'.");
        List<Term> expression = new ArrayList<>();
        expression.addAll(term());

        skipWhitespaces();
        while (hasNext() && statement.charAt(position) != '|') {
            expression.addAll(term());
            skipWhitespaces();
        }

        return expression;
    }

    // <literal> | "<" <rule-name> ">"
    private List<Term> term() {
        if (debug)
            System.out.println("term: " + position + " '" + statement.charAt(position) + "'.");
        char current = statement.charAt(position);

        List<Term> terms = new ArrayList<>();

        switch (current) {
            case '<':
                terms.add(Term.createRuleTerm(getTextBetween('<', '>')));
            default:
                String text = literal();
                for (int i = 0; i < text.length(); i++) {
                    terms.add(Term.createTextTerm(text.charAt(i)));
                }
        }

        return terms;
    }

    // '"' <text> '"' | "'" <text> "'"
    private String literal() {
        if (debug)
            System.out.println("literal: " + position + " '" + statement.charAt(position) + "'.");
        char start = statement.charAt(position);
        return getTextBetween(start, start);
    }


    private boolean hasNext() {
        return statement.length() > position;
    }

    private char next() {
        return statement.charAt(position++);
    }

    private String getTextBetween(char from, char to) {
        match(from);

        StringBuilder stringBuilder = new StringBuilder();

        for (char c = next(); c != to; c = next()) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    private boolean match(String str) {
        boolean res = true;

        for (int i = 0; i < str.length(); i++) {
            res &= match(str.charAt(i));
        }

        // TODO: throw Exception
        return res;
    }

    private boolean match(char c) {
        // TODO: throw Exception
        return next() == c;
    }

    private void skipWhitespaces() {
        if (position >= statement.length()) {
            return;
        }

        Character c = statement.charAt(position);

        while (Character.isWhitespace(c)) {
            position++;

            if (position >= statement.length()) {
                return;
            }

            c = statement.charAt(position);
        }
    }

}
