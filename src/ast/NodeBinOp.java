package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'AST per un'operazione binaria.
 * <p>Un oggetto {@code NodeBinOp} è composto da un operatore {@link LangOper} e da due nodi {@link NodeExpr}
 * che rappresentano gli operandi (sinistro e destro) dell'operazione.</p>
 *
 * @see LangOper
 * @see NodeExpr
 * @author Kristian Rigo (20046665)
 */
public class NodeBinOp extends NodeExpr {
    private final NodeExpr left;
    private final NodeExpr right;
    private LangOper op;

    /**
     * Costruisce un nuovo nodo {@link NodeBinOp} con i nodi {@link NodeExpr} sinistro e destro
     * e l'operatore {@link LangOper} specificati.
     *
     * @param left Il nodo {@code NodeExpr} che rappresenta l'operando sinistro.
     * @param op L'operatore {@code LangOper} da associare a questo nodo.
     * @param right Il nodo {@code NodeExpr} che rappresenta l'operando destro.
     */
    public NodeBinOp(NodeExpr left, LangOper op, NodeExpr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    /**
     * Restituisce il nodo {@link NodeExpr} sinistro di questo nodo, ovvero l'operando sinistro dell'operazione.
     *
     * @return Il nodo {@code NodeExpr} che rappresenta l'operando sinistro.
     */
    public NodeExpr getLeft() {
        return this.left;
    }

    /**
     * Restituisce il nodo {@link NodeExpr} destro di questo nodo, ovvero l'operando destro dell'operazione.
     *
     * @return Il nodo {@code NodeExpr} che rappresenta l'operando destro.
     */
    public NodeExpr getRight() {
        return this.right;
    }

    /**
     * Restituisce l'operatore {@link LangOper} associato a questo nodo.
     *
     * @return L'operatore {@code LangOper} di questo nodo.
     */
    public LangOper getOp() {
        return this.op;
    }

    /**
     * Imposta l'operatore {@link LangOper} di questo nodo.
     *
     * @param op L'operatore {@code LangOper} da associare a questo nodo.
     */
    public void setOp(LangOper op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "[NodeBinOp: " + left.toString() + " " + op.toString() + " " + right.toString() + "]";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}