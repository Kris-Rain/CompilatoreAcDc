package ast;

import visitor.IVisitor;

public abstract class NodeAST {

    /**
     * Ritorna il nodo {@link NodeId} assegnato a questo nodo. Se il nodo non {@code NodeId} associati, ritorna {@code null}.
     *
     * @return il {@code NodeId} assegnato a questo nodo
     * @throws UnsupportedOperationException se il nodo non prevede associazioni con nodi di classe {@code NodeId}
     */
    public NodeId getId() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    /**
     * Ritorna il tipo {@link LangType} di questo nodo.
     *
     * @return il {@code LangType} associato a questo nodo
     * @throws UnsupportedOperationException se il nodo non prevede un {@code LangType}
     */
    public LangType getType() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    /**
     * Metodo che accetta un visitatore.
     * <p>Questo metodo permette di visitare il nodo corrente tramite un visitatore specifico.</p>
     *
     * @param visitor il visitatore da accettare
     */
    public abstract void accept(IVisitor visitor);

    /**
     * Metodo che restituisce una rappresentazione in formato stringa del nodo.
     * <p>Questo metodo permette di ottenere una rappresentazione testuale del nodo corrente.</p>
     *
     * @return la rappresentazione in formato stringa del nodo
     */
    @Override
    public abstract String toString();
}
