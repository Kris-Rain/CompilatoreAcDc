package ast;

import visitor.IVisitor;
import java.util.ArrayList;

/**
 * Classe che rappresenta il nodo principale dell'AST del programma.
 * <p>Il nodo {@code NodeProgram} contiene una lista di nodi {@link NodeDecSt} che rappresentano dichiarazioni o istruzioni.
 * Questo nodo è il punto di partenza per la visita dell'AST, utilizzata per operazioni come il type checking e la generazione del codice.</p>
 *
 * @see NodeDecSt
 * @see NodeAST
 * @see IVisitor
 * @author Kristian Rigo (matr. 20046665)
 */
public class NodeProgram extends NodeAST {
    private final ArrayList<NodeDecSt> decSts;

    /**
     * Costruisce un nuovo nodo {@link NodeProgram} con la lista di nodi {@link NodeDecSt} specificata.
     *
     * @param decSts La lista di nodi {@code NodeDecSt} da associare a questo nodo.
     */
    public NodeProgram(ArrayList<NodeDecSt> decSts) {
        this.decSts = decSts;
    }

    /**
     * Restituisce la lista di nodi {@link NodeDecSt} figli di questo nodo.
     * <p>Se non sono presenti nodi figli, viene restituita una lista vuota.</p>
     *
     * @return La lista di nodi {@code NodeDecSt} figli di questo nodo.
     */
    public ArrayList<NodeDecSt> getDecSts() {
        return decSts;
    }

    @Override
    public String toString() {
        return "[NodeProgram: " + decSts.toString() + ']';
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }


}
