package parser.statements;

import enums.SymbolKind;
import interpreter.Frame;
import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;

public class CallStmt implements Stmt {

    private final VarExpr funcName;
    private final List<Expr> args;

    public CallStmt(String funcName, ArrayList<Expr> args) {
        this.funcName = new VarExpr(funcName);
        this.args = args;
    }

    public Block getBody() {
        Expr expr = Frame.get(funcName.getName());

        FuncExpr func = null;
        if (expr instanceof VarExpr varExpr) {
            func = (FuncExpr) Frame.get(varExpr.getName());
        }

        if (expr instanceof FuncExpr funcExpr) {
            func = funcExpr;
        }

        if (func == null) {
            throw new Error(funcName.getName() + " is not a function");
        }

        for (int i = 0; i < func.getArgs().size(); i++) {
            String arg = func.getArgs().get(i).getName();
            SymbolKind kind = func.getArgs().get(i).getKind();

            Frame.put(arg, args.get(i), kind);
        }

        return func.getBody();
    }

    @Override
    public String toString() {
        return "Stmt<Call>";
    }
}
