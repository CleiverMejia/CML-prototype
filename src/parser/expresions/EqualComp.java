package parser.expresions;

import interpreter.Interpreter;
import java.util.Objects;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Oper;

public class EqualComp implements Comp {

    public Expr left;
    public Expr right;

    public EqualComp(Expr left, Expr right) {
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
            return new BoolExpr(Objects.equals(leftNumber, rightNumber));
        }
        if (leftNumber != null && rightString != null) {
            return new BoolExpr(Objects.equals(leftNumber, Integer.valueOf(rightString)));
        }
        if (leftString != null && rightNumber != null) {
            return new BoolExpr(Objects.equals(Integer.valueOf(leftString), rightNumber));
        }
        if (leftString != null && rightString != null) {
            return new BoolExpr(leftString.equals(rightString));
        }

        return null;
    }

    @Override
    public String toString() {
        return left + "==" + right;
    }
}
