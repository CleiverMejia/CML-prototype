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

    public void define(int i, Expr expr) {
        while (i > list.size() - 1) {
            list.add(null);
        }

        list.set(i, expr);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
