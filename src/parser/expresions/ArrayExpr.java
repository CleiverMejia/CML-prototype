package parser.expresions;

import java.util.ArrayList;
import java.util.List;
import parser.interfaces.Expr;

public class ArrayExpr implements Expr {
    private List<Expr> list = new ArrayList<>();

    public ArrayExpr(ArrayList<Expr> list) {
        this.list = list;
    }

    @Override
    public Expr get() {
        return null;
    }

    public Expr get(int i) {
        return list.get(i);
    }

    public void set(int i, Expr expr) {
        list.set(i, expr);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
