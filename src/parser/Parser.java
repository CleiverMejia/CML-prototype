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

    private final Block mainBlock = new Core();
    private final ArrayList<Token> tokens;
    private Token tok;
    private int pos = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.tok = !tokens.isEmpty() ? tokens.get(0) : null;
    }

    public Block getMain() {
        return mainBlock;
    }

    public void run() {
        mainBlock.addAll(getBlock());
    }

    private Token peek() {
        if (pos < tokens.size() - 1) {
            tok = tokens.get(++pos);
            return tokens.get(pos - 1);
        }

        return tokens.get(pos++);
    }

    private Token consume(TokenType tokenType) {
        if (tok.type == tokenType) {
            return peek();
        }

        throw new Error(tokenType + " it's not the same type as " + tok.type);
    }

    private Block getBlock() {
        Block block = new Block();

        while (pos < tokens.size()) {
            switch (tok.type) {
                case RBRACE -> {
                    consume(TokenType.RBRACE);
                    return block;
                }
                case IDENT ->
                    block.add(ident());
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
                case IMPORT ->
                    block.add(importL());
                case EOF ->
                    consume(TokenType.EOF);
                default -> {
                    System.out.printf("%s %s", tok, tokens.get(pos - 1));
                }
            }
        }

        return block;
    }

    public Expr getDataType() {
        if (tok.type == TokenType.NUMBER) {
            return new NumberExpr(consume(TokenType.NUMBER).value);
        }

        if (tok.type == TokenType.STRING) {
            return new StringExpr(consume(TokenType.STRING).string);
        }

        if (tok.type == TokenType.BOOL) {
            return new BoolExpr(consume(TokenType.BOOL).string.equals("true"));
        }

        if (tok.type == TokenType.IDENT) {
            String name = consume(TokenType.IDENT).string;
            Expr var = new VarExpr(name);

            switch (tok.type) {
                case LPARENT -> {
                    consume(TokenType.LPARENT);
                    ArrayList<Expr> args = new ArrayList<>();

                    while (tok.type != TokenType.SEMICOLON && tok.type != TokenType.EOF) {
                        args.add(getExpr());
                    }

                    return new CallExpr(var, args);
                }
                case DOT -> {
                    consume(TokenType.DOT);
                    String fieldName = consume(TokenType.IDENT).string;

                    FieldExpr field = new FieldExpr(new VarExpr(name), new VarExpr(fieldName));

                    if (tok.type == TokenType.LPARENT) {
                        consume(TokenType.LPARENT);
                        ArrayList<Expr> args = new ArrayList<>();

                        while (tok.type != TokenType.SEMICOLON && tok.type != TokenType.EOF) {
                            args.add(getExpr());
                        }

                        return new CallExpr(field, args);
                    }

                    return field;
                }
                case LBRACKET -> {
                    consume(TokenType.LBRACKET);

                    int ind = (int) consume(TokenType.NUMBER).value;
                    consume(TokenType.RBRACKET);

                    return new AccessExpr(name, ind);
                }
                /* case COMMA -> {
                    ArrayList<VarExpr> arg = new ArrayList<>();
                    while (tok.type != TokenType.SEMICOLON && tok.type != TokenType.EOF && tok.type != TokenType.ARROW) {
                        arg.add((VarExpr) getExpr());
                    }

                    consume(TokenType.ARROW);
                    consume(TokenType.LBRACE);

                    Block body = getBlock();

                    return new FuncExpr("Expr", arg, body);
                } */
                default -> {
                    return new VarExpr(name);
                }
            }
        }

        if (tok.type == TokenType.LBRACKET) {
            consume(TokenType.LBRACKET);

            ArrayList<Expr> exprs = new ArrayList<>();

            while (tok.type != TokenType.SEMICOLON && tok.type != TokenType.EOF) {
                exprs.add(getExpr());
            }

            return new ArrayExpr(exprs);
        }

        if (tok.type == TokenType.FUNCTION) {
            consume(TokenType.FUNCTION);

            ArrayList<VarExpr> arg = new ArrayList<>();

            if (tok.type == TokenType.LPARENT) {
                consume(TokenType.LPARENT);

                while (pos < tokens.size() && tok.type != TokenType.RPARENT) {
                    if (tok.type == TokenType.COMMA) {
                        consume(TokenType.COMMA);
                        continue;
                    }

                    String argName = consume(TokenType.IDENT).string;
                    arg.add(new VarExpr(argName));
                }

                consume(TokenType.RPARENT);
                consume(TokenType.LBRACE);
            }

            Block body = getBlock();

            return new FuncExpr("", arg, body);
        }

        return null;
    }

    private Expr getExpr() {
        Expr expr = null;

        while (pos < tokens.size()) {
            TokenType tokenType = tok.type;

            if (tokenType == TokenType.LPARENT) {
                consume(TokenType.LPARENT);
                expr = getExpr();
                continue;
            }

            if (tokenType == TokenType.SEMICOLON
                    || tokenType == TokenType.RPARENT
                    || tokenType == TokenType.LBRACE
                    || tokenType == TokenType.COMMA
                    || tokenType == TokenType.EOF
                    || tokenType == TokenType.RBRACKET) {
                consume(tokenType);
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
                Expr dataType = getDataType();
                expr = dataType;

                if (tok.type == TokenType.IDENT) {
                    throw new Error("Close the expression with a semicolon");
                }
                continue;
            }

            tokenType = consume(tokenType).type;
            Expr rightExpr;
            if (tok.type == TokenType.LPARENT) {
                consume(TokenType.LPARENT);
                rightExpr = getExpr();
            } else {
                rightExpr = getDataType();

                if (tok.type == TokenType.IDENT) {
                    throw new Error("Close the expression with a semicolon");
                }
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

    private Stmt ident() {
        String name = consume(TokenType.IDENT).string;
        Expr var = new VarExpr(name);

        if (tok.type == TokenType.DOT) {
            consume(TokenType.DOT);

            String field = consume(TokenType.IDENT).string;
            var = new FieldExpr(new VarExpr(name), new VarExpr(field));
        }

        if (tok.type == TokenType.LBRACKET) {
            consume(TokenType.LBRACKET);

            int ind = (int) consume(TokenType.NUMBER).value;
            consume(TokenType.RBRACKET);

            var = new AccessExpr(name, ind);
        }

        if (tok.type == TokenType.ASSIGN) {
            consume(TokenType.ASSIGN);

            if (tok.type == TokenType.NEW) {
                consume(TokenType.NEW);
                return instance(name);
            }

            return assign(var);
        }

        if (tok.type == TokenType.LPARENT) {
            consume(TokenType.LPARENT);
            return call(var);
        }

        throw new Error("Misused identifier");
    }

    private AssignStmt assign(Expr name) {
        Expr expr = getExpr();

        return new AssignStmt(name, expr);
    }

    private IfStmt ifL() {
        consume(TokenType.IF);
        Comp condition = (Comp) getExpr();
        Block body = getBlock();

        if (pos < tokens.size() && tok.type == TokenType.ELSE) {
            consume(TokenType.ELSE);
            consume(TokenType.LBRACE);
            Block elseBody = getBlock();

            return new IfStmt(condition, body, elseBody);
        }

        return new IfStmt(condition, body);
    }

    private WhileStmt whileL() {
        consume(TokenType.WHILE);
        Comp condition = (Comp) getExpr();
        Block body = getBlock();

        return new WhileStmt(condition, body);
    }

    private FunctionStmt function() {
        consume(TokenType.FUNCTION);
        String funcName = consume(TokenType.IDENT).string;
        ArrayList<VarExpr> arg = new ArrayList<>();

        if (tok.type == TokenType.LPARENT) {
            consume(TokenType.LPARENT);

            while (pos < tokens.size() && tok.type != TokenType.RPARENT) {
                if (tok.type == TokenType.COMMA) {
                    consume(TokenType.COMMA);
                    continue;
                }

                String argName = consume(TokenType.IDENT).string;
                arg.add(new VarExpr(argName));
            }

            consume(TokenType.RPARENT);
            consume(TokenType.LBRACE);
        }

        Block body = getBlock();

        return new FunctionStmt(new FuncExpr(funcName, arg, body));
    }

    private CallStmt call(Expr funcName) {
        ArrayList<Expr> args = new ArrayList<>();

        while (tok.type != TokenType.SEMICOLON && tok.type != TokenType.EOF) {
            if (tok.type == TokenType.EOF) {
                consume(TokenType.EOF);
                continue;
            }

            args.add(getExpr());
        }

        if (tok.type == TokenType.SEMICOLON) {
            consume(TokenType.SEMICOLON);
        }

        return new CallStmt(funcName, args);
    }

    private ReturnStmt returnL() {
        consume(TokenType.RETURN);
        Expr result = getExpr();

        return new ReturnStmt(result);
    }

    private ClassStmt classL() {
        consume(TokenType.CLASS);
        String className = consume(TokenType.IDENT).string;
        consume(TokenType.LBRACE);
        Block body = getBlock();

        return new ClassStmt(new ClassExpr(className, body));
    }

    private InstanceStmt instance(String name) {
        CallExpr classConstructor = (CallExpr) getDataType();
        String className = classConstructor.getName();
        classConstructor.setConstructor(name);

        if (tok.type == TokenType.SEMICOLON) {
            consume(TokenType.SEMICOLON);
        }

        return new InstanceStmt(new VarExpr(name), classConstructor, className);
    }

    private ImportStmt importL() {
        consume(TokenType.IMPORT);

        String path = consume(TokenType.STRING).string;

        if (tok.type == TokenType.SEMICOLON) {
            consume(TokenType.SEMICOLON);
        }

        return new ImportStmt(path);
    }
}
