package lexer;

import enums.TokenType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexer {

    private Scanner doc;
    private ArrayList<Token> tokens = new ArrayList<>();

    public Lexer(String fileName) throws Exception {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                throw new Exception("File not found");
            }

            doc = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (this.doc.hasNextLine()) {
            next();
        }

        //tokens.forEach(row -> System.out.println(row));
        this.doc.close();
    }

    public void next() {
        String src = this.doc.nextLine();
        int positionLine = 0;

        while (positionLine < src.length()) {
            char character = src.charAt(positionLine);

            if (Character.isWhitespace(character)) { // White space
                positionLine++;
                continue;
            }

            if (Character.isDigit(character)) { // Digits
                int start = positionLine;
                while (positionLine < src.length() && Character.isDigit(src.charAt(positionLine))) {
                    positionLine++;
                }

                int value = Integer.parseInt(src.substring(start, positionLine));
                tokens.add(new Token(TokenType.NUMBER, value));
                continue;
            }

            if (character == '"') { // Strings
                int start = ++positionLine;
                do {
                    positionLine++;
                } while (positionLine < src.length() && src.charAt(positionLine) != '"');

                String string = src.substring(start, positionLine++);
                tokens.add(new Token(TokenType.STRING, string));
                continue;
            }

            if (Character.isLetter(character)) { // Letters
                int start = positionLine;
                while (positionLine < src.length() && Character.isLetter(src.charAt(positionLine))) {
                    positionLine++;
                }

                String string = src.substring(start, positionLine);

                if (string.equalsIgnoreCase(TokenType.PRINT.name())) {
                    tokens.add(new Token(TokenType.PRINT, string));
                    continue;
                }

                if (string.equalsIgnoreCase(TokenType.IF.name())) {
                    tokens.add(new Token(TokenType.IF, string));
                    continue;
                }

                tokens.add(new Token(TokenType.IDENT, string));
                continue;
            }

            switch (character) {
                case '(' ->
                    tokens.add(new Token(TokenType.LPARENT, "("));
                case ')' ->
                    tokens.add(new Token(TokenType.RPARENT, ")"));
                case '+' ->
                    tokens.add(new Token(TokenType.PLUS, "+"));
                case '-' ->
                    tokens.add(new Token(TokenType.MINUS, "-"));
                case '*' ->
                    tokens.add(new Token(TokenType.MUL, "*"));
                case '/' ->
                    tokens.add(new Token(TokenType.DIV, "/"));
                case ';' ->
                    tokens.add(new Token(TokenType.SEMICOLON, ";"));
                case '{' ->
                    tokens.add(new Token(TokenType.LBRACE, "{"));
                case '}' ->
                    tokens.add(new Token(TokenType.RBRACE, "}"));
                case '<' -> {
                    if (src.charAt(positionLine + 1) == '=') {
                        tokens.add(new Token(TokenType.LESSEQUAL, "<="));
                        positionLine++;
                        break;
                    }

                    tokens.add(new Token(TokenType.LESS, "<"));
                }
                case '>' -> {
                    if (src.charAt(positionLine + 1) == '=') {
                        tokens.add(new Token(TokenType.GREATEREQUAL, ">="));
                        positionLine++;
                        break;
                    }

                    tokens.add(new Token(TokenType.GREATER, ">"));
                }
                case '=' -> {
                    if (src.charAt(positionLine + 1) == '=') {
                        tokens.add(new Token(TokenType.EQUAL, "=="));
                        positionLine++;
                        break;
                    }

                    tokens.add(new Token(TokenType.ASSIGN, "="));
                }
                default -> {
                    System.out.println(src);
                    System.out.println(positionLine);
                    System.out.println(character);
                    throw new AssertionError();
                }
            }

            positionLine++;
        }
    }

    public ArrayList<Token> getTokens() {
        return this.tokens;
    }
}
