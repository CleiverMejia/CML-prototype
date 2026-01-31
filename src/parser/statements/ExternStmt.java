package parser.statements;

import interpreter.CallStack;
import parser.expresions.NumberExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;

public class ExternStmt implements Stmt {

    public void exec() {
    }

    protected String resolveArgString(String name) {
        Expr expr = CallStack.resolveArg(name);

        if (expr instanceof VarExpr varExpr) {
            expr = CallStack.resolve(varExpr.getName());
        }

        if (expr instanceof StringExpr stringExpr) {
            return stringExpr.text;
        }

        throw new Error(String.format("The argument %s is not a string\n", name));
    }

    protected float resolveArgNumber(String name) {
        Expr expr = CallStack.resolve(name);

        if (expr instanceof VarExpr varExpr) {
            expr = CallStack.resolveArg(varExpr.getName());
        }

        if (expr instanceof NumberExpr numberExpr) {
            return numberExpr.value;
        }

        if (expr instanceof StringExpr stringExpr) {
            return Integer.parseInt(stringExpr.text);
        }

        throw new Error(String.format("The argument %s is not a number\n", name));
    }
}
