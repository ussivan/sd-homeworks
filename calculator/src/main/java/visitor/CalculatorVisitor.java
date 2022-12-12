package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

public class CalculatorVisitor implements TokenVisitor {

    private Deque<NumberToken> stack;

    @Override
    public void visit(NumberToken numberToken) {
        stack.push(numberToken);
    }

    @Override
    public void visit(Brace brace) {
        throw new IllegalArgumentException("Braces in polish notation not allowed");
    }

    @Override
    public void visit(Operation operation) {
        try {
            NumberToken a = stack.pop();
            NumberToken b = stack.pop();
            stack.push(operation.apply(b, a));
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("Need more operands");
        }
    }

    public int calc(List<Token> tokens) {
        stack = new ArrayDeque<>();
        tokens.forEach(t -> t.accept(this));
        if (stack.size() != 1) {
            throw new IllegalStateException("Expression not finished");
        }
        return stack.pop().value();
    }
}
