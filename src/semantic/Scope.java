package semantic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import parser.interfaces.Expr;

public class Scope {

    private final Queue<Scope> childrens = new LinkedList<>();
    private final Map<String, Expr> symbols = new HashMap<>();
    private Scope parent = null;

    public Scope() {
    }

    public Scope(Scope parent) {
        this.parent = parent;
    }

    protected void addChildren(Scope scope) {
        this.childrens.add(scope);
    }

    protected void addSymbol(String name, Expr expr) {
        symbols.put(name, expr);
    }

    protected boolean existSymbol(String name) {
        if (parent == null) {
            return false;
        }
        System.out.println(symbols.containsKey(name));

        return parent.existSymbol(name) ? true : symbols.containsKey(name);
    }

    protected Scope getChildren() {
        return childrens.poll();
    }

    protected Map<String, Expr> getSymbols() {
        return symbols;
    }

    protected Scope getParent() {
        if (parent == null) {
            return null;
        }

        symbols.containsKey("asd");

        parent.childrens.add(this);

        return parent;
    }

    @Override
    public String toString() {
        return "Symbols: " + symbols + "\nChildrens: " + childrens + "\n";
    }
}
