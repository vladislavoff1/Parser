package grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vlad on 11/03/16.
 */
public class Rule {

    private String name;
    private List<Term> terms;

    public Rule(String name) {
        this.name = name;
        terms = new ArrayList<>();
    }

    public Rule(String name, List<Term> terms) {
        this.name = name;
        this.terms = terms;
    }

    public String getName() {
        return name;
    }

    public Term[] getTerms() {
        return (Term[]) terms.toArray();
    }

    public void addTerm(Term term) {
        terms.add(term);
    }

    public void addTerm(Collection<Term> terms) {
        this.terms.addAll(terms);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<");
        stringBuilder.append(name);
        stringBuilder.append("> ::= ");

        for (Term term : terms) {
            stringBuilder.append(term);
            stringBuilder.append(" ");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

}
