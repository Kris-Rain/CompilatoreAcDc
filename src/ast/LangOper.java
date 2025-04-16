package ast;

/**
 * Enumerazione che rappresenta gli operatori del linguaggio <em>ac</em>..
 * <p>Questa enumerazione include cinque operatori principali: {@link #PLUS}, {@link #MINUS}, {@link #TIMES}, {@link #DIV} e {@link #DIV_FLOAT}.
 * <br>Ogni operatore è associato a un simbolo specifico, che può essere ottenuto tramite il metodo {@link #getSymbol()}.</p>
 *
 * @see LangType
 * @see NodeBinOp
 * @author Kristian Rigo (matr. 20046665)
 */
public enum LangOper {
    /**
     * Operatore {@link LangOper} che rappresenta l'addizione.
     */
    PLUS,
    /**
     * Operatore {@link LangOper} che rappresenta la sottrazione.
     */
    MINUS,
    /**
     * Operatore {@link LangOper} che rappresenta la moltiplicazione.
     */
    TIMES,
    /**
     * Operatore {@link LangOper} che rappresenta la divisione intera.
     */
    DIV,
    /**
     * Operatore {@link LangOper} che rappresenta la divisione in virgola mobile.
     */
    DIV_FLOAT;

    /**
     * Restituisce il simbolo corrispondente a questo operatore.
     * <p>Il simbolo restituito è una stringa che rappresenta l'operatore matematico associato.</p>
     *
     * @return Il simbolo dell'operatore in formato {@code String}.
     */
    public String getSymbol() {
        return switch (this) {
            case PLUS -> "+";
            case MINUS -> "-";
            case TIMES -> "*";
            case DIV, DIV_FLOAT -> "/";
        };
    }
}