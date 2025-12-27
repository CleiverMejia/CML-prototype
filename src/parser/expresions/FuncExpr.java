package parser.expresions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import parser.Block;
import parser.interfaces.Expr;

public class FuncExpr implements Expr {
    private final List<String> args;
    private final Block body;

    public FuncExpr(ArrayList<String> args, Block body) {
        this.args = args;
        this.body = body;
    }

    public FuncExpr(Block body, String... args) {
        this.args = new ArrayList<>(Arrays.asList(args));
        this.body = body;
    }

    public List<String> getArgs() {
        return args;
    }

    public Block getBody() {
        return body;
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
