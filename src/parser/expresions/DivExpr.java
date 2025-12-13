package parser.expresions;

import interpreter.Interpreter;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class DivExpr implements Expr {
    private Expr left;
    private Expr right;

    public DivExpr(Expr left, Expr right) {
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

        Integer rightNumber = (right instanceof NumberExpr rNum) ? rNum.value : null;
        String rightString = (right instanceof StringExpr rStr) ? rStr.text : null;

        if (leftNumber != null && rightNumber != null) {
            return new NumberExpr(leftNumber / rightNumber);
        }
        if (leftNumber != null && rightString != null) {
            return new NumberExpr(leftNumber / Integer.valueOf(rightString));
        }
        if (leftString != null && rightNumber != null) {
            return new NumberExpr(Integer.valueOf(leftString) / rightNumber);
        }

        return null;
    }

    @Override
    public String toString() {
        return get() + "";
    }
}
