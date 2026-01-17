package interpreter;

import enums.SymbolKind;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import parser.expresions.ObjExpr;
import parser.interfaces.Expr;

public class Frame {

    private static final Stack<Map<String, Expr>> table = new Stack<>();
    private static Expr returnStack = null;
    private static Map<String, Expr> self = null;

    static {
        createScope();
    }

    public static void createScope() {
        table.add(new HashMap<>());
    }

    public static void put(String name, Expr expr, SymbolKind kind) {
        if (kind != SymbolKind.PARAM) {
            String field = name;
            if (name.contains(".")) {
                field = getField(name);
            }

            if (self != null) {
                if (self.containsKey(field)) {
                    self.put(field, expr);
                    return;
                }
            }

            for (int i = table.size() - 1; i >= 0; i--) {
                Map<String, Expr> scope = table.get(i);

                if (scope.containsKey(name)) {
                    scope.put(name, expr);
                    return;
                }
            }

            if (name.contains(".")) {
                throw new Error(name + " is not defined");
            }
        }

        table.get(table.size() - 1).put(name, expr);
    }

    public static void pop() {
        table.pop();
    }

    public static void setSelf(Map<String, Expr> scope) {
        self = scope;
    }

    public static void closeSelf() {
        self = null;
    }

    public static Expr get(String name) {
        String field = name;
        if (name.contains(".")) {
            field = getField(name);
        }

        if (self != null) {
            if (self.containsKey(field)) {
                return self.get(field);
            }
        }

        for (int i = table.size() - 1; i >= 0; i--) {
            Map<String, Expr> scope = table.get(i);

            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }

        throw new Error("Assignment " + name + " not found");
    }

    private static String getField(String name) {
        String[] sep = name.split("\\.");

        String objName = sep[0];
        String field = sep[1];

        ObjExpr obj = (ObjExpr) get(objName);
        self = obj.getDeclarations();

        return field;
    }

    public static void setReturn(Expr expr) {
        Frame.returnStack = expr;
    }

    public static Expr getReturn() {
        return returnStack;
    }

    public static void getTable() {
        /* table.forEach(scope -> {
            scope.forEach((k, v) -> System.out.println(k + " = " + v));
        }); */
        System.out.println(table);

        System.out.println();
    }

    public static void getSelf() {
        System.out.println(self);
    }
}
