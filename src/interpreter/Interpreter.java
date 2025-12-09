package interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.expresions.BoolExpr;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;
import parser.statements.AssignStmt;
import parser.statements.IfStmt;
import parser.statements.PrintStmt;

public class Interpreter {

    public static final Map<String, Expr> symbolTable = new HashMap<>();

    public void run(List<Stmt> main) {

        for (Stmt stmt : main) {
            if (stmt instanceof PrintStmt printStmt) {
                System.out.println(printStmt.getText());
            }

            if (stmt instanceof AssignStmt assignStmt) {
                symbolTable.put(assignStmt.getName(), assignStmt.getValue());
            }

            if (stmt instanceof IfStmt ifStmt) {
                if (((BoolExpr) ifStmt.getCondition().get()).value) {
                    run(ifStmt.getBody().getStmt());
                }
            }
        }
    }
}
