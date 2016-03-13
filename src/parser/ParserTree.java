package parser;

import java.util.List;

/**
 * Created by vlad on 12/03/16.
 */
public class ParserTree {

    private List<ParserTree> children;
    private String value;

    public ParserTree(String value) {
        this.value = value;
    }

    public ParserTree(String value, List<ParserTree> children) {
        this.value = value;
        this.children = children;
    }

    public List<ParserTree> getChildren() {
        return children;
    }

    public void setChilds(List<ParserTree> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
