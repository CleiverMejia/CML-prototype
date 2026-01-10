package parser;

import enums.TokenType;
import java.util.ArrayList;
import lexer.Token;
import lib.*;
import parser.expresions.*;
import parser.expresions.comparations.*;
import parser.expresions.operations.*;
import parser.interfaces.*;
import parser.statements.*;

public class Parser {

    private final Block mainBlock = new Block(new Print(), new Sqrt(), new Http());
    private final ArrayList<Token> tokens;
    private int pos = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Block getMain() {
        return mainBlock;
    }

    public void run() {
        mainBlock.addAll(getBlock());
    }

    private Block getBlock() {
        Block block = new Block();

        while (pos < tokens.size()) {
            Token token = tokens.get(pos);

            switch (token.type) {
                case RBRACE -> {
                    return block;
                }
                case IDENT -> {
                    if (tokens.get(pos + 1).type == TokenType.LPARENT) {
                        block.add(call());
                    }
                }
                case ASSIGN ->
                    block.add(assign());
                case IF ->
                    block.add(ifL());
                case WHILE ->
                    block.add(whileL());
                case FUNCTION ->
                    block.add(function());
                case RETURN ->
                    block.add(returnL());
                case CLASS ->
                    block.add(classL());
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

        if (tokenValue.type == TokenType.BOOL) {
            return new BoolExpr(tokenValue.string.equals("true"));
        }

        if (tokenValue.type == TokenType.IDENT) {
            String name = tokenValue.string;

            if (tokens.get(pos + 1).type == TokenType.LPARENT) {
                pos++;
                ArrayList<Expr> args = new ArrayList<>();

                while (tokens.get(pos).type != TokenType.RPARENT) {
                    args.add(getExpr());
                }

                return new CallExpr(name, args);
            }

            return new VarExpr(name);
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
                    || tokenType == TokenType.LBRACE
                    || tokenType == TokenType.COMMA) {
                break;
            }

            if (tokenType != TokenType.PLUS
                    && tokenType != TokenType.MINUS
                    && tokenType != TokenType.MUL
                    && tokenType != TokenType.DIV
                    && tokenType != TokenType.EQUAL
                    && tokenType != TokenType.GREATER
                    && tokenType != TokenType.LESS
                    && tokenType != TokenType.GREATEREQUAL
                    && tokenType != TokenType.LESSEQUAL
                    && tokenType != TokenType.NOTEQUAL
                    && tokenType != TokenType.AND
                    && tokenType != TokenType.OR
                    && tokenType != TokenType.NOT) {
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
                case EQUAL ->
                    expr = new EqualComp(expr, rightExpr);
                case GREATER ->
                    expr = new GreaterComp(expr, rightExpr);
                case LESS ->
                    expr = new LessComp(expr, rightExpr);
                case GREATEREQUAL ->
                    expr = new GreaterEqualComp(expr, rightExpr);
                case LESSEQUAL ->
                    expr = new LessEqualComp(expr, rightExpr);
                case NOTEQUAL ->
                    expr = new NotEqualComp(expr, rightExpr);
                case AND ->
                    expr = new AndComp(expr, rightExpr);
                case OR ->
                    expr = new OrComp(expr, rightExpr);
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

    private IfStmt ifL() {
        Comp condition = (Comp) getExpr();
        Block body = getBlock();

        if (pos + 1 < tokens.size() && tokens.get(pos + 1).type == TokenType.ELSE) {
            pos++;
            Block elseBody = getBlock();

            return new IfStmt(condition, body, elseBody);
        }

        return new IfStmt(condition, body);
    }

    private WhileStmt whileL() {
        Comp condition = (Comp) getExpr();
        Block body = getBlock();

        return new WhileStmt(condition, body);
    }

    private FunctionStmt function() {
        String funcName = tokens.get(++pos).string;
        ArrayList<String> arg = new ArrayList<>();

        if (tokens.get(pos + 1).type == TokenType.LPARENT) {
            pos += 2;

            while (pos < tokens.size() && tokens.get(pos).type != TokenType.RPARENT) {
                if (tokens.get(pos).type == TokenType.COMMA) {
                    pos++;
                    continue;
                }

                arg.add(tokens.get(pos).string);
                pos++;
            }
        }

        Block body = getBlock();

        return new FunctionStmt(new FuncExpr(funcName, arg, body));
    }

    private CallStmt call() {
        String funcName = tokens.get(pos++).string;
        ArrayList<Expr> args = new ArrayList<>();

        while (tokens.get(pos).type != TokenType.RPARENT) {
            args.add(getExpr());
        }

        return new CallStmt(funcName, args);
    }

    private ReturnStmt returnL() {
        Expr result = getExpr();

        return new ReturnStmt(result);
    }

    private ClassStmt classL() {
        String className = tokens.get(pos++).string;
        Block body = getBlock();

        return new ClassStmt(new ClassExpr(className, body));
    }
}
