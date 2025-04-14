package symbolTable;

public class RegisterException extends RuntimeException {
  public RegisterException() {
    super();
  }
  public RegisterException(String message) {
    super(message);
  }
  public RegisterException(String message, Throwable cause) {
    super(message, cause);
  }
}
