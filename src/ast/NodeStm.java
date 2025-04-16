package ast;

/**
 * Classe astratta che rappresenta un nodo dell'AST per operazioni di istruzione, come assegnamenti o stampe.
 * <p>La classe {@code NodeStm} funge da base per i nodi {@link NodeAssign} e {@link NodePrint},
 * che rappresentano rispettivamente operazioni di assegnamento e di stampa.</p>
 *
 * @see NodeAssign
 * @see NodePrint
 * @see NodeDecSt
 * @author Kristian Rigo (matr. 20046665)
 */
public abstract class NodeStm extends NodeDecSt {

    /**
     * Crea un nuovo {@code NodeStm} con il {@link NodeId} specificato.
     *
     * @param id il {@code NodeId} di questo {@code NodeStm}
     */
    public NodeStm(NodeId id) {
        super(id);
    }
}
