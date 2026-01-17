package parser.expresions;

import parser.interfaces.Expr;

public class NumberExpr implements Expr {
    public static final NumberExpr ZERO = new NumberExpr(0);
    public static final NumberExpr ONE = new NumberExpr(1);

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
