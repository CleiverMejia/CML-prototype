package parser.statements;

import parser.Block;
import parser.expresions.FuncExpr;
import parser.interfaces.Stmt;

public class FunctionStmt implements Stmt {
    private String funcName = null;
    private FuncExpr func = null;

    public FunctionStmt() {}

    public FunctionStmt(FuncExpr function) {
        this.funcName = function.getName();
        this.func = function;
    }

    public void setFunction(FuncExpr func) {
        this.funcName = func.getName();
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
