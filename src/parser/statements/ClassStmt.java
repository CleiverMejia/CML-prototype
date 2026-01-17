package parser.statements;

import enums.SymbolKind;
import parser.Block;
import parser.expresions.ClassExpr;
import parser.interfaces.Stmt;

public class ClassStmt implements Stmt{
    private String name = null;
    private ClassExpr clss = null;
    private SymbolKind kind;

    public ClassStmt() {}

    public ClassStmt(ClassExpr clss) {
        this.name = clss.getName();
        this.clss = clss;
    }

    public void setClss(ClassExpr clss) {
        this.name = clss.getName();
        this.clss = clss;
    }

    public void setKind(SymbolKind kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public Block getBody() {
        return clss.getBody();
    }

    public ClassExpr getClss() {
        return clss;
    }

    public SymbolKind getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "Stmt<Class>";
    }
}
