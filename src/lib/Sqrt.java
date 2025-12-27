package lib;

import interpreter.Frame;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.expresions.NumberExpr;
import parser.statements.ExternStmt;
import parser.statements.FunctionStmt;

public class Sqrt extends FunctionStmt {

    public Sqrt() {
        Block block = new Block(new ExternStmt() {
            @Override
            public void exec() {
                NumberExpr a = (NumberExpr) Frame.get("a");

                System.out.println(Math.sqrt(a.value));
            }
        });

        FuncExpr function = new FuncExpr(block, "a");

        setName("sqrt");
        setFunction(function);
    }
}
