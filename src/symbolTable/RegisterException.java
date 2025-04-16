package symbolTable;

/**
 * La classe {@code RegisterException} rappresenta un'eccezione personalizzata che viene sollevata
 * quando non ci sono registri disponibili per l'assegnazione.
 *
 * @see Register
 * @author Kristian Rigo (matr. 20046665)
 */
public class RegisterException extends Exception {

    /**
     * Costruisce una nuova eccezione {@link RegisterException} senza alcun messaggio.
     */
    public RegisterException() {
        super();
    }

    /**
     * Costruisce una nuova eccezione {@link RegisterException} con un messaggio specifico.
     *
     * @param message Il messaggio descrittivo dell'eccezione.
     */
    public RegisterException(String message) {
        super(message);
    }

    /**
     * Costruisce una nuova eccezione {@link RegisterException} con un messaggio e una causa specifici.
     *
     * @param message Il messaggio descrittivo dell'eccezione.
     * @param cause La causa che ha originato l'eccezione.
     */
    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
