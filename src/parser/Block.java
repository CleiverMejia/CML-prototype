package parser;

import java.util.ArrayList;
import java.util.Arrays;
import parser.interfaces.Stmt;

public class Block extends ArrayList<Stmt> {

    public Block(Stmt... stmts) {
        addAll(Arrays.asList(stmts));
    }
}
