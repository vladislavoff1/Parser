package grammar;

/**
 * Created by vlad on 11/03/16.
 */
public class Term {

    private String text;
    private Boolean isRule = false;

    private Term(String str, Boolean isRule) {
        this.text = str;
        this.isRule = isRule;
    }

    public static Term createTextTerm(String text) {
        return new Term(text, false);
    }

    public static Term createRuleTerm(String rule) {
        return new Term(rule, true);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        char begin;
        char end;

        if (isRule) {
            begin = '<';
            end = '>';
        } else {
            if (text.indexOf('"') >= 0) {
                begin = end = '\'';
            } else {
                begin = end = '"';
            }
        }

        stringBuilder.append(begin);
        stringBuilder.append(text);
        stringBuilder.append(end);

        return stringBuilder.toString();
    }
}
