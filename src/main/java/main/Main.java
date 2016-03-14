package main;

import com.romanov_v.parser.AbstractParser;
import com.romanov_v.parser.EarleyParser;
import com.romanov_v.parser.ParserTree;
import com.romanov_v.parser.SimpleBnfParser;
import com.romanov_v.parser.grammar.Grammar;
import com.romanov_v.parser.grammar.Rule;
import com.romanov_v.parser.grammar.Term;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vlad on 11/03/16.
 */
public class Main {

    public static void main(String... args) throws IOException {

        String inp = readFile("semantics.bnf");

        System.out.println("Semantic: ");
        System.out.println(inp);

        System.out.println();
        System.out.println("Parsed semantic: ");

        Grammar grammar = SimpleBnfParser.parse(inp);
        addBasicRules(grammar);
        System.out.println(grammar);


        inp = readFile("input.txt");
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

    public static String readFile(String name) throws FileNotFoundException {
        Resources resources = Resources.getInstance();
        return resources.readFile(name);
    }

}
