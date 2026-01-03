package parser.expresions;

import interpreter.Frame;
import interpreter.Interpreter;
import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.interfaces.Expr;

public class CallExpr implements Expr {

    private final String name;
    private final List<Expr> args;

    public CallExpr(String name, ArrayList<Expr> args) {
        this.name = name;
        this.args = args;
    }

    public Block getBody() {
        FuncExpr func = (FuncExpr) Frame.get(name);

        for (int i = 0; i < func.getArgs().size(); i++) {
            Expr curArg = args.get(i);
            if (curArg instanceof VarExpr varExpr) {
                curArg = Frame.get(varExpr.getName());
            }

            Frame.put(func.getArgs().get(i), curArg);
        }

        return func.getBody();
    }

    public String getName() {
        return name;
    }

    @Override
    public Expr get() {
        Interpreter.run(getBody());

        return Frame.getReturn();
    }
}
