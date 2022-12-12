package position;

import token.Token;
import token.Tokenizer;

import java.io.IOException;

public interface Position {
    Token createToken(Tokenizer tokenizer) throws IOException;

    void nextToken(Tokenizer tokenizer);
}
