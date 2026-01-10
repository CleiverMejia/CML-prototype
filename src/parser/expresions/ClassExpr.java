package parser.expresions;

import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;
import parser.statements.AssignStmt;
import parser.statements.FunctionStmt;

public class ClassExpr implements Expr {
    private final String name;
    private final List<AssignStmt> parameters = new ArrayList<>();
    private final List<FunctionStmt> methods = new ArrayList<>();

    public ClassExpr(String name, Block body) {
        this.name = name;

        for (Stmt stmt : body) {
            if (stmt instanceof AssignStmt assignStmt) {
                this.parameters.add(assignStmt);
            }

            if (stmt instanceof FunctionStmt functionStmt) {
                this.methods.add(functionStmt);
            }
        }
    }

    @Override
    public Expr get() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        return name;
    }

    public Block getBody() {
        Block body = new Block();

        body.addAll(parameters);
        body.addAll(methods);

        return body;
    }

    @Override
    public String toString() {
        return "Class!";
    }
}
