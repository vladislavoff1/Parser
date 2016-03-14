package main;

import com.romanov_v.parser.*;
import com.romanov_v.parser.grammar.Grammar;
import com.romanov_v.parser.grammar.Grammars;

import java.io.*;
import java.util.List;

/**
 * Created by vlad on 11/03/16.
 */
public class Main {

    public static void main(String... args) {

        boolean needSimplify = false;
        if (args.length > 0) {
            if (args[0].equals("-s")) {
                needSimplify = true;
            }
        }

        Resources resources = Resources.getInstance();
        String inp;

        // Read semantic
        try {
            inp = resources.readFile("semantics.bnf");
        } catch (FileNotFoundException e) {
            System.out.println("File 'semantic.bnf' not found.");
            return;
        }

        // Parse semantic
        Grammar grammar;
        try {
            grammar = SimpleBnfParser.parse(inp);
            Grammars.addBasicRules(grammar);
        } catch (ParserException e) {
            System.out.println("Parser error (semantic.bnf): " + e.getMessage());
            return;
        }

        // Search undeclared rules in semantic
        List<String> undeclared = grammar.undeclaredRules();
        if (undeclared.size() > 0) {
            for (String ruleName : undeclared) {
                System.out.println("Semantic error (semantic.bnf): undeclared rule '" + ruleName + "'.");
            }
            return;
        }

        // Read input
        try {
            inp = resources.readFile("input.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File 'input.txt' not found.");
            return;
        }

        // Parse input
        AbstractParser parser = new EarleyParser(grammar, inp);

        ParserTree tree;
        try {
            tree = parser.parse();

            if (needSimplify) {
                tree = tree.simplify();
            }
        } catch (ParserException e) {
            System.out.println("Parser error (input.txt): " + e.getMessage());
            return;
        }
        System.out.println(tree);
    }

}
