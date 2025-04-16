package visitor;

import ast.*;

/**
 * {@code IVisitor} è l'interfaccia per il pattern <em>visitor</em>.
 * <p>Questa interfaccia contiene un metodo {@code visit} per ogni tipo di nodo concreto dell'AST.
 * <br>Il metodo {@link NodeAST#accept} di ogni nodo concreto dell'AST chiama il metodo {@code visit} corrispondente dell'interfaccia {@code IVisitor}.</p>
 * <p>Questa interfaccia è implementata dalla classe {@link TypeCheckingVisitor} e {@link CodeGenerationVisitor}.</p>
 *
 * @see TypeCheckingVisitor
 * @see CodeGenerationVisitor
 * @see NodeAST
 * @author Kristian Rigo (matr. 20046665)
 */
public interface IVisitor {

    /**
     * Visita il nodo {@link NodeProgram} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodePrg il nodo {@code NodeProgram} da visitare
     */
    void visit(NodeProgram nodePrg);

    /**
     * Visita il nodo {@link NodeAssign} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodeDecl il nodo {@code NodeDecl} da visitare
     */
    void visit(NodeDecl nodeDecl);

    /**
     * Visita il nodo {@link NodeId} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodeId il nodo {@code NodeId} da visitare
     */
    void visit(NodeId nodeId);

    /**
     * Visita il nodo {@link NodeBinOp} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodeBinOp il nodo {@code NodeBinOp} da visitare
     */
    void visit(NodeBinOp nodeBinOp);

    /**
     * Visita il nodo {@link NodeAssign} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodeAssign il nodo {@code NodeAssign} da visitare
     */
    void visit(NodeAssign nodeAssign);

    /**
     * Visita il nodo {@link NodeConst} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodeConst il nodo {@code NodeConst} da visitare
     */
    void visit(NodeConst nodeConst);

    /**
     * Visita il nodo {@link NodeDeref} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodeDeref il nodo {@code NodeDeref} da visitare
     */
    void visit(NodeDeref nodeDeref);

    /**
     * Visita il nodo {@link NodePrint} e ne esegue l'analisi semantica o la
     * generazione del codice, a seconda dell'implementazione.
     *
     * @param nodePrint il nodo {@code NodePrint} da visitare
     */
    void visit(NodePrint nodePrint);
}
