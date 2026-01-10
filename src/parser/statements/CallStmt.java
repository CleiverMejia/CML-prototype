package parser.statements;

import interpreter.Frame;
import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;

public class CallStmt implements Stmt {

    private final String name;
    private final List<Expr> args;

    public CallStmt(String name, ArrayList<Expr> args) {
        this.name = name;
        this.args = args;
    }

    public Block getBody() {
        FuncExpr func = (FuncExpr) Frame.get(name);
        Frame.createArgScope();

        for (int i = 0; i < func.getArgs().size(); i++) {
            Frame.putArg(func.getArgs().get(i), args.get(i));
        }

        return func.getBody();
    }

    public String getName() {
        return name;
    }
}
