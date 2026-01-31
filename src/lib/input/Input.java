package lib.input;

import interpreter.CallStack;
import java.util.Scanner;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.expresions.StringExpr;
import parser.interfaces.Expr;
import parser.statements.ExternStmt;
import parser.statements.FunctionStmt;

public class Input extends FunctionStmt {

    public Input() {
        Block block = new Block(new ExternStmt() {
            @Override
            public void exec() {
                try (Scanner sc = new Scanner(System.in)) {
                    Expr msg = CallStack.resolveArg("msg");

                    if (msg != null) {
                        System.out.print(msg);
                    }

                    String input = sc.nextLine();

                    CallStack.setReturn(new StringExpr(input));
                }
            }
        });

        FuncExpr function = new FuncExpr("input", block, "msg");

        setFunction(function);
    }
}
