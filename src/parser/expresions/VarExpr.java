package parser.expresions;

import enums.SymbolKind;
import interpreter.Frame;
import parser.interfaces.Expr;

public class VarExpr implements Expr {
    private final String name;
    private SymbolKind kind;

    public VarExpr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SymbolKind getKind() {
        return kind;
    }

    public void setKind(SymbolKind kind) {
        this.kind = kind;
    }

    @Override
    public Expr get() {
        return null;
    }

    @Override
    public String toString() {
        return Frame.get(name) + "";
    }
}
