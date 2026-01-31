package parser.expresions;

import interpreter.CallStack;
import parser.interfaces.Expr;

public class VarExpr implements Expr {
    private final String name;

    public VarExpr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Expr get() {
        return null;
    }

    @Override
    public String toString() {
        return CallStack.resolve(name) + "";
    }
}
