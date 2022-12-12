import token.Token;
import token.Tokenizer;
import visitor.CalculatorVisitor;
import visitor.ParserVisitor;
import visitor.PrintVisitor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        String expression = new Scanner(System.in).nextLine();
        Tokenizer tokenizer = new Tokenizer(new ByteArrayInputStream(expression.getBytes(StandardCharsets.UTF_8)));
        List<Token> tokenList = tokenizer.getTokens();
        List<Token> polishNotation = new ParserVisitor().toPolishNotation(tokenList);
        new PrintVisitor(System.out).printPolish(polishNotation);
        System.out.println(new CalculatorVisitor().calc(polishNotation));
    }

}
