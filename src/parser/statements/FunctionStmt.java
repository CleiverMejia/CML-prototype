package parser.statements;

import parser.Block;
import parser.expresions.FuncExpr;
import parser.interfaces.Stmt;

public class FunctionStmt implements Stmt {
    private final FuncExpr funcName;
    private final Block body;

    public FunctionStmt(String funcName, Block body) {
        this.funcName = new FuncExpr(funcName);
        this.body = body;
    }

    public String getName() {
        return funcName.getName();
    }

    public Block getBody() {
        return body;
    }
}
