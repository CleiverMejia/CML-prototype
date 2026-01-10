package interpreter;

import parser.Block;
import parser.expresions.BoolExpr;
import parser.interfaces.Stmt;
import parser.statements.*;

public class Interpreter {

    public static void run(Block block) {
        Frame.createScope();

        for (Stmt stmt : block) {
            if (stmt instanceof AssignStmt assignStmt) {
                Frame.put(assignStmt.getName(), assignStmt.getValue());
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

            if (stmt instanceof FunctionStmt functionStmt) {
                Frame.put(functionStmt.getName(), functionStmt.getFunction());
            }

            if (stmt instanceof CallStmt callStmt) {
                run(callStmt.getBody());
                Frame.popArg();
            }

            if (stmt instanceof ExternStmt externStmt) {
                externStmt.exec();
            }

            if (stmt instanceof ReturnStmt returnStmt) {
                Frame.setReturn(returnStmt.getExpr());
                break;
            }

            if (stmt instanceof ClassStmt classStmt) {
                Frame.put(classStmt.getName(), classStmt.getClss());
            }
        }

        Frame.pop();
    }
}
