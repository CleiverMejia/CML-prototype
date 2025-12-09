package lexer;

import enums.TokenType;

public class Token {

    public TokenType type;
    public String string;
    public int value;

    public Token(TokenType type, int value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type, String string) {
        this.type = type;
        this.string = string;
    }

    @Override
    public String toString() {
        if (string == null) {
            return "[" + type.name() + ", " + value + "]";
        }

        return "[" + type.name() + ", " + string + "]";
    }
}
