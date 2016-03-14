package com.romanov_v.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad on 12/03/16.
 */
public class ParserTree {

    private List<ParserTree> children = new ArrayList<>();
    private String value;
    private boolean terminal;

    public ParserTree(String value) {
        this.value = value;
        this.terminal = false;
    }

    public ParserTree(String value, boolean isTerminal) {
        this.value = value;
        this.terminal = isTerminal;
    }

    public ParserTree(ParserTree tree) {
        value = tree.value;

        for (ParserTree c : tree.children) {
            children.add(c);
        }
    }

    public List<ParserTree> getChildren() {
        return children;
    }

    public void setChildren(List<ParserTree> children) {
        this.children = children;
    }

    public void addChild(ParserTree child) {
        children.add(child);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public String toString() {
        return toString("");
    }

    private String toString(String prefix) {
        StringBuilder result = new StringBuilder();
        if (terminal) {
            result.append("\'");
        }
        result.append(value);
        if (terminal) {
            result.append("\'");
        }
        result.append("\n");

        for (int i = 0; i < children.size(); i++) {
            if (i != children.size() - 1) { // if not last
                result
                        .append(prefix)
                        .append("├── ")
                        .append(children.get(i).toString(prefix + "│   "));
            } else {
                result
                        .append(prefix)
                        .append("└── ")
                        .append(children.get(i).toString(prefix + "    "));
            }
        }

        return result.toString();
    }
}
