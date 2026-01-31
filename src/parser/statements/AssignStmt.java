package parser.statements;

import interpreter.CallStack;
import parser.expresions.CallExpr;
import parser.expresions.FieldExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Oper;
import parser.interfaces.Stmt;

public class AssignStmt implements Stmt {

    private final Expr var;
    private final Expr expr;

    public AssignStmt(Expr var, Expr expr) {
        this.var = var;
        this.expr = expr;
    }

    public String getName() {
        if (var instanceof VarExpr varExpr) {
            return varExpr.getName();
        }

        return null;
    }

    public Expr getValue() {
        if (expr instanceof Oper exprOp) {
            return exprOp.get();
        }

        if (expr instanceof VarExpr exprVar) {
            return CallStack.resolve(exprVar.getName());
        }

        if (expr instanceof CallExpr exprCall) {
            return exprCall.get();
        }

        return expr;
    }

    public void exec() {
        if (var instanceof VarExpr varExpr) {
            CallStack.define(varExpr.getName(), getValue());
        }

        if (var instanceof FieldExpr fieldExpr) {
            fieldExpr.define(getValue());
        }
    }

    @Override
    public String toString() {
        return "Stmt<Assign>";
    }
}
