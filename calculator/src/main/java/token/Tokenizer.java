package token;

import position.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private Position position = new StartPosition();
    private final InputStreamReader input;
    private int index = -1;
    private char curChar = (char) -1;

    public List<Token> getTokens() throws IOException, ParseException {
        List<Token> tokens = new ArrayList<>();
        nextChar();
        position.nextToken(this);
        while (!(position instanceof ErrorPosition) && !(position instanceof EndPosition)) {
            tokens.add(position.createToken(this));
            while (!isEOF() && Character.isWhitespace(curChar)) {
                nextChar();
            }
            position.nextToken(this);
        }
        if (position instanceof ErrorPosition) {
            throw new ParseException(((ErrorPosition) position).message(), index);
        }
        return tokens;
    }

    public void nextChar() throws IOException {
        index++;
        curChar = (char) input.read();
    }

    public Tokenizer(InputStream is) {
        input = new InputStreamReader(is);
    }

    public void setState(Position position) {
        this.position = position;
    }

    public boolean isOperationOrBrace() {
        return "+-*/()".indexOf(curChar) != -1;
    }

    public boolean isDigit() {
        return Character.isDigit(curChar);
    }

    public boolean isEOF() {
        return curChar == (char) -1;
    }

    public char getCurChar() {
        return curChar;
    }

    public int parseNumber() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (!isEOF() && isDigit()) {
            sb.append(getCurChar());
            nextChar();
        }
        return Integer.parseInt(sb.toString());
    }
}
