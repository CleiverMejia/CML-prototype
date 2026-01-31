package parser.statements;

import parser.expresions.CallExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Stmt;

public class InstanceStmt implements Stmt {
    private final VarExpr obj;
    private final CallExpr classConstructor;
    private final String className;

    public InstanceStmt(VarExpr obj, CallExpr classConstructor, String className) {
        this.obj = obj;
        this.classConstructor = classConstructor;
        this.className = className;
    }

    public String getObjName() {
        return obj.getName();
    }

    public String getClassName() {
        return className;
    }

    public CallExpr getConstructor() {
        return classConstructor;
    }

    @Override
    public String toString() {
        return "Stmt<Instance>";
    }
}
