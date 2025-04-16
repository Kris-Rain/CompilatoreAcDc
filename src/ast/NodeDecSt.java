package ast;

/**
 * Classe astratta che rappresenta un nodo dell'AST per dichiarazioni di variabili o istruzioni come l'assegnamento o la stampa.
 * <p>La classe {@code NodeDecSt} funge da base per i nodi che rappresentano dichiarazioni di variabili
 * ({@link NodeDecl}) o istruzioni che estendono {@link NodeStm}.</p>
 *
 * @see NodeStm
 * @see NodeDecl
 * @see NodeAST
 * @author Kristian Rigo (matr. 20046665)
 */
public abstract class NodeDecSt extends NodeAST {
    private final NodeId id;

    /**
     * Crea un nuovo {@link NodeDecSt} con il {@link NodeId} specificato.
     *
     * @param id il {@code NodeId} relativo a questo {@code NodeDecSt}.
     */
    public NodeDecSt(NodeId id) {
        this.id = id;
    }

    @Override
    public NodeId getId() {
        return id;
    }
}
