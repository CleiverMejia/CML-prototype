package parser.expresions;

import interpreter.Interpreter;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class MulExpr implements Oper {

    private Expr left;
    private Expr right;

    public MulExpr(Expr left, Expr right) {
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

        Integer rightNumber = (right instanceof NumberExpr rNum) ? rNum.value : null;

        if (leftNumber != null && rightNumber != null) {
            return new NumberExpr(leftNumber * rightNumber);
        }
        if (leftString != null && rightNumber != null) {
            return new StringExpr(leftString.repeat(rightNumber));
        }

        return null;
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
