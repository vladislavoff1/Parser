package com.romanov_v.parser;

import com.romanov_v.parser.grammar.Grammar;
import com.romanov_v.parser.grammar.Rule;
import com.romanov_v.parser.grammar.Term;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by vlad on 14/03/16.
 */
public class SimpleBnfParserTest {

    @Test
    public void clearStatement() throws Exception {
        Grammar g = SimpleBnfParser.parse("");
        assertTrue("Rules is empty", g.getRules().isEmpty());
    }

    @Test
    public void oneZeroGrammar() throws Exception {
        String statement = "<s> ::= '0'";

        Grammar g = SimpleBnfParser.parse(statement);

        assertFalse("Rules is not empty", g.getRules().isEmpty());

        assertEquals(g.getRules().size(), 1);
        assertEquals(g.getRules().get(0).getName(), "s");

        assertEquals(g.getRules().get(0).getTerms().length, 1);

        assertTermIsText(g.getRules().get(0).getTerms()[0], "0");
    }

    @Test
    public void rightBrackets() throws Exception {
        String statement = "<s> ::= '(' <s> ')' | '()'";

        Grammar g = SimpleBnfParser.parse(statement);

        List<Rule> rules = g.getRules();

        assertEquals("Count of rules", 2, rules.size());


        Rule rule = rules.get(0);
        assertEquals("Rule name", "s", rule.getName());
        assertEquals("Count of terms", 3, rule.getTerms().length);

        assertTermIsText(rule.getTerms()[0], "(");
        assertTermIsRule(rule.getTerms()[1], "s");
        assertTermIsText(rule.getTerms()[2], ")");


        rule = rules.get(1);
        assertEquals("Rule name", "s", rule.getName());
        assertEquals("Count of terms", 2, rule.getTerms().length);

        assertTermIsText(rule.getTerms()[0], "(");
        assertTermIsText(rule.getTerms()[1], ")");
    }

    @Test
    public void unexpectedEnd() throws Exception {
        String statement = "<s> ::= '(' <s> ')' | '()";

        try {
            SimpleBnfParser.parse(statement);
            assertTrue("ParserException must be thrown", false);
        } catch (ParserException e) {
            // all right
        } catch (Exception e) {
            assertTrue("ParserException must be thrown", false);
        }
    }

    @Test
    public void unexpectedSymbol() throws Exception {
        String statement = "<s> ::= '(' <s> ')' > '()'";

        try {
            SimpleBnfParser.parse(statement);
            assertTrue("ParserException must be thrown", false);
        } catch (ParserException e) {
            // all right
        } catch (Exception e) {
            assertTrue("ParserException must be thrown", false);
        }
    }

    @Test
    public void multilineOneRule() throws Exception {
        String statement = "<s> ::= '(' <s> ')' \n | '()'";

        Grammar g = SimpleBnfParser.parse(statement);

        List<Rule> rules = g.getRules();

        assertEquals("Count of rules", 2, rules.size());


        Rule rule = rules.get(0);
        assertEquals("Rule name", "s", rule.getName());
        assertEquals("Count of terms", 3, rule.getTerms().length);

        assertTermIsText(rule.getTerms()[0], "(");
        assertTermIsRule(rule.getTerms()[1], "s");
        assertTermIsText(rule.getTerms()[2], ")");


        rule = rules.get(1);
        assertEquals("Rule name", "s", rule.getName());
        assertEquals("Count of terms", 2, rule.getTerms().length);

        assertTermIsText(rule.getTerms()[0], "(");
        assertTermIsText(rule.getTerms()[1], ")");
    }

    @Test
    public void multilineOneRule2() throws Exception {
        String statement = "<s> ::= '(' <s> ')' | \n '()'";

        Grammar g = SimpleBnfParser.parse(statement);

        List<Rule> rules = g.getRules();

        assertEquals("Count of rules", 2, rules.size());


        Rule rule = rules.get(0);
        assertEquals("Rule name", "s", rule.getName());
        assertEquals("Count of terms", 3, rule.getTerms().length);

        assertTermIsText(rule.getTerms()[0], "(");
        assertTermIsRule(rule.getTerms()[1], "s");
        assertTermIsText(rule.getTerms()[2], ")");


        rule = rules.get(1);
        assertEquals("Rule name", "s", rule.getName());
        assertEquals("Count of terms", 2, rule.getTerms().length);

        assertTermIsText(rule.getTerms()[0], "(");
        assertTermIsText(rule.getTerms()[1], ")");
    }

    @Test
    public void multiline2() throws Exception {
        String statement = "<s> ::= <a> | <b>" + "\n" +
                "<a> ::= '[' <s> ']' | '[]'" + "\n" +
                "<b> ::= '(' <s> ')' | '()'";

        Grammar g = SimpleBnfParser.parse(statement);
        List<Rule> rules = g.getRules();

        assertEquals("Count of rules", 6, rules.size());
    }


    private void assertTermIsRule(Term term, String value) throws Exception {
        assertTrue("It's rule", term.isRule());
        assertEquals("Value", term.getValue(), value);
    }

    private void assertTermIsText(Term term, String value) throws Exception {
        assertFalse("It's terminal", term.isRule());
        assertEquals("Value", term.getValue(), value);
    }
}