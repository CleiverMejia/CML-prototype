package parser;

import enums.TokenType;
import java.util.ArrayList;
import java.util.List;
import lexer.Token;
import parser.expresions.DivExpr;
import parser.expresions.EqualComp;
import parser.expresions.MinusExpr;
import parser.expresions.MulExpr;
import parser.expresions.NumberExpr;
import parser.expresions.PlusExpr;
import parser.expresions.StringExpr;
import parser.expresions.VarExpr;
import parser.interfaces.Comp;
import parser.interfaces.Expr;
import parser.interfaces.Stmt;
import parser.statements.AssignStmt;
import parser.statements.IfStmt;
import parser.statements.PrintStmt;

public class Parser {

    private Block mainBlock = new Block();
    private final ArrayList<Token> tokens;
    private int pos = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> getStmts() {
        return mainBlock.getStmt();
    }

    public void run() {
        mainBlock = getBlock();
    }

    private Block getBlock() {
        Block block = new Block();

        while (pos < tokens.size()) {
            Token token = tokens.get(pos);

            switch (token.type) {
                case RBRACE -> {
                    return block;
                }
                case ASSIGN ->
                    block.add(assign());
                case PRINT ->
                    block.add(print());
                case IF ->
                    block.add(ifL());
                default -> {
                }
            }

            pos++;
        }

        return block;
    }

    public Expr getDataType(Token tokenValue) {
        if (tokenValue.type == TokenType.NUMBER) {
            return new NumberExpr(tokenValue.value);
        }

        if (tokenValue.type == TokenType.STRING) {
            return new StringExpr(tokenValue.string);
        }

        if (tokenValue.type == TokenType.IDENT) {
            return new VarExpr(tokenValue.string);
        }

        return null;
    }

    private Expr getExpr() {
        Expr expr = null;

        while (pos < tokens.size()) {
            Token tokenValue = tokens.get(++pos);
            TokenType tokenType = tokenValue.type;

            if (tokenType == TokenType.LPARENT) {
                expr = getExpr();
                continue;
            }

            if (tokenType == TokenType.SEMICOLON
                    || tokenType == TokenType.RPARENT
                    || tokenType == TokenType.LBRACE) {
                break;
            }

            if (tokenType != TokenType.PLUS
                    && tokenType != TokenType.MINUS
                    && tokenType != TokenType.MUL
                    && tokenType != TokenType.DIV
                    && tokenType != TokenType.EQUAL) {
                Expr dataType = getDataType(tokenValue);
                expr = dataType;
                continue;
            }

            Token nextToken = tokens.get(++pos);
            Expr rightExpr;
            if (nextToken.type == TokenType.LPARENT) {
                rightExpr = getExpr();
            } else {
                rightExpr = getDataType(nextToken);
            }

            switch (tokenType) {
                case PLUS ->
                    expr = new PlusExpr(expr, rightExpr);
                case MINUS ->
                    expr = new MinusExpr(expr, rightExpr);
                case MUL ->
                    expr = new MulExpr(expr, rightExpr);
                case DIV ->
                    expr = new DivExpr(expr, rightExpr);
                case EQUAL -> {
                    expr = new EqualComp(expr, rightExpr);
                }
                default -> {
                }
            }

        }

        return expr;
    }

    private AssignStmt assign() {
        String name = tokens.get(pos - 1).string;
        Expr expr = getExpr();

        return new AssignStmt(new VarExpr(name), expr);
    }

    private PrintStmt print() {
        Expr expr = getExpr();

        return new PrintStmt(expr);
    }

    public IfStmt ifL() {
        Comp condition = (Comp) getExpr();
        Block body = getBlock();

        return new IfStmt(condition, body);
    }
}
