package ast;

import visitor.TypeCheckingVisitor;

/**
 * Classe che rappresenta un descrittore di tipo, utilizzata per il type checking.
 * <p>Un {@code TypeDescriptor} è composto da un {@link TypeTD} e da un messaggio di errore (se il tipo è {@code TypeTD.ERROR}).</p>
 *
 * @see TypeTD
 * @see TypeCheckingVisitor
 * @author Kristian Rigo (matr. 20046665)
 */
public class TypeDescriptor {
    private final TypeTD type;
    private final String msg;

    /**
     * Costruisce un nuovo {@link TypeDescriptor} con il tipo e il messaggio di errore specificati.
     * <p><strong>Nota:</strong> il messaggio di errore è rilevante solo se il tipo è {@code TypeTD.ERROR}.</p>
     *
     * @param type Il tipo associato a questo {@code TypeDescriptor}.
     * @param msg Il messaggio di errore associato (se il tipo è {@code TypeTD.ERROR}).
     */
    public TypeDescriptor(TypeTD type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    /**
     * Costruisce un nuovo {@link TypeDescriptor} con il tipo specificato e un messaggio di errore vuoto.
     *
     * @param type Il tipo associato a questo {@code TypeDescriptor}.
     */
    public TypeDescriptor(TypeTD type) {
        this(type, "");
    }

    /**
     * Restituisce il tipo di questo {@code TypeDescriptor}.
     *
     * @return Il {@link TypeTD} associato a questo {@code TypeDescriptor}.
     */
    public TypeTD getType() {
        return this.type;
    }

    /**
     * Restituisce il messaggio di errore associato a questo {@code TypeDescriptor}.
     *
     * @return Il messaggio di errore, oppure una stringa vuota {@code ""} se non è presente.
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * Verifica se questo {@code TypeDescriptor} è compatibile con un altro {@code TypeDescriptor} e restituisce {@code true} nel caso in cui lo siano.
     * <p>Un {@code TypeDescriptor} è considerato compatibile se:
     * <ul>
     *     <li>Entrambi i tipi sono uguali.</li>
     *     <li>Il tipo corrente è {@code TypeTD.INT} e l'altro è {@code TypeTD.FLOAT}.</li>
     * </ul>
     *
     * @param tD Il {@code TypeDescriptor} con cui verificare la compatibilità.
     * @return {@code true} se i due {@code TypeDescriptor} sono compatibili, {@code false} altrimenti.
     */
    public boolean isCompatible(TypeDescriptor tD) {
        return this.type == tD.type || (this.type == TypeTD.INT && tD.type == TypeTD.FLOAT);
    }

    /**
     * Verifica se questo {@code TypeDescriptor} rappresenta un errore.
     *
     * @return {@code true} se il tipo è {@code TypeTD.ERROR}, {@code false} altrimenti.
     */
    public boolean isError() {
        return this.type == TypeTD.ERROR;
    }

    /**
     * Verifica se questo {@code TypeDescriptor} rappresenta un valore di tipo {@code float}.
     *
     * @return {@code true} se il tipo è {@code TypeTD.FLOAT}, {@code false} altrimenti.
     */
    public boolean isFloat() {
        return this.type == TypeTD.FLOAT;
    }

    /**
     * Verifica se questo {@code TypeDescriptor} rappresenta un valore di tipo {@code int}.
     *
     * @return {@code true} se il tipo è {@code TypeTD.INT}, {@code false} altrimenti.
     */
    public boolean isInt() {
        return this.type == TypeTD.INT;
    }

    /**
     * Verifica se questo {@code TypeDescriptor} rappresenta un'istruzione valida.
     *
     * @return {@code true} se il tipo è {@code TypeTD.OK}, {@code false} altrimenti.
     */
    public boolean isOk() {
        return this.type == TypeTD.OK;
    }
}