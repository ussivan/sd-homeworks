package visitor;

import token.*;
import token.NumberToken;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParserVisitor implements TokenVisitor {

    private List<Token> tokensPolish;
    private Deque<Token> deque;

    @Override
    public void visit(NumberToken numberToken) {
        tokensPolish.add(numberToken);
    }

    @Override
    public void visit(Brace brace) {
        if (brace.toString().equals("(")) {
            deque.push(brace);
        } else {
            while (!deque.isEmpty() && !deque.peek().toString().equals("(")) {
                tokensPolish.add(deque.pop());
            }
            if (deque.isEmpty()) {
                throw new IllegalStateException("Mismatched parentheses");
            }
            deque.pop();
        }
    }

    @Override
    public void visit(Operation operation) {
        while (!deque.isEmpty() && operation.getPriority() <= (deque.peek() instanceof Operation ? ((Operation) deque.peek()).getPriority() : 0)) {
            tokensPolish.add(deque.pop());
        }
        deque.push(operation);
    }

    public List<Token> toPolishNotation(List<Token> tokens) {
        tokensPolish = new ArrayList<>();
        deque = new ArrayDeque<>();
        tokens.forEach(t -> t.accept(this));
        while (!deque.isEmpty()) {
            Token token = deque.pop();
            if (!(token instanceof Operation)) {
                throw new IllegalStateException("Not operation token");
            }
            tokensPolish.add(token);
        }
        return tokensPolish;
    }
}
