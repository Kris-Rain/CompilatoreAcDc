package ast;

/**
 * Enumerazione che rappresenta i tipi di terminali supportati dal linguaggio <em>ac</em>.
 * <p>Il linguaggio <em>ac</em> include due tipi principali: {@link #INT} e {@link #FLOAT}.
 * Questi tipi sono utilizzati durante la costruzione dell'AST e il controllo dei tipi.</p>
 *
 * @see TypeTD
 * @see LangOper
 * @author Kristian Rigo (matr. 20046665)
 */
public enum LangType {
    /**
     * Tipo {@link LangType} che rappresenta un valore intero ({@code int}).
     */
    INT,
    /**
     * Tipo {@link LangType} che rappresenta un valore in virgola mobile ({@code float}).
     */
    FLOAT
}