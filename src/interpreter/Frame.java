package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import parser.interfaces.Expr;

public class Frame {

    private static final Stack<Map<String, Expr>> table = new Stack<>();
    private static final Stack<Map<String, Expr>> args = new Stack<>();
    private static Expr returnStack = null;

    public static void createScope() {
        table.add(new HashMap<>());
    }

    public static void createArgScope() {
        args.add(new HashMap<>());
    }

    public static void put(String name, Expr expr) {
        for (int i = args.size() - 1; i >= 0; i--) {
            Map<String, Expr> scope = args.get(i);

            if (scope.containsKey(name)) {
                scope.put(name, expr);
                return;
            }
        }

        for (Map<String, Expr> scope : table) {
            if (scope.containsKey(name)) {
                scope.put(name, expr);
                return;
            }
        }

        table.get(table.size() - 1).put(name, expr);
    }

    public static void putArg(String name, Expr expr) {
        args.get(args.size() - 1).put(name, expr);
    }

    public static void pop() {
        table.pop();
    }

    public static void popArg() {
        if (!args.isEmpty()) {
            args.pop();
        }
    }

    public static Expr get(String name) {
        for (int i = args.size() - 1; i >= 0; i--) {
            Map<String, Expr> scope = args.get(i);

            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }

        for (Map<String, Expr> scope : table) {
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }

        throw new Error("Assignment " + name + " not found");
    }

    public static void setReturn(Expr expr) {
        returnStack = expr;
    }

    public static Expr getReturn() {
        return returnStack;
    }

    public static String getArgs() {
        return args.toString();
    }

    public static String getTable() {
        return table.toString();
    }
}
