package main;

import bnf.SimpleBnfParser;
import files.*;
import grammar.Grammar;
import grammar.Rule;
import grammar.Term;
import parser.AbstractParser;
import parser.EarleyParser;
import parser.ParserTree;

import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vlad on 11/03/16.
 */
public class Main {

    public static void main(String... args) throws IOException {

        String inp = FileSystem.readFile("resources/semantics.bnf");

        System.out.println("Semantic: ");
        System.out.println(inp);

        System.out.println();
        System.out.println("Parsed semantic: ");

        Grammar grammar = SimpleBnfParser.parse(inp);
        addBasicRules(grammar);
        System.out.println(grammar);


        inp = FileSystem.readFile("resources/input.txt");
        System.out.println();
        System.out.println("Input: \n" + inp);

        AbstractParser parser = new EarleyParser(inp, grammar);
        ParserTree tree = parser.parse();

        System.out.println();
        System.out.println("Result: ");
        System.out.println(tree);
    }

    private static void addBasicRules(Grammar grammar) {
        Rule eol = new Rule("EOL");
        char[] eolTerms = System.getProperty("line.separator").toCharArray();
        for (char c : eolTerms) {
            eol.addTerm(Term.createTextTerm(c));
        }
        grammar.addRule(eol);
    }

}
