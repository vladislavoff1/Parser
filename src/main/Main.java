package main;

import bnf.SimpleBnfParser;
import grammar.Grammar;

/**
 * Created by vlad on 11/03/16.
 */
public class Main {

    public static void main(String... args) {
        String inp = "<syntax>         ::= <rule> | <rule> <syntax>\n" +
                "<rule>           ::= <opt-whitespace> \"<\" <rule-name> \">\" <opt-whitespace> \"::=\" <opt-whitespace> <expression> <line-end>\n" +
                "<opt-whitespace> ::= \" \" <opt-whitespace> | \"\"\n" +
                "<expression>     ::= <list> | <list> <opt-whitespace> \"|\" <opt-whitespace> <expression>\n" +
                "<line-end>       ::= <opt-whitespace> <EOL> | <line-end> <line-end>\n" +
                "<list>           ::= <term> | <term> <opt-whitespace> <list>\n" +
                "<term>           ::= <literal> | \"<\" <rule-name> \">\"\n" +
                "<literal>        ::= '\"' <text> '\"' | \"'\" <text> \"'\"";

        System.out.println("Input: ");
        System.out.println(inp);

        System.out.println();
        System.out.println("Result: ");

        Grammar grammar = SimpleBnfParser.parse(inp);
        System.out.println(grammar);
    }

}
