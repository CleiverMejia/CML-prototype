package interpreter;

import enums.SymbolKind;
import parser.Block;
import parser.expresions.BoolExpr;
import parser.expresions.CallExpr;
import parser.expresions.ClassExpr;
import parser.expresions.ObjExpr;
import parser.interfaces.Stmt;
import parser.statements.*;

public class Interpreter {

    public static void run(Block block) {
        for (Stmt stmt : block) {
            if (stmt instanceof AssignStmt assignStmt) {
                Frame.put(assignStmt.getName(), assignStmt.getValue(), assignStmt.getKind());

                if (assignStmt.getKind() == SymbolKind.FIELD) {
                    Frame.closeSelf();
                }
            }

            if (stmt instanceof IfStmt ifStmt) {
                BoolExpr condition = (BoolExpr) ifStmt.getCondition().get();

                if (condition.value) {
                    Frame.createScope();
                    run(ifStmt.getBody());
                    Frame.pop();
                } else if (ifStmt.getElse() != null) {
                    Frame.createScope();
                    run(ifStmt.getElse());
                    Frame.pop();
                }
            }

            if (stmt instanceof WhileStmt whileStmt) {
                BoolExpr condition = (BoolExpr) whileStmt.getCondition().get();

                while (condition.value) {
                    Frame.createScope();
                    run(whileStmt.getBody());
                    Frame.pop();

                    condition = (BoolExpr) whileStmt.getCondition().get();
                }
            }

            if (stmt instanceof FunctionStmt functionStmt) {
                Frame.put(functionStmt.getName(), functionStmt.getFunction(), functionStmt.getKind());
            }

            if (stmt instanceof CallStmt callStmt) {
                Frame.createScope();
                run(callStmt.getBody());
                Frame.pop();
            }

            if (stmt instanceof ExternStmt externStmt) {
                externStmt.exec();
            }

            if (stmt instanceof ReturnStmt returnStmt) {
                Frame.setReturn(returnStmt.getExpr());
                break;
            }

            if (stmt instanceof ClassStmt classStmt) {
                Frame.put(classStmt.getName(), classStmt.getClss(), classStmt.getKind());
            }

            if (stmt instanceof InstanceStmt instanceStmt) {
                ClassExpr clss = (ClassExpr) Frame.get(instanceStmt.getClassName());
                ObjExpr obj = clss.getObject();
                CallExpr constructor = instanceStmt.getConstructor();

                Frame.setSelf(obj.getDeclarations());
                constructor.get();
                Frame.closeSelf();

                Frame.put(instanceStmt.getObjName(), obj, instanceStmt.getKind());
            }
        }
    }
}
