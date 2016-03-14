package com.romanov_v.parser.grammar;

import java.util.*;
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
    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    public List<Rule> getRulesByName(String name) {
        return rules.stream()
                .filter((rule) -> rule.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<String> undeclaredRules() {
        Set<String> declaredRules = new HashSet<>();
        Set<String> usedRules = new HashSet<>();

        for (Rule r : rules) {
            declaredRules.add(r.getName());
            for (Term t : r.getTerms()) {
                if (t.isRule()) {
                    usedRules.add(t.getValue());
                }
            }
        }

        List<String> result = new ArrayList<>();

        for (String used : usedRules) {
            if (!declaredRules.contains(used)) {
                result.add(used);
            }
        }

        return result;
    }
}
