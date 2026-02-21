package interpreter;

import java.util.ArrayList;
import parser.Block;
import parser.expresions.BoolExpr;
import parser.expresions.CallExpr;
import parser.expresions.ClassExpr;
import parser.interfaces.Stmt;
import parser.statements.*;

public class Interpreter {

    public static String sourcePath = "";
    public static ArrayList<String> imports = new ArrayList<>();

    public static void run(Block block) {
        CallStack.createScope();
        int ind = 0;

        while (ind < block.size()) {
            Stmt stmt = block.get(ind);

            if (stmt instanceof AssignStmt assignStmt) {
                assignStmt.exec();
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
                functionStmt.setScope(CallStack.currentScope());

                CallStack.define(
                        functionStmt.getName(),
                        functionStmt.getFunction()
                );
            }

            if (stmt instanceof CallStmt callStmt) {
                run(callStmt.getBody());
                CallStack.closeFrame();
            }

            if (stmt instanceof ExternStmt externStmt) {
                externStmt.exec();
            }

            if (stmt instanceof ReturnStmt returnStmt) {
                CallStack.setReturn(returnStmt.getExpr());
                break;
            }

            if (stmt instanceof ClassStmt classStmt) {
                CallStack.define(classStmt.getName(), classStmt.getClss());
            }

            if (stmt instanceof InstanceStmt instanceStmt) {
                ClassExpr clss = (ClassExpr) CallStack.resolve(instanceStmt.getClassName());
                CallExpr constructor = instanceStmt.getConstructor();

                CallStack.define(instanceStmt.getObjName(), clss.getObject());

                constructor.get();
            }

            if (stmt instanceof ImportStmt importStmt) {
                block.remove(ind);

                Block importResolved = importStmt.resolve();

                if (importResolved != null) {
                    block.addAll(ind, importResolved);
                }

                continue;
            }

            ind++;
        }

        CallStack.closeScope();
    }
}
