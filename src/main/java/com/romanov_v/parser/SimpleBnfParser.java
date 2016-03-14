package com.romanov_v.parser;

import com.romanov_v.parser.grammar.Grammar;
import com.romanov_v.parser.grammar.Rule;
import com.romanov_v.parser.grammar.Term;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vlad on 11/03/16.
 */
public class SimpleBnfParser {

    private String statement;
    private int position = 0;

    private SimpleBnfParser(String statement) {
        this.statement = statement;
    }

    // <rule> | <rule> <syntax>
    public static Grammar parse(String statement) throws ParserException {
        String[] statements = statement.split("\n");
        List<String> started = Arrays.asList(statements).stream()
                .map(String::trim)
                .filter((s) -> !s.isEmpty())
                .collect(Collectors.toList());

        List<String> list = new ArrayList<>();
        String prev = "";
        for (String str : started) {
            if (!prev.isEmpty() && prev.charAt(prev.length() - 1) == '|') {
                prev += str;
                continue;
            }
            if (str.charAt(0) == '|' || prev.isEmpty()) {
                prev += str;
                continue;
            }
            list.add(prev);
            prev = str;
        }

        if (!prev.isEmpty()) {
            list.add(prev);
        }


        List<Rule> rules = new ArrayList<>();

        for (String stmnt : list) {
            stmnt = stmnt.trim();
            if (stmnt.isEmpty()) {
                continue;
            }

            SimpleBnfParser simpleBnfParser = new SimpleBnfParser(stmnt);
            List<Rule> rule = simpleBnfParser.rule();
            rules.addAll(rule);
        }

        return new Grammar(rules);
    }

    // <opt-whitespace> "<" <rule-name> ">" <opt-whitespace> "::=" <opt-whitespace> <expression> <line-end>
    private List<Rule> rule() throws ParserException {
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
    private List<Rule> list(String name) throws ParserException {
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
    private List<Term> expression() throws ParserException {
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
    private List<Term> term() throws ParserException {
        char current = statement.charAt(position);

        List<Term> terms = new ArrayList<>();

        switch (current) {
            case '<':
                String ruleName = getTextBetween('<', '>');
                terms.add(Term.createRuleTerm(ruleName));
                break;

            case '"':
            case '\'':
                String text = literal();
                for (int i = 0; i < text.length(); i++) {
                    terms.add(Term.createTextTerm(text.charAt(i)));
                }
                break;

            default:
                throw createParserException("Unexpected symbol '" + current + "'");
        }

        return terms;
    }

    // '"' <text> '"' | "'" <text> "'"
    private String literal() throws ParserException {
        char start = statement.charAt(position);
        return getTextBetween(start, start);
    }


    private boolean hasNext() {
        return statement.length() > position;
    }

    private char next() throws ParserException {
        try {
            return statement.charAt(position++);
        } catch (StringIndexOutOfBoundsException e) {
            position--;
            throw createParserException("Unexpected end");
        }
    }

    private String getTextBetween(char from, char to) throws ParserException {
        match(from);

        StringBuilder stringBuilder = new StringBuilder();

        for (char c = next(); c != to; c = next()) {
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    private void match(String str) throws ParserException {
        int position = this.position;

        boolean res = true;
        for (int i = 0; i < str.length(); i++) {
            res &= next() == str.charAt(i);
        }

        if (!res) {
            throw new ParserException("'" + str + "' expected", statement, position);
        }
    }

    private void match(char c) throws ParserException {
        if (next() != c) {
            throw createParserException("'" + c + "' expected");
        }
    }

    private void skipWhitespaces() throws ParserException {
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

    private ParserException createParserException(String message) {
        return new ParserException(message, statement, position);
    }

}
