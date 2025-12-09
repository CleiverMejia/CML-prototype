package parser.statements;

import parser.Block;
import parser.interfaces.Comp;
import parser.interfaces.Stmt;

public class IfStmt implements Stmt {

    Comp condition;
    Block body;

    public IfStmt(Comp condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    public Comp getCondition() {
        return condition;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "if(" + condition + ") {" + body + "}";
    }
}
