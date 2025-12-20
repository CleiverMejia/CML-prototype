package parser.expresions.comparations;

import interpreter.SymbolTable;
import parser.expresions.BoolExpr;
import parser.expresions.NumberExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class LessComp implements Comp {

    private final Expr left;
    private final Expr right;

    public LessComp(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expr get() {
        Expr leftTemp = left;
        Expr rightTemp = right;

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
        NumberExpr leftNumber = (leftTemp instanceof NumberExpr lNum) ? lNum : null;
        String leftString = (leftTemp instanceof StringExpr lStr) ? lStr.text : null;
        Boolean leftBoolean = (leftTemp instanceof BoolExpr lBool) ? lBool.value : null;

        NumberExpr rightNumber = (rightTemp instanceof NumberExpr rNum) ? rNum : null;
        String rightString = (rightTemp instanceof StringExpr rStr) ? rStr.text : null;
        Boolean rightBoolean = (rightTemp instanceof BoolExpr rBool) ? rBool.value : null;

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
