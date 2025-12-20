package interpreter;

import parser.Block;
import parser.expresions.BoolExpr;
import parser.interfaces.Stmt;
import parser.statements.AssignStmt;
import parser.statements.IfStmt;
import parser.statements.PrintStmt;
import parser.statements.WhileStmt;

public class Interpreter {

    public void run(Block block) {
        SymbolTable.createScope();

        for (Stmt stmt : block) {
            if (stmt instanceof PrintStmt printStmt) {
                System.out.println(printStmt.getText());
            }

            if (stmt instanceof AssignStmt assignStmt) {
                SymbolTable.put(assignStmt.getName(), assignStmt.getValue());
            }

            if (stmt instanceof IfStmt ifStmt) {
                BoolExpr condition = (BoolExpr) ifStmt.getCondition().get();
                if (condition.value) {
                    run(ifStmt.getBody());
                } else if (ifStmt.getElse() != null) {
                    run(ifStmt.getElse());
                }
            }

            if (stmt instanceof WhileStmt whileStmt) {
                BoolExpr condition = (BoolExpr) whileStmt.getCondition().get();

                while (condition.value) {
                    run(whileStmt.getBody());

                    condition = (BoolExpr) whileStmt.getCondition().get();
                }
            }
        }

        SymbolTable.pop();
    }
}
