package grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRulesByName(String name) {
        return rules.stream()
                .filter((rule) -> rule.getName().equals(name))
                .collect(Collectors.toList());
    }
}
