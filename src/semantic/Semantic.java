package semantic;

import parser.Block;
import parser.interfaces.Stmt;
import parser.statements.AssignStmt;
import parser.statements.FunctionStmt;
import parser.statements.IfStmt;
import parser.statements.WhileStmt;

public class Semantic {

    public void run(Block block) {
        declare(block);
        assign(block);
    }

    private void declare(Block block) {
        SymbolTable.createScope();

        for (Stmt stmt : block) {
            if (stmt instanceof AssignStmt assignStmt) {
                SymbolTable.put(assignStmt.getName(), assignStmt.getValue());
            }

            if (stmt instanceof IfStmt ifStmt) {
                declare(ifStmt.getBody());

                if (ifStmt.getElse() != null) {
                    declare(ifStmt.getElse());
                }
            }

            if (stmt instanceof WhileStmt whileStmt) {
                declare(whileStmt.getBody());
            }

            if (stmt instanceof FunctionStmt functionStmt) {
                SymbolTable.put(functionStmt.getName(), functionStmt.getFunction());
                declare(functionStmt.getBody());
            }
        }

        SymbolTable.closeScope();
        System.out.println(SymbolTable.toStrings());
    }

    private void assign(Block block) {
        for (Stmt stmt : block) {
            if (stmt instanceof AssignStmt assignStmt) {
                SymbolTable.put(assignStmt.getName(), assignStmt.getValue());
            }

            if (stmt instanceof IfStmt ifStmt) {
                declare(ifStmt.getBody());

                if (ifStmt.getElse() != null) {
                    declare(ifStmt.getElse());
                }
            }

            if (stmt instanceof WhileStmt whileStmt) {
                declare(whileStmt.getBody());
            }

            if (stmt instanceof FunctionStmt functionStmt) {
                SymbolTable.put(functionStmt.getName(), functionStmt.getFunction());
                declare(functionStmt.getBody());
            }
        }
    }
}
