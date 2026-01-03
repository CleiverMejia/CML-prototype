package parser.expresions;

import parser.interfaces.Expr;

public class NumberExpr implements Expr {

    public float value;

    public NumberExpr(float value) {
        this.value = value;
    }

    @Override
    public Expr get() {
        return null;
    }

    @Override
    public String toString() {
        return value == (int) value ? String.valueOf((int) value) : String.valueOf(value);
    }
}
