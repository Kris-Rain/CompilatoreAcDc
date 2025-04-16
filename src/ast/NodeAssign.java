package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'AST per un'operazione di assegnazione.
 * <p>Un oggetto {@code NodeAssign} è composto da un identificatore {@link NodeId} e da un'espressione {@link NodeExpr}
 * ovvero l'espressione che viene assegnata all'identificatore.</p>
 *
 * @see NodeId
 * @see NodeExpr
 * @see NodeStm
 * @author Kristian Rigo (matr. 20046665)
 */
public class NodeAssign extends NodeStm {
    private final NodeExpr expr;

    /**
     * Costruisce un nuovo nodo {@link NodeAssign} con un identificatore {@link NodeId} e un nodo {@link NodeExpr} specificati.
     *
     * @param id L'identificatore da associare a questo nodo.
     * @param expr L'espressione da associare a questo nodo.
     */
    public NodeAssign(NodeId id, NodeExpr expr) {
        super(id);
        this.expr = expr;
    }

    /**
     * Restituisce l'espressione {@link NodeExpr} associata a questo nodo.
     *
     * @return L'espressione {@link NodeExpr} di questo nodo.
     */
    public NodeExpr getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "[NodeAssign: " + this.getId() + " -> " + expr.toString() + ']';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}