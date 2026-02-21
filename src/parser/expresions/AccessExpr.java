package parser.expresions;

import interpreter.CallStack;
import parser.interfaces.Expr;

public class AccessExpr implements Expr {
    private final String var;
    private final int ind;

    public AccessExpr(String var, int ind) {
        this.var = var;
        this.ind = ind;
    }

    @Override
    public Expr get() {
        ArrayExpr array = (ArrayExpr) CallStack.resolve(var);

        return array.get(ind);
    }

    public void define(Expr expr) {
        ArrayExpr array = (ArrayExpr) CallStack.resolve(var);

        array.define(ind, expr);
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
