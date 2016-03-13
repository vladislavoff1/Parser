package main;

import bnf.SimpleBnfParser;
import grammar.Grammar;
import parser.AbstractParser;
import parser.EarleyParser;
import parser.ParserTree;

/**
 * Created by vlad on 11/03/16.
 */
public class Main {

    public static void main(String... args) {
        String inp = "<P> ::= <S>\n" +
                "<S> ::= <S> \"+\" <M> | <M>\n" +
                "<M> ::= <M> \"*\" <T> | <T>\n" +
                "<T> ::= \"1\" | \"2\" | \"3\" | \"4\"";

        System.out.println("Input: ");
        System.out.println(inp);

        System.out.println();
        System.out.println("Result: ");

        Grammar grammar = SimpleBnfParser.parse(inp);
        System.out.println(grammar);

        AbstractParser parser = new EarleyParser("2+3*4", grammar);
        ParserTree tree = parser.parse();

        System.out.println(tree);
    }

}
