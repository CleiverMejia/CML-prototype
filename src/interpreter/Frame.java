package interpreter;

import parser.expresions.ObjExpr;
import parser.interfaces.Expr;

public class Frame {
    private ObjExpr thisObj;
    private Scope scope;
    private Expr returnValue;

    public Frame(ObjExpr thisObj, Scope scope) {
        this.thisObj = thisObj;
        this.scope = scope;
    }

    public ObjExpr getThisObj() {
        return thisObj;
    }

    public void setThisObj(ObjExpr thisObj) {
        this.thisObj = thisObj;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Expr getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Expr returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String toString() {
        return "\nframe: " + scope.toString() + ": " + thisObj;
    }
}
