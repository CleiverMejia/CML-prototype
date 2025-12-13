package parser.expresions;

import interpreter.Interpreter;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class AndComp implements Comp {

    public Expr left;
    public Expr right;

    public AndComp(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expr get() {
        // Operations
        if (left instanceof Oper leftOp) {
            left = (Expr) leftOp.get();
        }
        if (right instanceof Oper rightOp) {
            right = (Expr) rightOp.get();
        }

        // Comparation
        if (left instanceof Comp leftOp) {
            left = leftOp.get();
        }
        if (right instanceof Comp rightOp) {
            right = rightOp.get();
        }

        // Variables
        if (left instanceof VarExpr leftVar) {
            left = Interpreter.symbolTable.get(leftVar.name);
        }
        if (right instanceof VarExpr rightVar) {
            right = Interpreter.symbolTable.get(rightVar.name);
        }

        // Number or String
        NumberExpr leftNumber = (left instanceof NumberExpr lNum) ? lNum : null;
        String leftString = (left instanceof StringExpr lStr) ? lStr.text : null;
        Boolean leftBoolean = (left instanceof BoolExpr lBool) ? lBool.value : null;

        NumberExpr rightNumber = (right instanceof NumberExpr rNum) ? rNum : null;
        String rightString = (right instanceof StringExpr rStr) ? rStr.text : null;
        Boolean rightBoolean = (right instanceof BoolExpr rBool) ? rBool.value : null;

        // Number
        if (leftNumber != null && rightNumber != null) {
            return new BoolExpr(false);
        }
        if (leftNumber != null && rightString != null) {
            return new BoolExpr(false);
        }
        if (leftNumber != null && rightBoolean != null) {
            return new BoolExpr(false);
        }

        // String
        if (leftString != null && rightNumber != null) {
            return new BoolExpr(false);
        }
        if (leftString != null && rightString != null) {
            return new BoolExpr(false);
        }
        if (leftString != null && rightBoolean != null) {
            return new BoolExpr(false);
        }

        // Boolean
        if (leftBoolean != null && rightNumber != null) {
            return new BoolExpr(false);
        }
        if (leftBoolean != null && rightString != null) {
            return new BoolExpr(false);
        }
        if (leftBoolean != null && rightBoolean != null) {
            return new BoolExpr(leftBoolean && rightBoolean);
        }

        return null;
    }

    @Override
    public String toString() {
        return left + "&&" + right;
    }
}
