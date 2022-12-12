package token;

import visitor.TokenVisitor;

public abstract class Operation implements Token {
    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public abstract int getPriority();

    public abstract NumberToken apply(NumberToken a, NumberToken b);

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Operation)) {
            return false;
        }
        return toString().equals(obj.toString());
    }
}
