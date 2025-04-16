package parser;

import token.TokenType;

/**
 * La classe {@code SyntacticException} rappresenta un'eccezione che viene sollevata
 * in caso di errori di parsing, ovvero errori sintattici durante l'analisi del codice sorgente.
 * <p>Questa eccezione fornisce costruttori per gestire errori specifici, come token inattesi
 * o messaggi di errore personalizzati.</p>
 *
 * @see Parser
 * @author Kristian Rigo (matr. 20046665)
 */
public class SyntacticException extends Exception {

    /**
     * Crea una nuova {@link SyntacticException} specificando la riga, il token atteso e il token trovato.
     * <p>Il messaggio di errore è costruito nel seguente formato:
     * <pre>"Expected " + expected + " but found " + found + " at line " + line</pre>
     *
     * @param line La riga in cui si è verificato l'errore.
     * @param expected Il token atteso.
     * @param found Il token effettivamente trovato.
     */
    public SyntacticException(int line, String expected, TokenType found) {
        super("Expected " + expected + " but found " + found + " at line " + line);
    }

    /**
     * Crea una nuova {@link SyntacticException} con il messaggio di errore specificato.
     *
     * @param message Il messaggio descrittivo dell'errore.
     */
    public SyntacticException(String message) {
        super(message);
    }

    /**
     * Crea una nuova {@link SyntacticException} con un messaggio di errore e una causa specifici.
     *
     * @param message Il messaggio descrittivo dell'errore.
     * @param cause La causa che ha originato questa eccezione.
     */
    public SyntacticException(String message, Throwable cause) {
        super(message, cause);
    }
}
