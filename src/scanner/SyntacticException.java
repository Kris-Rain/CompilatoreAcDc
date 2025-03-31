package scanner;

import token.TokenType;

public class SyntacticException extends RuntimeException {

  public SyntacticException(int line, String expected, TokenType found) {
    super("Expected " + expected + " but found " + found + " at line " + line);
  }
  public SyntacticException(String message) {
    super(message);
  }
}
