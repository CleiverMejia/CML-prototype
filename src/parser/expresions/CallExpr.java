package parser.expresions;

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
        FuncExpr func = (FuncExpr) Frame.get(name);
        Frame.createArgScope();

        for (int i = 0; i < func.getArgs().size(); i++) {
            Expr curArg = args.get(i);

            if (curArg instanceof CallExpr argCall) {
                curArg = argCall.get();
            }

            if (curArg instanceof VarExpr argVar) {
                curArg = Frame.get(argVar.getName());
            }

            if (curArg instanceof Oper argOper) {
                curArg = argOper.get();
            }

            Frame.putArg(func.getArgs().get(i), curArg);
        }

        return func.getBody();
    }

    public String getName() {
        return name;
    }

    @Override
    public Expr get() {
        Interpreter.run(getBody());
        Frame.popArg();

        return Frame.getReturn();
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
