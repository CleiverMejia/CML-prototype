package parser.expresions;

import parser.interfaces.Expr;

public class VarExpr implements Expr {
    public String name;

    public VarExpr(String name) {
        this.name = name;
    }

    @Override
    public Expr get() {
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
