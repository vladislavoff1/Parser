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
    public ParserTree parse() {
        states.add(new ArrayList<>());

        if (grammar.getRules().isEmpty()) {
            return null;
        }

        Rule first = grammar.getRules().get(0);
        states.get(0).add(new State(first, 0, 0, new ParserTree(first.getName())));

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

        for (State state : states.get(statement.length())) {
            if (state.isComplete() && state.rule.getName().equals(first.getName())) {
                return state.tree;
            }
        }

        return null;
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

        public Rule rule;
        public int position;

        public int origin;
        public ParserTree tree;

        public State(Rule rule, int position, int origin, ParserTree tree) {
            this.rule = rule;
            this.position = position;
            this.origin = origin;
            this.tree = tree;
        }

        public boolean isComplete() {
            return rule.getTerms().length <= position;
        }

        public Term nextTerm() {
            return rule.getTerms()[position];
        }
    }

}
