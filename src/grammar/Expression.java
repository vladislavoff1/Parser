package grammar;

import grammar.Term;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vlad on 11/03/16.
 */
public class Expression {

    private List<Term> terms;

    public Expression(Term term) {
        terms = new ArrayList<>();
        terms.add(term);
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

        for (Term term : terms) {
            stringBuilder.append(term);
            stringBuilder.append(" ");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

}
