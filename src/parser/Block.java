package parser;

import java.util.ArrayList;
import java.util.List;
import parser.interfaces.Stmt;

public class Block {

    private final List<Stmt> nodes = new ArrayList<>();

    public List<Stmt> getStmt() {
        return nodes;
    }

    public void add(Stmt statement) {
        nodes.add(statement);
    }

    @Override
    public String toString() {
        return nodes.toString();
    }
}
