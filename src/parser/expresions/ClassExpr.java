package parser.expresions;

import java.util.HashMap;
import java.util.Map;
import parser.Block;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;
import parser.statements.AssignStmt;
import parser.statements.FunctionStmt;

public class ClassExpr implements Expr {
    private final String name;
    private final Map<String, Expr> declarations = new HashMap<>();
    private final Block body = new Block();

    public ClassExpr(String name, Block body) {
        this.name = name;

        FunctionStmt constructor = new FunctionStmt(
            new FuncExpr(name, new Block())
        );

        this.declarations.put(name, constructor.getFunction());
        this.body.add(constructor);

        for (Stmt stmt : body) {
            if (stmt instanceof AssignStmt assignStmt) {
                this.declarations.put(assignStmt.getName(), assignStmt.getValue());
            }

            if (stmt instanceof FunctionStmt functionStmt) {
                this.declarations.put(functionStmt.getName(), functionStmt.getFunction());
            }

            if (stmt instanceof AssignStmt || stmt instanceof FunctionStmt) {
                this.body.add(stmt);
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

    public ObjExpr getObject()  {
        return new ObjExpr(name, new HashMap<>(declarations));
    }

    public Block getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Class<" + name + ">";
    }
}
