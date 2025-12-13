package parser.expresions;

import interpreter.Interpreter;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class MinusExpr implements Oper {

    private Expr left;
    private Expr right;

    public MinusExpr(Expr left, Expr right) {
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
        Integer leftNumber = (left instanceof NumberExpr lNum) ? lNum.value : null;
        String leftString = (left instanceof StringExpr lStr) ? lStr.text : null;
        Boolean leftBoolean = (left instanceof BoolExpr lBool) ? lBool.value : null;

        Integer rightNumber = (right instanceof NumberExpr rNum) ? rNum.value : null;
        String rightString = (right instanceof StringExpr rStr) ? rStr.text : null;
        Boolean rightBoolean = (right instanceof BoolExpr rBool) ? rBool.value : null;

        // Number
        if (leftNumber != null && rightNumber != null) {
            return new NumberExpr(leftNumber - rightNumber);
        }
        if (leftNumber != null && rightString != null) {
            return new NumberExpr(leftNumber - Integer.valueOf(rightString));
        }
        if (leftNumber != null && rightBoolean != null) {
            return new NumberExpr(leftNumber - (rightBoolean ? 1 : 0));
        }

        // String
        if (leftString != null && rightNumber != null) {
            return new StringExpr(leftString.substring(0, leftString.length() - rightNumber));
        }
        if (leftString != null && rightString != null) {
            if (leftString.endsWith(rightString)) {
                return new StringExpr(leftString.substring(0, leftString.length() - rightString.length()));
            }

            return new StringExpr(leftString);
        }
        if (leftString != null && rightBoolean != null) {
            if (leftString.endsWith(rightBoolean.toString())) {
                return new StringExpr(leftString.substring(0, leftString.length() - rightBoolean.toString().length()));
            }

            return new StringExpr(leftString);
        }

        // Boolean
        if (leftBoolean != null && rightNumber != null) {
            return new NumberExpr((leftBoolean ? 1 : 0) - rightNumber);
        }
        if (leftBoolean != null && rightString != null) {
            if (leftBoolean.toString().endsWith(rightString)) {
                return new StringExpr(leftBoolean.toString().substring(0, leftBoolean.toString().length() - rightString.length()));
            }

            return new StringExpr(leftBoolean.toString());
        }
        if (leftBoolean != null && rightBoolean != null) {
            return new BoolExpr(leftBoolean && !rightBoolean);
        }

        return null;
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
