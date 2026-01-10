package parser.expresions.operations;

import interpreter.Frame;
import parser.expresions.CallExpr;
import parser.expresions.NumberExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class DivExpr implements Oper {

    private final Expr left;
    private final Expr right;

    public DivExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expr get() {
        Expr leftTemp = left;
        Expr rightTemp = right;

        // calls
        if (leftTemp instanceof CallExpr leftCall) {
            leftTemp = leftCall.get();
        }
        if (rightTemp instanceof CallExpr rightCall) {
            rightTemp = rightCall.get();
        }

        // Variables
        if (leftTemp instanceof VarExpr leftVar) {
            leftTemp = Frame.get(leftVar.getName());
        }
        if (rightTemp instanceof VarExpr rightVar) {
            rightTemp = Frame.get(rightVar.getName());
        }

        // Operations
        if (leftTemp instanceof Oper leftOp) {
            leftTemp = leftOp.get();
        }
        if (rightTemp instanceof Oper rightOp) {
            rightTemp = rightOp.get();
        }

        // Number or String
        Float leftNumber = (leftTemp instanceof NumberExpr lNum) ? lNum.value : null;
        String leftString = (leftTemp instanceof StringExpr lStr) ? lStr.text : null;

        Float rightNumber = (rightTemp instanceof NumberExpr rNum) ? rNum.value : null;
        String rightString = (rightTemp instanceof StringExpr rStr) ? rStr.text : null;

        if (leftNumber != null && rightNumber != null) {
            return new NumberExpr(leftNumber / rightNumber);
        }
        if (leftNumber != null && rightString != null) {
            return new NumberExpr(leftNumber / Float.valueOf(rightString));
        }
        if (leftString != null && rightNumber != null) {
            return new NumberExpr(Float.valueOf(leftString) / rightNumber);
        }

        throw new Error("left->" + (leftTemp == null ? "null" : leftTemp.getClass())
                + " / right->" + (rightTemp == null ? "null" : rightTemp.getClass())
                + " is not valid");
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
