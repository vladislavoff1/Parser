package com.romanov_v.parser.grammar;

/**
 * Created by vlad on 14/03/16.
 */
public class Grammars {

    public static void addBasicRules(Grammar grammar) {
        Rule eol = new Rule("EOL");
        char[] eolTerms = System.getProperty("line.separator").toCharArray();
        for (char c : eolTerms) {
            eol.addTerm(Term.createTextTerm(c));
        }
        grammar.addRule(eol);
    }

}
