package token;

import scanner.Scanner;

/**
 * La classe {@code Token} rappresenta un token qualsiasi del linguaggio <em>ac</em>.
 * <br>Ogni token contiene informazioni sul tipo {@link TokenType}, la riga {@code int} in cui è stato trovato
 * e un valore {@code String} opzionale associato.
 * <br>Il valore può essere {@code null} se il token non ha un valore associato, come nel caso delle parole chiave,
 * dell'operatore di assegnamento {@code "="} o del delimitatore {@code ";"}.
 *
 * @see TokenType
 * @see Scanner
 * @author Kristian Rigo (matr. 20046665)
 */
public class Token {

    private final int line;
    private final TokenType type;
    private final String val;

    /**
     * Costruttore della classe {@link Token}, che prende in input il {@link TokenType}, la riga e il valore specificati.
     *
     * @param tipo Il tipo del {@code token}.
     * @param line La riga in cui è stato trovato il {@code token}.
     * @param val Il valore associato al {@code token} (può essere {@code null}).
     */
    public Token(TokenType tipo, int line, String val) {
        this.type = tipo;
        this.line = line;
        this.val = val;
    }

    /**
     * Costruttore della classe {@link Token}, che prende in input il {@link TokenType}, la riga e il valore specificati.
     * <br>Il valore di questo {@code token} viene impostato a {@code null}, dunque questo costruttore è usato solo
     * nel caso in cui il valore non abbia un valore associato, come nel caso delle parole chiave, dell'operatore di assegnamento {@code "="}
     * o del delimitatore {@code ";"}.
     *
     * @param tipo Il tipo di questo {@code token}.
     * @param line La riga in cui è stato trovato questo {@code token}.
     */
    public Token(TokenType tipo, int line) {
        this(tipo, line, null);
    }

    /**
     * Ritorna la riga in cui è stato trovato questo {@link Token}.
     *
     * @return La riga in cui è stato letto questo {@code token}.
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Restituisce il {@link TokenType} di questo {@link Token}.
     *
     * @return Il valore {@link TokenType} di questo {@code token}.
     */
    public TokenType getType() {
        return this.type;
    }

    /**
     * Ritorna il valore di questo {@link Token}, nel formato {@code String}.
     * <br>Il valore ritornato può essere {@code null} se il token non ha un valore associato,
     * come nel caso delle parole chiave o degli operatori.
     *
     * @return Il valore di questo {@code token} o {@code null} se non è presente.
     */
    public String getVal() {
        return this.val;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del token.
     * <br>Ad esempio, un token con tipo {@code INT}, riga {@code 1} e valore {@code 42}
     * sarà rappresentato come {@code <INT, r:1, 42>}.
     *
     * @return Una stringa che rappresenta il token.
     */
    @Override
    public String toString() {
        if (this.getVal() == null) {
            return "<" + this.getType() + ", r:" + this.getLine() + ">";
        }
        return "<" + this.getType() + ", r:" + this.getLine() + ", " + this.getVal() + ">";
    }
}
