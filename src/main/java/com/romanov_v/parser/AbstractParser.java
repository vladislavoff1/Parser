package com.romanov_v.parser;

/**
 * Created by vlad on 12/03/16.
 */
public abstract class AbstractParser {

    protected String statement;

    public AbstractParser(String statement) {
        this.statement = statement;
    }

    public abstract ParserTree parse();

}
