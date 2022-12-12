package visitor;

import org.junit.jupiter.api.Test;
import token.*;
import token.NumberToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserVisitorTest {

    ParserVisitor parserVisitor = new ParserVisitor();

    @Test
    public void testSimple() throws IOException, ParseException {
        assertEquals(List.of(new NumberToken(30)), polishNotation("30"));
    }

    @Test
    public void testBinary() throws IOException, ParseException {
        assertEquals(List.of(new NumberToken(3), new NumberToken(2), new Plus()), polishNotation("3 + 2"));
        assertEquals(List.of(new NumberToken(200), new NumberToken(20), new Divide()), polishNotation("200 / 20"));
    }

    @Test
    public void testBraceBinary() throws IOException, ParseException {
        assertEquals(List.of(
                        new NumberToken(30),
                        new NumberToken(2),
                        new Plus(),
                        new NumberToken(8),
                        new Divide()),
                polishNotation("(30 + 2) / 8"));
    }

    @Test
    public void testOperations() throws IOException, ParseException {
        assertEquals(List.of(
                        new NumberToken(2),
                        new NumberToken(2),
                        new Multiply(),
                        new NumberToken(2),
                        new Plus()),
                polishNotation("2 * 2 + 2"));
        assertEquals(List.of(
                        new NumberToken(2),
                        new NumberToken(2),
                        new NumberToken(2),
                        new Multiply(),
                        new Plus()),
                polishNotation("2 + 2 * 2"));
        assertEquals(List.of(
                        new NumberToken(2),
                        new NumberToken(2),
                        new Minus(),
                        new NumberToken(2),
                        new Minus()),
                polishNotation("2 - 2 - 2"));
        assertEquals(List.of(
                        new NumberToken(2),
                        new NumberToken(2),
                        new NumberToken(2),
                        new Minus(),
                        new Minus()),
                polishNotation("2 - (2 - 2)"));
    }

    private List<Token> polishNotation(String input) throws IOException, ParseException {
        return parserVisitor.toPolishNotation(new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).getTokens());
    }

}
