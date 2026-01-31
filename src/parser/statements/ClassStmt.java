package parser.statements;

import parser.Block;
import parser.expresions.ClassExpr;
import parser.interfaces.Stmt;

public class ClassStmt implements Stmt{
    private String name = null;
    private ClassExpr clss = null;

    public ClassStmt() {}

    public ClassStmt(ClassExpr clss) {
        this.name = clss.getName();
        this.clss = clss;
    }

    public void setClss(ClassExpr clss) {
        this.name = clss.getName();
        this.clss = clss;
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

    @Override
    public String toString() {
        return "Stmt<Class>";
    }
}
