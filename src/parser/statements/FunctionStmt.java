package parser.statements;

import java.util.ArrayList;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.interfaces.Stmt;

public class FunctionStmt implements Stmt {
    private String funcName = null;
    private FuncExpr func = null;

    public FunctionStmt() {}

    public FunctionStmt(String funcName, ArrayList<String> args, Block body) {
        this.funcName = funcName;
        this.func = new FuncExpr(args, body);
    }

    public void setName(String funcName) {
        this.funcName = funcName;
    }

    public void setFunction(FuncExpr func) {
        this.func = func;
    }

    public String getName() {
        return funcName;
    }

    public FuncExpr getFunction() {
        return this.func;
    }

    public Block getBody() {
        return func.getBody();
    }
}
