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

    private Token peek() {
        return tokens.get(pos);
    }

    public void run() {
        mainBlock = getBlock();
    }

    private Block getBlock() {
        Block block = new Block();

        while (pos < tokens.size()) {
            Token token = peek();

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

            if (tokenType == TokenType.SEMICOLON || tokenType == TokenType.RPARENT) {
                break;
            }

            if (tokenType != TokenType.PLUS
                    && tokenType != TokenType.MINUS
                    && tokenType != TokenType.MUL
                    && tokenType != TokenType.DIV) {
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
        Comp condition = null;
        Block body = null;

        pos++;
        while (pos < tokens.size() && tokens.get(pos).type != TokenType.RBRACE) {
            Token token = peek();

            switch (token.type) {
                case EQUAL -> {
                    Token tokenVal1 = tokens.get(pos - 1);
                    Token tokenVal2 = tokens.get(++pos);

                    Expr dataType1 = getDataType(tokenVal1);
                    Expr dataType2 = getDataType(tokenVal2);

                    condition = new EqualComp(dataType1, dataType2);
                }
                case LBRACE -> {
                    body = getBlock();
                    pos--;
                }
                default -> {
                }
            }

            pos++;
        }

        return new IfStmt(condition, body);
    }
}
