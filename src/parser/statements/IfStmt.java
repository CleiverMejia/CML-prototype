package parser.statements;

import parser.Block;
import parser.interfaces.Comp;
import parser.interfaces.Stmt;

public class IfStmt implements Stmt {

    Comp condition;
    Block body;
    Block elseBody = null;

    public IfStmt(Comp condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    public IfStmt(Comp condition, Block body, Block elseBody) {
        this.condition = condition;
        this.body = body;
        this.elseBody = elseBody;
    }

    public Comp getCondition() {
        return condition;
    }

    public Block getBody() {
        return body;
    }

    public Block getElse() {
        return elseBody;
    }

    @Override
    public String toString() {
        return "if(" + condition + ") {" + body + "}";
    }
}
