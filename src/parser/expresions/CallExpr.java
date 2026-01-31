package parser.expresions;

import interpreter.CallStack;
import interpreter.Interpreter;
import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class CallExpr implements Expr {

    private Expr funcName;
    private final List<Expr> args;
    private ObjExpr obj = null;

    public CallExpr(Expr funcName, ArrayList<Expr> args) {
        this.funcName = funcName;
        this.args = args;
    }

    public void setConstructor(String name) {
        funcName = new FieldExpr(new VarExpr(name), (VarExpr) funcName);
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
            func = (FuncExpr) CallStack.resolve(varExpr.getName());
        }

        if (expr instanceof FuncExpr funcExpr) {
            func = funcExpr;
        }

        if (func == null) {
            throw new Error(funcName + " is not a function, " + (expr != null ? expr.getClass() : "null"));
        }

        CallStack.createFrame(obj);
        for (int i = 0; i < func.getArgs().size(); i++) {
            Expr exprArg = args.get(i);
            String arg = func.getArgs().get(i).getName();

            if (exprArg instanceof CallExpr argCall) {
                exprArg = argCall.get();
            }

            if (exprArg instanceof VarExpr argVar) {
                exprArg = CallStack.resolveArg(argVar.getName());
            }

            if (exprArg instanceof Oper argOper) {
                exprArg = argOper.get();
            }

            CallStack.define(arg, exprArg);
        }

        return func.getBody();
    }

    public String getName() {
        if (funcName instanceof VarExpr varExpr) {
            return varExpr.getName();
        }

        return funcName.toString();
    }

    @Override
    public Expr get() {
        Interpreter.run(getBody());
        CallStack.closeFrame();

        return CallStack.getReturn();
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
