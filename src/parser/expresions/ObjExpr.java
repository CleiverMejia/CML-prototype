package parser.expresions;

import java.util.Map;
import parser.interfaces.Expr;

public class ObjExpr implements Expr {
    private final String name;
    private final Map<String, Expr> declarations;

    public ObjExpr(String name, Map<String, Expr> declarations) {
        this.name = name;
        this.declarations = declarations;
    }

    public Map<String, Expr> getDeclarations() {
        return declarations;
    }

    public Expr getDeclaration(String field) {
        return declarations.get(field);
    }

    @Override
    public Expr get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public String toString() {
        return "Object<" + name + ">";
    }
}
