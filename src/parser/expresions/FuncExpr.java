package parser.expresions;

import interpreter.Interpreter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import parser.Block;
import parser.interfaces.Expr;

public class FuncExpr implements Expr {
    private final List<String> args;
    private final Block body;
    private final String name;

    public FuncExpr(String name, ArrayList<String> args, Block body) {
        this.name = name;
        this.args = args;
        this.body = body;
    }

    public FuncExpr(String name, Block body, String... args) {
        this.name = name;
        this.args = new ArrayList<>(Arrays.asList(args));
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgs() {
        return args;
    }

    public Block getBody() {
        return body;
    }

    public Expr getReturn() {
        Interpreter.run(body);
        return null;
    }

    @Override
    public Expr get() {
        return null;
    }

    @Override
    public String toString() {
        return "Function!";
    }
}
