package semantic;

import parser.interfaces.Expr;

public class SymbolTable {

    private static Scope current = null;

    protected static void createScope() {
        current = new Scope(current);
    }

    protected static void closeScope() {
        if (current.getParent() != null) {
            current = current.getParent();
        }
    }

    protected static void openScope() {
        current = current.getChildren();
    }

    protected static void put(String name, Expr expr) {
        if (current != null && !current.existSymbol(name)) {
            current.addSymbol(name, expr);
        }
    }

    protected static String toStrings() {
        return current.toString();
    }
}
