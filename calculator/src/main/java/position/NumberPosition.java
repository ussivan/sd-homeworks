package position;

import token.NumberToken;
import token.Token;
import token.Tokenizer;

import java.io.IOException;

public class NumberPosition implements Position {
    @Override
    public Token createToken(Tokenizer tokenizer) throws IOException {
        return new NumberToken(tokenizer.parseNumber());
    }

    @Override
    public void nextToken(Tokenizer tokenizer) {
        if (tokenizer.isOperationOrBrace()) {
            tokenizer.setState(new StartPosition());
        } else if (tokenizer.isEOF()) {
            tokenizer.setState(new EndPosition());
        } else {
            tokenizer.setState(new ErrorPosition("Expected eof"));
        }
    }
}
