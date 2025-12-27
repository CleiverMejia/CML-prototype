package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import parser.interfaces.Expr;

public class Frame {

    private static final Stack<Map<String, Expr>> table = new Stack<>();

    public static void createScope() {
        table.add(new HashMap<>());
    }

    public static void put(String name, Expr expr) {
        for (Map<String, Expr> scope : table) {
            if (scope.containsKey(name)) {
                scope.put(name, expr);
                return;
            }
        }

        table.get(table.size() - 1).put(name, expr);
    }

    public static void pop() {
        table.pop();
    }

    public static Expr get(String name) {
        for (Map<String, Expr> scope : table) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }

        throw new Error("Assignment not found");
    }

    public static String toStrings() {
        return table.toString();
    }
}
