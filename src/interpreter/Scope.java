package interpreter;

import java.util.HashMap;
import java.util.Map;
import parser.interfaces.Expr;

public class Scope {
    private final Map<String, Expr> vars = new HashMap<>();
    private Scope parent = null;

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public boolean contains(String name) {
        return vars.containsKey(name);
    }

    public Expr get(String name) {
        return vars.get(name);
    }

    public Expr getVar(String name) {
        return vars.get(name);
    }

    public void define(String name, Expr expr) {
        vars.put(name, expr);
    }

    public Expr resolveVar(String name) {
        return vars.get(name);
    }

    public Scope getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return vars.toString();
    }
}
