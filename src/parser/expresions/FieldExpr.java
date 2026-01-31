package parser.expresions;

import interpreter.CallStack;
import parser.interfaces.Expr;

public class FieldExpr implements Expr {

    private final VarExpr field;
    private final VarExpr var;

    public FieldExpr(VarExpr field, VarExpr var) {
        this.field = field;
        this.var = var;
    }

    @Override
    public Expr get() {
        ObjExpr obj = getObj();

        return obj.resolve(this.var.getName());
    }

    public ObjExpr getObj() {
        return (ObjExpr) CallStack.resolve(this.field.getName());
    }

    public String getFieldName() {
        return field.getName();
    }

    public String getVarName() {
        return var.getName();
    }

    public void define(Expr expr) {
        ObjExpr obj = getObj();

        obj.define(this.var.getName(), expr);
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
