package ast;

/**
 * Enumerazione che rappresenta i tipi utilizzati durante il controllo dei tipi nel linguaggio <em>ac</em>.
 * <p>Il linguaggio <em>ac</em> supporta due tipi principali: {@link #INT} e {@link #FLOAT}.
 * <br>Il tipo {@link #OK} viene utilizzato per indicare istruzioni valide.
 * <br>Il tipo {@link #ERROR} viene utilizzato per segnalare errori semantici o di conversione.</p>
 *
 * @see TypeDescriptor
 * @see LangType
 * @author Kristian Rigo (matr. 20046665)
 */
public enum TypeTD {
    /**
     * Tipo {@link TypeTD} che rappresenta un'espressione valida di tipo {@code int}.
     */
    INT,
    /**
     * Tipo {@link TypeTD} che rappresenta un'espressione valida di tipo {@code float}.
     */
    FLOAT,
    /**
     * Tipo {@link TypeTD} che indica un'istruzione corretta.
     */
    OK,
    /**
     * Tipo {@link TypeTD} che segnala un errore semantico e/o di conversione.
     * <p>L'unica conversione consentita è da {@code int} a {@code float}.</p>
     */
    ERROR
}
