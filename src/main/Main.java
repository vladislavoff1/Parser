package main;

import bnf.SimpleBnfParser;
import grammar.Grammar;
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

    public static void main(String... args) {

        StringBuilder stringBuilder = new StringBuilder();

        Path path = Paths.get("resources/semantics.bnf");

        try (FileReader fileReader = new FileReader(path.toFile())) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.lines().forEachOrdered((s) -> stringBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inp = stringBuilder.toString();

        System.out.println("Semantic: ");
        System.out.println(inp);

        System.out.println();
        System.out.println("Parsed semantic: ");

        Grammar grammar = SimpleBnfParser.parse(inp);
        System.out.println(grammar);

        StringBuilder stringBuilder2 = new StringBuilder();
        Path path2 = Paths.get("resources/input.txt");

        try (FileReader fileReader = new FileReader(path2.toFile())) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.lines().forEachOrdered((s) -> stringBuilder2.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
        inp = stringBuilder2.toString();
        System.out.println();
        System.out.println("Input: \n" + inp);

        AbstractParser parser = new EarleyParser(inp, grammar);
        ParserTree tree = parser.parse();

        System.out.println();
        System.out.println("Result: ");
        System.out.println(tree);
    }

}
