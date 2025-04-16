package ast;

import visitor.IVisitor;

/**
 * Classe astratta che rappresenta un nodo generico dell'AST.
 * <p>La classe {@code NodeAST} è la classe base per <em>tutti</em> i nodi dell'AST.
 *
 * @see NodeDecSt
 * @see NodeExpr
 * @see NodeProgram
 * @see NodeId
 * @author Kristian Rigo (matr. 20046665)
 */
public abstract class NodeAST {

    /**
     * Ritorna il nodo {@link NodeId} assegnato a questo nodo. Se il nodo non {@code NodeId} associati, ritorna {@code null}.
     *
     * @return Il {@code NodeId} assegnato a questo nodo.
     * @throws UnsupportedOperationException se il nodo non prevede associazioni con nodi di classe {@code NodeId}.
     */
    public NodeId getId() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    /**
     * Ritorna il tipo {@link LangType} di questo nodo.
     *
     * @return Il {@code LangType} associato a questo nodo.
     * @throws UnsupportedOperationException se il nodo non prevede un {@code LangType}.
     */
    public LangType getType() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    /**
     * Metodo che accetta un {@link IVisitor} e invoca il metodo {@code visit} corrispondente a questo nodo.
     *
     * @param visitor Il {@link IVisitor} da accettare per visitare questo nodo.
     */
    public abstract void accept(IVisitor visitor);

    /**
     * Metodo che restituisce una rappresentazione in formato {@code String} del nodo.
     * La stringa ritornata è nel formato {@code [<TipoNodo>: <Attributi>]}, dove:
     * <ul>
     *    <li>{@code <TipoNodo>} è il tipo di questo nodo dell'AST;</li>
     *    <li>{@code <Attributi>} sono gli attributi di questo nodo, come il nome, il {@code LangType} o eventuali nodi figli.</li>
     * </ul>
     *
     * @return La rappresentazione in formato {@code String} di questo nodo.
     */
    @Override
    public abstract String toString();
}
