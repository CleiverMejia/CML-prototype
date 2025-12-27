package lib;

import interpreter.Frame;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.interfaces.Expr;
import parser.statements.ExternStmt;
import parser.statements.FunctionStmt;

public class Print extends FunctionStmt {

    public Print() {
        Block block = new Block(new ExternStmt() {
            @Override
            public void exec() {
                Expr expr = Frame.get("text");
                System.out.println(expr);
            }
        });

        FuncExpr function = new FuncExpr(block, "text");

        setName("print");
        setFunction(function);
    }
}
