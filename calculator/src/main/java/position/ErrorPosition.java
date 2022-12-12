package position;

import token.Token;
import token.Tokenizer;

public record ErrorPosition(String message) implements Position {

    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("Cant create token");

    }

    @Override
    public void nextToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("Error");
    }
}
