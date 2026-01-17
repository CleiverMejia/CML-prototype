package semantic;

import enums.SymbolKind;
import parser.Block;
import parser.expresions.CallExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Stmt;
import parser.statements.*;
import semantic.scope.*;

public class Semantic {
    Scope current = new Scope();

    public void run(Block block) {
        for (Stmt stmt : block) {
            if (stmt instanceof AssignStmt assignStmt) {
                String name = assignStmt.getName();

                current.putSymbol(name, new SymbolInfo(name, SymbolKind.LOCAL));
                assignStmt.setKind(name.contains(".") ? SymbolKind.FIELD : SymbolKind.LOCAL);
            }

            if (stmt instanceof IfStmt ifStmt) {
                current = new IfScope(current);

                run(ifStmt.getBody());

            }

            if (stmt instanceof WhileStmt whileStmt) {
                current = new WhileScope(current);

                run(whileStmt.getBody());
            }

            if (stmt instanceof FunctionStmt functionStmt) {
                String name = functionStmt.getName();
                SymbolInfo symbol = new SymbolInfo(name, SymbolKind.METHOD);

                current.putSymbol(name, symbol);

                current = new FunctionScope(current);

                for (VarExpr argVar : functionStmt.getArgs()) {
                    String argName = argVar.getName();

                    current.putSymbol(argName, new SymbolInfo(argName, SymbolKind.PARAM));
                    argVar.setKind(SymbolKind.PARAM);
                }

                run(functionStmt.getBody());
            }

            if (stmt instanceof ClassStmt classStmt) {
                String className = classStmt.getName();

                current.putSymbol(className, new SymbolInfo(className, SymbolKind.CLASS));
                classStmt.setKind(SymbolKind.CLASS);

                current = new ClassScope(current);

                run(classStmt.getBody());
            }

            if (stmt instanceof InstanceStmt instanceStmt) {
                instanceStmt.setKind(SymbolKind.LOCAL);
            }
        }

        //System.out.println(current);

        current = current.getParent();
    }
}
