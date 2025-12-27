package parser.expresions.operations;

import interpreter.Frame;
import parser.expresions.BoolExpr;
import parser.expresions.NumberExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class MulExpr implements Oper {

    private final Expr left;
    private final Expr right;

    public MulExpr(Expr left, Expr right) {
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
            leftTemp = Frame.get(leftVar.getName());
        }
        if (rightTemp instanceof VarExpr rightVar) {
            rightTemp = Frame.get(rightVar.getName());
        }

        // Number or String
        Integer leftNumber = (leftTemp instanceof NumberExpr lNum) ? lNum.value : null;
        String leftString = (leftTemp instanceof StringExpr lStr) ? lStr.text : null;
        Boolean leftBoolean = (leftTemp instanceof BoolExpr lBool) ? lBool.value : null;

        Integer rightNumber = (rightTemp instanceof NumberExpr rNum) ? rNum.value : null;
        Boolean rightBoolean = (rightTemp instanceof BoolExpr rBool) ? rBool.value : null;

        // Number
        if (leftNumber != null && rightNumber != null) {
            return new NumberExpr(leftNumber * rightNumber);
        }

        // String
        if (leftString != null && rightNumber != null) {
            return new StringExpr(leftString.repeat(rightNumber));
        }

        // Boolean
        if (leftBoolean != null && rightBoolean != null) {
            return new BoolExpr(leftBoolean && rightBoolean);
        }

        return null;
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
