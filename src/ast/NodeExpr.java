package ast;

/**
 * Classe astratta che rappresenta un qualsiasi nodo espressione nell'AST.
 * <p>La classe {@code NodeExpr} funge da base per <em>tutti</em> i nodi espressione, come
 * {@link NodeConst}, {@link NodeBinOp} e {@link NodeDeref}.</p>
 * <p><em>Nota:</em> Il corpo di questa classe è intenzionalmente vuoto, poiché non esistono operazioni comuni
 * tra le sue sottoclassi. Tuttavia, la sua presenza è fondamentale, in particolare durante il parsing,
 * per applicare regole sintattiche condivise tra diverse tipologie di espressioni.</p>
 *
 * @see NodeBinOp
 * @see NodeConst
 * @see NodeDeref
 * @see NodeAST
 * @author Kristian Rigo (matr. 20046665)
 */
public abstract class NodeExpr extends NodeAST {}
