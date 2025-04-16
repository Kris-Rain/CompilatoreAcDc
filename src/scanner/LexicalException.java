package scanner;

/**
 * La classe {@code LexicalException} rappresenta un'eccezione che viene sollevata
 * in caso di errore durante l'analisi lessicale eseguita da uno {@link Scanner}.
 * <p>Questa eccezione fornisce diversi costruttori per gestire errori specifici, come caratteri
 * non accettati o sequenze di caratteri non riconosciute.</p>
 *
 * @see Scanner
 * @author Kristian Rigo (matr. 20046665)
 */
public class LexicalException extends Exception {

    /**
     * Crea una nuova {@link LexicalException} specificando la riga e il carattere che ha causato l'errore.
     * <p>Il messaggio di errore è costruito nel seguente formato:
     * <pre>"Errore lessicale alla riga " + line + ": carattere '" + c + "' non accettato"</pre>
     *
     * @param line La riga in cui si è verificato l'errore.
     * @param c Il carattere non accettato che ha causato l'errore.
     */
    public LexicalException(int line, char c) {
        super("Lexical error at line " + line + ": unexpected character '" + c + "' not accepted");
    }

    /**
     * Crea una nuova {@link LexicalException} specificando la riga e la sequenza di caratteri non riconosciuta.
     * <p>Il messaggio di errore è costruito nel seguente formato:
     * <pre>"Errore lessicale alla riga " + line + ": sequenza di caratteri '" + seq + "' non riconosciuta"</pre>
     *
     * @param line La riga in cui si è verificato l'errore.
     * @param seq La sequenza di caratteri non riconosciuta che ha causato l'errore.
     */
    public LexicalException(int line, String seq) {
        super("Lexical error at line " + line + ": character sequence '" + seq + "' not recognized");
    }

    /**
     * Crea una nuova {@link LexicalException} specificando solo la riga in cui si è verificato l'errore.
     * In questo caso non è specificato il carattere o la sequenza di caratteri che ha causato l'errore poiché è stato impossibile.
     * <p>Il messaggio di errore è costruito nel seguente formato:
     * <pre>"Errore lessicale alla riga " + line + ": impossibile leggere il carattere"</pre>
     *
     * @param line La riga in cui si è verificato l'errore.
     */
    public LexicalException(int line) {
        super("Lexical error at line " + line + ": impossible to read character");
    }
}
