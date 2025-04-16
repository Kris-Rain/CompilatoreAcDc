package token;

import scanner.Scanner;
import java.util.Arrays;

/**
 * L'enum {@code TokenType} rappresenta i vari tipi di token che possono essere identificati
 * e processati dallo {@link Scanner}.
 * Ogni tipo di token corrisponde a un elemento sintattico o semantico specifico del linguaggio.
 *
 * @see Scanner
 * @see Token
 * @author Kristian Rigo (matr. 20046665)
 */
public enum TokenType {
	/**
	 * {@link TokenType} che rappresenta un numero intero valido
	 */
    INT,
	/**
	 * {@link TokenType} che rappresenta un numero float valido
	 */
    FLOAT,
	/**
	 * {@link TokenType} che rappresenta la parola chiave {@code int}
	 */
	TYINT,
	/**
	 * {@link TokenType} che rappresenta la parola chiave {@code float}
	 */
	TYFLOAT,
	/**
	 * {@link TokenType} che rappresenta un identificatore (es. nome di variabile o funzione)
	 */
	ID,
	/**
	 * {@link TokenType} che rappresenta l'operatore {@code "+"}
	 */
	PLUS,
	/**
	 * {@link TokenType} che rappresenta l'operatore {@code "-"}
	 */
	MINUS,
	/**
	 * {@link TokenType} che rappresenta l'operatore {@code "/"}
	 */
	DIVIDE,
	/**
	 * {@link TokenType} che rappresenta l'operatore {@code "*"}
	 */
	TIMES,
	/**
	 * {@link TokenType} che rappresenta un operatore di assegnamento composto. Questo token può valere {@code "+="}, {@code "-="}, {@code "*="}, {@code "/="}.
	 */
	OP_ASS,
	/**
	 * {@link TokenType} che rappresenta l'operatore di assegnamento {@code "="}
	 */
	ASS,
	/**
	 * {@link TokenType} che rappresenta la parola chiave {@code print}
	 */
	PRINT,
	/**
	 * {@link TokenType} che rappresenta il delimitatore {@code ";"}
	 */
	SEMI,
	/**
	 * {@link TokenType} che rappresenta il marcatore di fine file {@code EOF}
	 */
	EOF;

    /**
     * Converte un array di valori {@code TokenType} in una stringa separata da virgole.
	 * <br>Ad esempio, {@code toStringEach(TokenType.INT, TokenType.FLOAT)} ritorna {@code "INT, FLOAT"}.
     *
     * @param types Un array di valori {@code TokenType} da convertire.
     * @return Una stringa contenente i nomi dei valori {@code TokenType}, separati da virgole.
     */
    public static String toStringEach(TokenType[] types) {
        return String.join(", ", Arrays.stream(types).map(Enum::toString).toArray(String[]::new));
    }
}