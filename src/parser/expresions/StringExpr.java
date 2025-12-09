package parser.expresions;

import parser.interfaces.Expr;

public class StringExpr implements Expr {

    public String text;

    public StringExpr(String text) {
        this.text = text;
    }

    @Override
    public Expr get() {
        return null;
    }

    @Override
    public String toString() {
        return text;
    }
}
