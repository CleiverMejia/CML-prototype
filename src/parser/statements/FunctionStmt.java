package parser.statements;

import enums.SymbolKind;
import java.util.List;
import parser.Block;
import parser.expresions.FuncExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Stmt;

public class FunctionStmt implements Stmt {
    private String funcName = null;
    private FuncExpr func = null;
    private SymbolKind kind;

    public FunctionStmt() {}

    public FunctionStmt(FuncExpr function) {
        this.funcName = function.getName();
        this.func = function;
    }

    public void setFunction(FuncExpr func) {
        this.funcName = func.getName();
        this.func = func;
    }

    public void setName(String funcName) {
        this.funcName = funcName;
    }

    public void setKind(SymbolKind kind) {
        this.kind = kind;
    }

    public String getName() {
        return funcName;
    }

    public List<VarExpr> getArgs() {
        return func.getArgs();
    }

    public FuncExpr getFunction() {
        return this.func;
    }

    public Block getBody() {
        return func.getBody();
    }

    public SymbolKind getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "Stmt<Function:" + funcName + ">";
    }
}
