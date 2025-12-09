package parser.expresions;

import parser.interfaces.Expr;

public class NumberExpr implements Expr {

    public int value;

    public NumberExpr(int value) {
        this.value = value;
    }

    @Override
    public Expr get() {
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
