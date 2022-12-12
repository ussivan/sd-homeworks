package visitor;

import org.junit.jupiter.api.Test;
import token.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class PrintVisitorTest {
    ParserVisitor parserVisitor = new ParserVisitor();

    @Test
    public void testSimple() throws IOException, ParseException {
        assertEquals("20", printRes("20"));
        assertEquals("200", printRes("200"));
    }

    @Test
    public void testBinary() throws IOException, ParseException {
        assertEquals("20 3 +", printRes("20 + 3"));
        assertEquals("200 50 /", printRes("200 / 50"));
    }

    @Test
    public void testCombined() throws IOException, ParseException {
        assertEquals("20 3 + 8 /", printRes("(20 + 3) / 8"));
        assertEquals("200 50 /", printRes("200 / 50"));
        assertEquals("3 3 3 * +", printRes("3 + 3 * 3"));
        assertEquals("3 3 - 3 -", printRes("3 - 3 - 3"));
        assertEquals("3 3 3 - -", printRes("3 - (3 - 3)"));
        assertEquals("23 10 + 5 * 3 32 5 + * 10 4 5 * - * - 8 3 / +",
                printRes("(23 + 10) * 5 - 3 * (32 + 5) * (10 - 4 * 5) + 8 / 3"));
    }

    private String printRes(String input) throws IOException, ParseException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new PrintVisitor(out).printPolish(parserVisitor.toPolishNotation(new Tokenizer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))).getTokens()));
        return out.toString().stripTrailing();
    }

}
