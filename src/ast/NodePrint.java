package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'AST per un'operazione di stampa.
 * <p>Un oggetto {@code NodePrint} è composto da un nodo {@link NodeId} che identifica l'elemento il cui valore deve essere stampato.</p>
 *
 * @see NodeId
 * @see NodeStm
 * @see IVisitor
 * @author Kristian Rigo (matr. 20046665)
 */
public class NodePrint extends NodeStm {

    /**
     * Costruisce un nuovo nodo {@link NodePrint} con il nodo {@link NodeId} specificato.
     *
     * @param id Il nodo {@code NodeId} che rappresenta l'identificatore il cui valore deve essere stampato.
     */
    public NodePrint(NodeId id) {
        super(id);
    }

    @Override
    public String toString() {
        return "[NodePrint: " + this.getId() + ']';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
