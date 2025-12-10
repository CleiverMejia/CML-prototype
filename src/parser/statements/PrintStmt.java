package parser.statements;

import interpreter.Interpreter;
import parser.expresions.NumberExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;

public class PrintStmt implements Stmt {

    Expr expr;

    public PrintStmt(Expr expr) {
        this.expr = expr;
    }

    public Object getText() {
        if (expr instanceof VarExpr var) {
            return Interpreter.symbolTable.get(var.name);
        }

        if (expr instanceof NumberExpr number) {
            return number.value;
        }

        if (expr instanceof StringExpr string) {
            return string.text;
        }

        return expr.get();
    }

    @Override
    public String toString() {
        return getText() + "";
    }
}
