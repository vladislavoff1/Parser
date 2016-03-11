package grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vlad on 11/03/16.
 */
public class Grammar {

    private List<Rule> rules = new ArrayList<>();

    public Grammar(Collection<Rule> rules) {
        this.rules.addAll(rules);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule rule : rules) {
            stringBuilder.append(rule);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
