package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class PrintVisitor implements TokenVisitor {

    private final PrintStream out;

    public PrintVisitor(OutputStream out) {
        this.out = new PrintStream(out);
    }

    @Override
    public void visit(NumberToken numberToken) {
        print(numberToken);
    }


    @Override
    public void visit(Brace brace) {
        throw new IllegalArgumentException("Braces in polish notation not allowed");
    }

    @Override
    public void visit(Operation operation) {
        print(operation);
    }

    private void print(Token token) {
        out.print(token + " ");
    }

    public void printPolish(List<Token> tokens) {
        tokens.forEach(t -> t.accept(this));
        out.println();
    }
}
