package visitor;

import token.Brace;
import token.NumberToken;
import token.Operation;

public interface TokenVisitor {
    void visit(NumberToken numberToken);

    void visit(Brace brace);

    void visit(Operation operation);
}
