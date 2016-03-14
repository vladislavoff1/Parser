package com.romanov_v.parser;

import com.romanov_v.parser.grammar.Grammar;
import com.romanov_v.parser.grammar.Rule;
import com.romanov_v.parser.grammar.Term;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vlad on 14/03/16.
 */
public class EarleyParserTest {

    @Test
    public void emptyGrammar() throws Exception {
        Grammar grammar = new Grammar(new ArrayList<>());

        AbstractParser abstractParserEmpty = new EarleyParser(grammar, "");
        AbstractParser abstractParserNotEmpty = new EarleyParser(grammar, "123");

        assertNull(abstractParserEmpty.parse());
        assertNull(abstractParserNotEmpty.parse());
    }

    @Test
    public void emptyStatement() throws Exception {
        List<Rule> rules = new ArrayList<>();
        Rule start = new Rule("start");
        start.addTerm(Term.createTextTerm("0"));
        rules.add(start);

        Grammar grammar = new Grammar(rules);

        AbstractParser parser = new EarleyParser(grammar, "");
        assertNull(parser.parse());
    }

    @Test
    public void oneZero() throws Exception {
        List<Rule> rules = new ArrayList<>();
        Rule start = new Rule("start");
        start.addTerm(Term.createTextTerm("0"));
        rules.add(start);

        Grammar grammar = new Grammar(rules);

        ParserTree tree;

        tree = new EarleyParser(grammar, "0").parse();
        assertNotNull(tree);

        tree = new EarleyParser(grammar, "2").parse();
        assertNull(tree);

        tree = new EarleyParser(grammar, "00").parse();
        assertNull(tree);

    }

}