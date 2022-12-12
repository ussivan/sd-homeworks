package position;

import token.Token;
import token.Tokenizer;

public class EndPosition implements Position {
    @Override
    public Token createToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("EOF");
    }

    @Override
    public void nextToken(Tokenizer tokenizer) {
        throw new UnsupportedOperationException("EOF");
    }
}
