package parser.statements;

import interpreter.Frame;
import parser.expresions.BoolExpr;
import parser.expresions.NumberExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;

public class ReturnStmt implements Stmt {

    private Expr expr;

    public ReturnStmt(Expr expr) {
        this.expr = expr;
    }

    public Expr getExpr() {
        if (expr instanceof VarExpr varExpr) {
            expr = Frame.get(varExpr.getName());
        }

        if (expr instanceof NumberExpr || expr instanceof StringExpr || expr instanceof BoolExpr) {
            return expr;
        }

        return expr.get();
    }
}
