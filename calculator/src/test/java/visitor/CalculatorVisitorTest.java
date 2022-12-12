package visitor;

import org.junit.jupiter.api.Test;
import token.Tokenizer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorVisitorTest {

    CalculatorVisitor calculatorVisitor = new CalculatorVisitor();
    ParserVisitor parserVisitor = new ParserVisitor();

    @Test
    public void testSimple() throws IOException, ParseException {
        assertEquals(3, evaluate("3"));
        assertEquals(200, evaluate("200"));
    }

    @Test
    public void testBinary() throws IOException, ParseException {
        assertEquals(4, evaluate("2 + 2"));
        assertEquals(8, evaluate("64 / 8"));
    }

    @Test
    public void testComplex() throws IOException, ParseException {
        assertEquals(3, evaluate("3 - 3 + 3"));
        assertEquals(3, evaluate("3 - (8 - 8)"));
        assertEquals(4, evaluate("(30 + 2) / 8"));
        assertEquals(1279, evaluate("(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 2"));
    }

    private int evaluate(String input) throws IOException, ParseException {
        return calculatorVisitor.calc(parserVisitor.toPolishNotation(new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).getTokens()));
    }

}
