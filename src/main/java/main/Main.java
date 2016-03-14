package main;

import com.romanov_v.parser.*;
import com.romanov_v.parser.grammar.Grammar;
import com.romanov_v.parser.grammar.Rule;
import com.romanov_v.parser.grammar.Term;

import java.io.*;
import java.util.List;

/**
 * Created by vlad on 11/03/16.
 */
public class Main {

    public static void main(String... args) {
        String inp = null;
        try {
            inp = readFile("semantics.bnf");
        } catch (FileNotFoundException e) {
            System.out.println("File 'semantic.bnf' not found.");
            return;
        }

        Grammar grammar;
        try {
            grammar = SimpleBnfParser.parse(inp);
            addBasicRules(grammar);
        } catch (ParserException e) {
            System.out.println("Parser error (semantic.bnf): " + e.getMessage());
            return;
        }

        List<String> undeclared = grammar.undeclaredRules();
        if (undeclared.size() > 0) {
            for (String ruleName : undeclared) {
                System.out.println("Semantic error (semantic.bnf): undeclared rule '" + ruleName + "'.");
            }
            return;
        }

        try {
            inp = readFile("input.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File 'input.txt' not found.");
            return;
        }

        AbstractParser parser = new EarleyParser(grammar, inp);

        ParserTree tree;
        try {
            tree = parser.parse().simplify();
        } catch (ParserException e) {
            System.out.println("Parser error (input.txt): " + e.getMessage());
            return;
        }

        System.out.println(tree);
    }

    // TODO: put in class Grammars
    private static void addBasicRules(Grammar grammar) {
        Rule eol = new Rule("EOL");
        char[] eolTerms = System.getProperty("line.separator").toCharArray();
        for (char c : eolTerms) {
            eol.addTerm(Term.createTextTerm(c));
        }
        grammar.addRule(eol);
    }

    // TODO: inline
    public static String readFile(String name) throws FileNotFoundException {
        Resources resources = Resources.getInstance();
        return resources.readFile(name);
    }

}
