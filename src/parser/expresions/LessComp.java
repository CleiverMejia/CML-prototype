package parser.expresions;

import interpreter.Interpreter;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class LessComp implements Comp {

    public Expr left;
    public Expr right;

    public LessComp(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expr get() {
        // Operations
        if (left instanceof Oper leftOp) {
            left = leftOp.get();
        }
        if (right instanceof Oper rightOp) {
            right = rightOp.get();
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
            return new BoolExpr(leftNumber.value < rightNumber.value);
        }
        if (leftNumber != null && rightString != null) {
            return new BoolExpr(leftNumber.value < Integer.parseInt(rightString));
        }
        if (leftNumber != null && rightBoolean != null) {
            return new BoolExpr(leftNumber.value < (rightBoolean ? 1 : 0));
        }

        // String
        if (leftString != null && rightNumber != null) {
            return new BoolExpr(Integer.parseInt(leftString) < rightNumber.value);
        }
        if (leftString != null && rightString != null) {
            return new BoolExpr(leftString.compareTo(rightString) < 0);
        }
        if (leftString != null && rightBoolean != null) {
            return new BoolExpr(leftString.compareTo(rightBoolean.toString()) < 0);
        }

        // Boolean
        if (leftBoolean != null && rightNumber != null) {
            return new BoolExpr((leftBoolean ? 1 : 0) < rightNumber.value);
        }
        if (leftBoolean != null && rightString != null) {
            return new BoolExpr(leftBoolean.toString().compareTo(rightString) < 0);
        }
        if (leftBoolean != null && rightBoolean != null) {
            return new BoolExpr(!leftBoolean && rightBoolean);
        }

        return null;
    }

    @Override
    public String toString() {
        return left + "<" + right;
    }
}
