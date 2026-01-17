package parser.statements;

import enums.SymbolKind;
import parser.expresions.CallExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Stmt;

public class InstanceStmt implements Stmt {
    private final VarExpr obj;
    private final CallExpr classConstructor;
    private SymbolKind kind;

    public InstanceStmt(VarExpr obj, CallExpr classConstructor) {
        this.obj = obj;
        this.classConstructor = classConstructor;
    }

    public void setKind(SymbolKind kind) {
        this.kind = kind;
    }

    public String getObjName() {
        return obj.getName();
    }

    public String getClassName() {
        return classConstructor.getName();
    }

    public CallExpr getConstructor() {
        return classConstructor;
    }

    public SymbolKind getKind() {
        return kind;
    }

    @Override
    public String toString() {
        return "Stmt<Instance>";
    }
}
