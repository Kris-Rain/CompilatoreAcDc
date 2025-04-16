package ast;

import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo dell'AST per la dichiarazione di una variabile.
 * <p>Un oggetto {@code NodeDecl} è composto da un identificatore {@link NodeId} da inizializzare, un tipo {@link LangType} e un'espressione {@link NodeExpr}
 * che rappresenta l'inizializzazione della variabile. L'inizializzazione può essere assente, in tal caso il valore sarà {@code null}.</p>
 *
 * @see NodeId
 * @see LangType
 * @see NodeExpr
 * @see NodeDecSt
 * @author Kristian Rigo (matr. 20046665)
 */
public class NodeDecl extends NodeDecSt {
    private final LangType type;
    private final NodeExpr init;

    /**
     * Costruisce un nuovo nodo {@link NodeDecl} con l'identificatore {@link NodeId}, il tipo {@link LangType} e il nodo {@link NodeExpr}
     * per l'inizializzazione specificati.
     * <p>Il parametro {@code init} può essere {@code null} se la variabile non è inizializzata.</p>
     *
     * @param id L'identificatore {@link NodeId} della variabile da dichiarare.
     * @param type Il tipo {@link LangType} della variabile da dichiarare.
     * @param init L'espressione {@link NodeExpr} di inizializzazione, oppure {@code null} se non presente.
     */
    public NodeDecl(NodeId id, LangType type, NodeExpr init) {
        super(id);
        this.type = type;
        this.init = init;
    }

    public LangType getType() {
        return this.type;
    }

    /**
     * Restituisce l'espressione di inizializzazione {@link NodeExpr} associata a questo nodo.
     * <p>Se la variabile non è inizializzata, restituisce {@code null}.</p>
     *
     * @return L'espressione di inizializzazione o {@code null} se non presente.
     */
    public NodeExpr getInit() {
        return this.init;
    }

    @Override
    public String toString() {
        return "[NodeDecl: id = " + this.getId() + ", type = " + this.type + ", init = " + this.getInit() + ']';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
