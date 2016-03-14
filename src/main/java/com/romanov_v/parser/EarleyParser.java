package com.romanov_v.parser;

import com.romanov_v.parser.grammar.Grammar;
import com.romanov_v.parser.grammar.Rule;
import com.romanov_v.parser.grammar.Term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vlad on 12/03/16.
 */
public class EarleyParser extends AbstractParser {

    private List<List<State>> states = new ArrayList<>();
    private Grammar grammar;

    public EarleyParser(Grammar grammar, String statement) {
        super(statement);
        this.grammar = grammar;
    }

    @Override
    public ParserTree parse() throws ParserException {

        // Add state for first rule
        states.add(new ArrayList<>());

        if (grammar.getRules().isEmpty()) {
            return null;
        }

        String firstName = grammar.getRules().get(0).getName();
        addFirst(firstName);

        for (int i = 0; i < statement.length() + 1; i++) {
            states.add(new ArrayList<>());
            added.clear();

            List<State> list = states.get(i);
            for (int j = 0; j < list.size(); j++) {
                State state = list.get(j);
                if (!state.isComplete()) {
                    if (state.nextTerm().isRule()) {
                        predictor(state, i);
                    } else if (statement.length() > i){
                        scanner(state, i);
                    }
                } else {
                    completer(state, i);
                }
            }
        }

        ParserTree result = getResult(firstName);

        if (result == null) {
            findError();
        }

        return result;
    }

    private void findError() throws ParserException {
        for (int i = 0; i < statement.length(); i++) {
            if (states.get(i + 1).size() == 0) {
                throw new ParserException("Unexpected symbol '" + statement.charAt(i) + "'", statement, i);
            }
        }
        throw new ParserException("Unexpected end", statement, statement.length());
    }

    private ParserTree getResult(String firstName) {
        for (State state : states.get(statement.length())) {
            if (state.isComplete() && state.rule.getName().equals(firstName)) {
                return state.tree;
            }
        }
        return null;
    }

    private void addFirst(String firstName) {
        for (Rule r : grammar.getRules()) {
            if (r.getName().equals(firstName)) {
                states.get(0).add(new State(r, 0, 0, new ParserTree(r.getName())));
            }
        }
    }

    private Set<String> added = new HashSet<>();
    private void predictor(State state, int origin) {
        Term term = state.nextTerm();

        String ruleName = term.getValue();
        if (added.contains(ruleName)) {
            return;
        } else {
            added.add(ruleName);
        }

        List<Rule> rules = grammar.getRulesByName(ruleName);

        for (Rule rule : rules) {
            states.get(origin).add(new State(rule, 0, origin, new ParserTree(ruleName)));
        }
    }

    private void scanner(State state, int origin) {
        char next = state.nextTerm().getValue().charAt(0);

        if (next == statement.charAt(origin)) {
            ParserTree tree = new ParserTree(state.tree);
            tree.addChild(new ParserTree(next + "", true));
            states.get(origin + 1).add(new State(state.rule, state.position + 1, state.origin, tree));
        }
    }

    private void completer(State state, int origin) {
        String ruleName = state.rule.getName();

        for (int i = 0; i < states.get(state.origin).size(); i++) {
            State s = states.get(state.origin).get(i);
            if (s.isComplete()) {
                continue;
            }
            Term term = s.nextTerm();
            if (term.isRule() && term.getValue().equals(ruleName)) {
                ParserTree tree = new ParserTree(s.tree);
                tree.addChild(state.tree);
                states.get(origin).add(new State(s.rule, s.position + 1, s.origin, tree));
            }
        }
    }

    private class State {

        private Rule rule;
        private int position;

        private int origin;
        private ParserTree tree;

        private boolean complete = false;

        public State(Rule rule, int position, int origin, ParserTree tree) {
            this.rule = rule;
            this.position = position;
            this.origin = origin;
            this.tree = tree;
            this.complete = rule.getTerms().length <= position;
        }

        public boolean isComplete() {
            return complete;
        }

        public Term nextTerm() {
            return rule.getTerms()[position];
        }
    }

}
