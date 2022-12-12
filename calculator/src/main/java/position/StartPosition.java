package position;

import token.*;

import java.io.IOException;

public class StartPosition implements Position {
    @Override
    public Token createToken(Tokenizer tokenizer) throws IOException {
        char c = tokenizer.getCurChar();
        tokenizer.nextChar();
        return switch (c) {
            case '+' -> new Plus();
            case '-' -> new Minus();
            case '*' -> new Multiply();
            case '/' -> new Divide();
            case '(' -> new OpenBrace();
            case ')' -> new CloseBrace();
            default -> null;
        };
    }

    @Override
    public void nextToken(Tokenizer tokenizer) {
        if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartPosition());
        } else if (tokenizer.isDigit()) {
            tokenizer.setState(new NumberPosition());
        } else if (tokenizer.isEOF()) {
            tokenizer.setState(new EndPosition());
        } else {
            tokenizer.setState(new ErrorPosition("Expected eof"));
        }
    }
}
