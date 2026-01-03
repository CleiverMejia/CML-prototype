package parser.statements;

import parser.interfaces.Expr;
import parser.interfaces.Stmt;

public class ReturnStmt implements Stmt {
    private final Expr expr;

    public ReturnStmt(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr.get();
    }
}
