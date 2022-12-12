package token;

public class Multiply extends Operation {
    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public NumberToken apply(NumberToken a, NumberToken b) {
        return new NumberToken(a.value() * b.value());
    }

    @Override
    public String toString() {
        return "*";
    }
}
