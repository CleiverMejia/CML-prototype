package parser.expresions;

import enums.SymbolKind;
import interpreter.Frame;
import interpreter.Interpreter;
import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class CallExpr implements Expr {

    private final String name;
    private final List<Expr> args;

    public CallExpr(String name, ArrayList<Expr> args) {
        this.name = name;
        this.args = args;
    }

    public Block getBody() {
        Expr expr = Frame.get(name);

        FuncExpr func = null;
        if (expr instanceof VarExpr varExpr) {
            func = (FuncExpr) Frame.get(varExpr.getName());
        }

        if (expr instanceof FuncExpr funcExpr) {
            func = funcExpr;
        }

        if (func == null) {
            Frame.getSelf();
            throw new Error(name + " is not a function, " + (expr != null ? expr.getClass() : "null"));
        }

        for (int i = 0; i < func.getArgs().size(); i++) {
            Expr exprArg = args.get(i);
            String arg = func.getArgs().get(i).getName();
            SymbolKind kind = func.getArgs().get(i).getKind();

            if (exprArg instanceof CallExpr argCall) {
                exprArg = argCall.get();
            }

            if (exprArg instanceof VarExpr argVar) {
                exprArg = Frame.get(argVar.getName());
            }

            if (exprArg instanceof Oper argOper) {
                exprArg = argOper.get();
            }

            Frame.put(arg, exprArg, kind);
        }

        return func.getBody();
    }

    public String getName() {
        return name;
    }

    @Override
    public Expr get() {
        Frame.createScope();
        Interpreter.run(getBody());
        Frame.pop();

        return Frame.getReturn();
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
