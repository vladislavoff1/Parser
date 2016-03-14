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

        AbstractParser abstractParserEmpty = new EarleyParser("", grammar);
        AbstractParser abstractParserNotEmpty = new EarleyParser("123", grammar);

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

        AbstractParser parser = new EarleyParser("", grammar);
        assertNull(parser.parse());
    }

    @Test
    public void oneZeroGrammar() throws Exception {
        List<Rule> rules = new ArrayList<>();
        Rule start = new Rule("start");
        start.addTerm(Term.createTextTerm("0"));
        rules.add(start);

        Grammar grammar = new Grammar(rules);

        AbstractParser parser;
        ParserTree tree;

        parser = new EarleyParser("0", grammar);
        tree = parser.parse();
        assertNotNull(tree);

        parser = new EarleyParser("2", grammar);
        tree = parser.parse();
        assertNull(tree);

        parser = new EarleyParser("00", grammar);
        assertNull(parser.parse());

    }

}