package parser;

import java.util.ArrayList;
import java.util.Arrays;
import parser.interfaces.Stmt;

public class Block extends ArrayList<Stmt> {

    public Block(Stmt... stmts) {
        addAll(Arrays.asList(stmts));
    }

    @Override
    public String toString() {
        String text = "";

        for (int i = 0; i < size(); i++) {
            text += get(i)  + "\n";
        }

        return text;
    }
}
