package parser.expresions.operations;

import interpreter.SymbolTable;
import parser.expresions.BoolExpr;
import parser.expresions.NumberExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class MinusExpr implements Oper {

    private final Expr left;
    private final Expr right;

    public MinusExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expr get() {
        Expr leftTemp = this.left;
        Expr rightTemp = this.right;

        // Operations
        if (leftTemp instanceof Oper leftOp) {
            leftTemp = leftOp.get();
        }
        if (rightTemp instanceof Oper rightOp) {
            rightTemp = rightOp.get();
        }

        // Comparation
        if (leftTemp instanceof Comp leftOp) {
            leftTemp = leftOp.get();
        }
        if (rightTemp instanceof Comp rightOp) {
            rightTemp = rightOp.get();
        }

        // Variables
        if (leftTemp instanceof VarExpr leftVar) {
            leftTemp = SymbolTable.get(leftVar.getName());
        }
        if (rightTemp instanceof VarExpr rightVar) {
            rightTemp = SymbolTable.get(rightVar.getName());
        }

        // Number or String
        Integer leftNumber = (leftTemp instanceof NumberExpr lNum) ? lNum.value : null;
        String leftString = (leftTemp instanceof StringExpr lStr) ? lStr.text : null;
        Boolean leftBoolean = (leftTemp instanceof BoolExpr lBool) ? lBool.value : null;

        Integer rightNumber = (rightTemp instanceof NumberExpr rNum) ? rNum.value : null;
        String rightString = (rightTemp instanceof StringExpr rStr) ? rStr.text : null;
        Boolean rightBoolean = (rightTemp instanceof BoolExpr rBool) ? rBool.value : null;

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
