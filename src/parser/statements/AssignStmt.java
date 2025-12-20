package parser.statements;

import interpreter.SymbolTable;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Oper;
import parser.interfaces.Stmt;

public class AssignStmt implements Stmt {

    private final VarExpr varName;
    private final Expr expr;

    public AssignStmt(VarExpr varName, Expr expr) {
        this.varName = varName;
        this.expr = expr;
    }

    public String getName() {
        return varName.getName();
    }

    public Expr getValue() {
        if (expr instanceof Oper exprOp) {
            return exprOp.get();
        }

        if (expr instanceof VarExpr exprVar) {
            return SymbolTable.get(exprVar.getName());
        }

        return expr;
    }

    @Override
    public String toString() {
        return varName + " = " + expr.get();
    }
}
