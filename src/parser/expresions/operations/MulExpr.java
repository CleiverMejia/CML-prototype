package parser.expresions.operations;

import interpreter.CallStack;
import parser.expresions.AccessExpr;
import parser.expresions.BoolExpr;
import parser.expresions.CallExpr;
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

        // Array Access
        if (leftTemp instanceof AccessExpr leftAccess) {
            leftTemp = leftAccess.get();
        }
        if (rightTemp instanceof AccessExpr rightAccess) {
            rightTemp = rightAccess.get();
        }

        // calls
        if (leftTemp instanceof CallExpr leftCall) {
            leftTemp = leftCall.get();
        }
        if (rightTemp instanceof CallExpr rightCall) {
            rightTemp = rightCall.get();
        }

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
            leftTemp = CallStack.resolve(leftVar.getName());
        }
        if (rightTemp instanceof VarExpr rightVar) {
            rightTemp = CallStack.resolve(rightVar.getName());
        }

        // Number or String
        Float leftNumber = (leftTemp instanceof NumberExpr lNum) ? lNum.value : null;
        String leftString = (leftTemp instanceof StringExpr lStr) ? lStr.text : null;
        Boolean leftBoolean = (leftTemp instanceof BoolExpr lBool) ? lBool.value : null;

        Float rightNumber = (rightTemp instanceof NumberExpr rNum) ? rNum.value : null;
        Boolean rightBoolean = (rightTemp instanceof BoolExpr rBool) ? rBool.value : null;

        // Number
        if (leftNumber != null && rightNumber != null) {
            return new NumberExpr(leftNumber * rightNumber);
        }

        // String
        if (leftString != null && rightNumber != null) {
            return new StringExpr(leftString.repeat(rightNumber.intValue()));
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
