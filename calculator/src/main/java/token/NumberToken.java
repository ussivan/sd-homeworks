package token;

import visitor.TokenVisitor;

public record NumberToken(int value) implements Token {

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NumberToken)) {
            return false;
        }
        return value == ((NumberToken) obj).value;
    }
}
