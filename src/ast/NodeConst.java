package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'AST per una costante numerica.
 * <p>Un oggetto {@code NodeConst} è caratterizzato da un tipo {@link LangType} e da un valore numerico (rappresentato come {@code String}).</p>
 * <p>Questo nodo rappresenta una specifica espressione costituita esclusivamente da una costante numerica.</p>
 *
 * @see LangType
 * @see NodeExpr
 * @author Kristian Rigo (matr. 20046665)
 */
public class NodeConst extends NodeExpr {
    private final String value;
    private final LangType type;

    /**
     * Costruisce un nuovo nodo {@link NodeConst} con il tipo {@link LangType} e la costante numerica specificati.
     * <p>Il valore numerico può essere di tipo {@code int} o {@code float}.</p>
     *
     * @param value Il valore numerico di questo nodo, in formato {@code String}.
     * @param type Il tipo {@code LangType} associato a questo nodo.
     */
    public NodeConst(String value, LangType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Restituisce il valore numerico di questo nodo.
     * <p>Il valore è rappresentato come una stringa e può essere di tipo {@code int} o {@code float}.</p>
     *
     * @return Il valore numerico di questo nodo in formato {@code String}.
     */
    public String getValue() {
        return value;
    }

    public LangType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "[NodeConst: " + this.type + ", " + this.value + ']';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}