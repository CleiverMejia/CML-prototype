package parser.expresions;

import parser.interfaces.Expr;

public class FuncExpr implements Expr {
    private final String name;

    public FuncExpr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Expr get() {
        return null;
    }
}
