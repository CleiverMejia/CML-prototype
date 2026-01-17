package parser.expresions;

import java.util.ArrayList;
import java.util.List;
import parser.Block;
import parser.interfaces.Expr;

public class FuncExpr implements Expr {
    private List<VarExpr> args = new ArrayList<>();
    private final Block body;
    private final String name;

    public FuncExpr(String name, ArrayList<VarExpr> args, Block body) {
        this.name = name;
        this.args = args;
        this.body = body;
    }

    public FuncExpr(String name, Block body, String... args) {
        this.name = name;
        this.body = body;

        for (String arg : args) {
            this.args.add(new VarExpr(arg));
        }
    }

    public String getName() {
        return name;
    }

    public List<VarExpr> getArgs() {
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
        return "Function<" + name + ">";
    }
}
