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
                if (expr != null) {
                    String text = expr.toString()
                            .replace("\\\\", "\\")
                            .replace("\\n", "\n")
                            .replace("\\t", "\t")
                            .replace("\\r", "\r")
                            .replace("\\b", "\b")
                            .replace("\\f", "\f")
                            .replace("\\\"", "\"")
                            .replace("\\'", "'");

                    System.out.println(text);
                    return;
                }

                System.out.println("");
            }
        });

        FuncExpr function = new FuncExpr("print", block, "text");

        setFunction(function);
    }
}
