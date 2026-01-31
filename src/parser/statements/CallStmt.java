package parser.statements;

import interpreter.CallStack;
import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.expresions.FieldExpr;
import parser.expresions.FuncExpr;
import parser.expresions.ObjExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;

public class CallStmt implements Stmt {

    private final Expr funcName;
    private final List<Expr> args;
    private ObjExpr obj = null;

    public CallStmt(Expr funcName, ArrayList<Expr> args) {
        this.funcName = funcName;
        this.args = args;
    }

    public Block getBody() {
        Expr expr = null;

        if (funcName instanceof VarExpr varExpr) {
            expr = CallStack.resolve(varExpr.getName());
        }

        FuncExpr func = null;
        if (funcName instanceof FieldExpr fieldExpr) {
            func = (FuncExpr) fieldExpr.get();
            obj = fieldExpr.getObj();
        }

        if (expr instanceof VarExpr varExpr) {
            func = (FuncExpr) CallStack.resolveArg(varExpr.getName());
        }

        if (expr instanceof FuncExpr funcExpr) {
            func = funcExpr;
        }

        if (func == null) {
            throw new Error(funcName + " is not a function");
        }

        CallStack.createFrame(obj);
        for (int i = 0; i < func.getArgs().size(); i++) {
            String arg = func.getArgs().get(i).getName();

            CallStack.define(arg, args.get(i));
        }

        return func.getBody();
    }

    @Override
    public String toString() {
        return "Stmt<Call>";
    }
}
