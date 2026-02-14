package interpreter;

import java.util.Stack;
import parser.expresions.CallExpr;
import parser.expresions.FuncExpr;
import parser.expresions.ObjExpr;
import parser.interfaces.Expr;

public class CallStack {

    private static final Stack<Frame> callStack = new Stack<>();

    static {
        createFrame();
    }

    public static void createFrame() {
        callStack.push(new Frame(null, new Scope(null)));
    }

    public static void createFrame(ObjExpr objExpr) {
        callStack.push(new Frame(objExpr, new Scope(null)));
    }

    public static void closeFrame() {
        callStack.pop();
    }

    public static void createScope() {
        Frame f = callStack.peek();

        f.setScope(new Scope(f.getScope()));
    }

    public static void closeScope() {
        Frame f = callStack.peek();

        f.setScope(f.getScope().getParent());
    }

    public static void define(String name, Expr expr) {
        Scope s = callStack.firstElement().getScope();

        while (s != null) {
            if (s.contains(name)) {
                s.define(name, expr);
                return;
            }

            s = s.getParent();
        }

        callStack.peek().getScope().define(name, expr);
    }

    public static void setReturn(Expr expr) {
        callStack.get(callStack.size()-2).setReturnValue(expr);
    }

    public static Stack<Frame> getCallStack() {
        return callStack;
    }

    public static Scope currentScope() {
        return callStack.peek().getScope();
    }

    public static Expr getReturn() {
        return callStack.peek().getReturnValue();
    }

    public static Expr resolve(String name) {
        for (int i = callStack.size() - 1; i > 0; i--) {
            Frame f = callStack.get(i);

            if ("this".equals(name)) {
                if (f.getThisObj() != null) {
                    return f.getThisObj();
                }

                continue;
            }

            Scope s = f.getScope();
            while (s != null) {
                if (s.contains(name)) {
                    return s.get(name);
                }

                s = s.getParent();
            }
        }

        return resolveGlobal(name);
    }

    public static Expr resolveArg(String name) {
        Frame f = callStack.peek();
        Scope s = f.getScope().getParent();
        while (s != null) {
            if (s.contains(name)) {
                return s.get(name);
            }

            s = s.getParent();
        }

        return resolveGlobal(name);
    }

    public static Expr resolveGlobal(String name) {
        Scope s = callStack.firstElement().getScope();
        while (s != null) {
            if (s.contains(name)) {
                return s.get(name);
            }

            s = s.getParent();
        }

        throw new RuntimeException(String.format("Symbol %s is not defined", name));
    }

    public static void callMethod(ObjExpr obj, CallExpr call) {
        FuncExpr method = (FuncExpr) obj.getDeclaration(call.getName());

        Frame frame = new Frame(obj, method.parentScope);
        callStack.push(frame);

        Interpreter.run(method.getBody());

        callStack.pop();
    }

}
