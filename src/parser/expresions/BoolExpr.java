package parser.expresions;

import parser.interfaces.Expr;

public class BoolExpr implements Expr {

    public boolean value;

    public BoolExpr(boolean value) {
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
