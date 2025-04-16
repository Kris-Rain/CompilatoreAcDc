package ast;

import symbolTable.Register;
import symbolTable.SymbolTable.Attribute;
import symbolTable.SymbolTable;
import visitor.IVisitor;

/**
 * Classe che rappresenta un nodo identificatore.
 * <p>Un nodo {@code NodeId} ha come attributo un oggetto {@link Attribute} che rappresenta il tipo e il registro associato all'identificatore.
 * Ogni {@code NodeId} possiede un nome (che lo identifica <strong>univocamente</strong>) in formato {@code String}.</p>
 *
 * @see NodeAST
 * @author Kristian Rigo (matr. 20046665)
 */
public class NodeId extends NodeAST {
    private final String idName;
    private Attribute attribute;

    /**
     * Crea un nuovo nodo {@link NodeId} con il nome specificato.
     *
     * @param idName il nome che identifica questo nodo.
     */
    public NodeId(String idName) {
        this.idName = idName;
    }

    /**
     * Ritorna il nome di questo nodo.
     *
     * @return il nome di questo nodo.
     */
    public String getIdName() {
        return this.idName;
    }

    /**
     * Ritorna l'{@link Attribute} associato a questo nodo.
     * <p>Se l'attributo di questo nodo non è stato ancora definito, viene assegnato ricercandolo tramite il metodo {@link SymbolTable#lookup}
     * e viene dunque ritornato l'attributo.</p>
     * <p>Se anche il metodo {@code lookUp} non è in grado di assegnare un attributo a questo nodo, viene ritornato {@code null}.</p>
     *
     * @return l'{@code Attribute} associato a questo nodo.
     */
    public Attribute getAttribute() { return attribute == null ? attribute = SymbolTable.lookup(idName) : attribute; }

    /**
     * Ritorna il {@link Register} associato a questo nodo.
     * <p>Il registro è un carattere {@code char} che identifica il registro <em>dc</em> utilizzato per memorizzare il valore dell'identificatore.</p>
     * <p>Se l'attributo di questo nodo non è stato ancora definito, viene assegnato ricercandolo tramite il metodo {@link SymbolTable#lookup}
     * e viene ritornato il rispettivo registro.</p>
     * <p>Se anche il metodo {@code lookUp} non è in grado di assegnare un attributo a questo nodo, viene ritornato {@code (char) -1}.</p>
     *
     * @return il {@code Register} associato a questo nodo.
     */
    public char getRegister() { return getAttribute() == null ? (char) -1 : attribute.getRegister(); }

    @Override
    public String toString() { return "[NodeId: " + this.idName + ']'; }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
