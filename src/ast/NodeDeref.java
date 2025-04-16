package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'AST per la dereferenziazione di un identificatore.
 * <p>Un oggetto {@code NodeDeref} è costituito da un nodo {@link NodeId} che rappresenta l'identificatore da dereferenziare.</p>
 * <p>Essendo un'operazione considerata un'espressione, questa classe estende {@link NodeExpr}.</p>
 *
 * @see NodeId
 * @see NodeExpr
 * @author Kristian Rigo (matr. 20046665)
 */
public class NodeDeref extends NodeExpr {
    private final NodeId id;

    /**
     * Costruisce un nuovo nodo {@link NodeDeref} con il nodo {@link NodeId} specificato.
     *
     * @param id Il nodo {@code NodeId} da associare a questo nodo.
     */
    public NodeDeref(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[NodeDeref: " + this.id + ']';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
