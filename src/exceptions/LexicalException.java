package exceptions;

public class LexicalException extends Exception {

    public LexicalException(int line, char c) {
        super("Lexical error at line " + line + ": unexpected character '" + c + "'");
    }

    public LexicalException(int line, String seq) {
        super("Lexical error at line " + line + ": character sequence '" + seq + "' not recognized");
    }

    public LexicalException(int line) {
        super("Lexical error at line " + line + ": impossible to read character");
    }
}
