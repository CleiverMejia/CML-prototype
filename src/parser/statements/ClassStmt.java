package parser.statements;

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

    public ClassExpr getClss() {
        return clss;
    }
}
