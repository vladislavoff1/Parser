package grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vlad on 11/03/16.
 */
public class Rule {

    private String name;
    private List<Expression> expressions;

    public Rule(String name) {
        this.name = name;
        expressions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }
    public void addExpression(Collection<Expression> expressions) {
        this.expressions.addAll(expressions);
    }

    public Expression[] getExpressions() {
        return (Expression[]) expressions.toArray();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<");
        stringBuilder.append(name);
        stringBuilder.append("> ::= ");

        boolean first = true;
        for (Expression e : expressions) {
            if (!first) {
                stringBuilder.append(" | ");
            } else {
                first = false;
            }

            stringBuilder.append(e.toString());
        }

        return stringBuilder.toString();
    }

}
