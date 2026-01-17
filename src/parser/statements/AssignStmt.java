package parser.statements;

import enums.SymbolKind;
import interpreter.Frame;
import parser.expresions.CallExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Oper;
import parser.interfaces.Stmt;

public class AssignStmt implements Stmt {

    private final VarExpr var;
    private final Expr expr;

    public AssignStmt(VarExpr var, Expr expr) {
        this.var = var;
        this.expr = expr;
    }

    public void setKind(SymbolKind symbolKind) {
        var.setKind(symbolKind);
    }

    public String getName() {
        return var.getName();
    }

    public Expr getValue() {
        if (expr instanceof Oper exprOp) {
            return exprOp.get();
        }

        if (expr instanceof VarExpr exprVar) {
            return Frame.get(exprVar.getName());
        }

        if (expr instanceof CallExpr exprCall) {
            return exprCall.get();
        }

        return expr;
    }

    public SymbolKind getKind() {
        return var.getKind();
    }

    @Override
    public String toString() {
        return "Stmt<Assign>";
    }
}
