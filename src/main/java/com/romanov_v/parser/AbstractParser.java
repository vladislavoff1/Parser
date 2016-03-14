package com.romanov_v.parser;

import com.romanov_v.parser.grammar.Grammar;

/**
 * Created by vlad on 12/03/16.
 */
public abstract class AbstractParser {

    protected String statement;
    protected Grammar grammar;

    public AbstractParser(String statement, Grammar grammar) {
        this.statement = statement;
        this.grammar = grammar;
    }

    public abstract ParserTree parse();

}
