package ast;

import symbolTable.Register;
import symbolTable.SymbolTable.Attribute;
import symbolTable.SymbolTable;
import visitor.IVisitor;

public class NodeId extends NodeAST {
    private String idName;
    private Attribute attribute;

    public NodeId(String idName) {
        this.idName = idName;
    }

    public String getIdName() {
        return this.idName;
    }

    /**
     * Ritorna l'{@link Attribute} associato a questo nodo.
     * <p>Se l'attributo di questo nodo non è stato ancora definito, viene assegnato ricercandolo tramite il metodo {@link SymbolTable#lookup}
     * e viene dunque ritornato l'attributo.</p>
     * <p>Se anche il metodo {@code lookUp} non è in grado di assegnare un attributo a questo nodo, viene ritornato {@code null}.</p>
     *
     * @return l'{@code Attribute} associato a questo nodo
     */
    public Attribute getAttribute() { return attribute == null ? attribute = SymbolTable.lookup(idName) : attribute; }

    /**
     * Ritorna il {@link Register} associato a questo nodo.
     * <p>Il registro è un carattere {@code char} che identifica il registro <em>dc</em> utilizzato per memorizzare il valore dell'identificatore.</p>
     * <p>Se l'attributo di questo nodo non è stato ancora definito, viene assegnato ricercandolo tramite il metodo {@link SymbolTable#lookup}
     * e viene ritornato il rispettivo registro.</p>
     * <p>Se anche il metodo {@code lookUp} non è in grado di assegnare un attributo a questo nodo, viene ritornato {@code (char) -1}.</p>
     *
     * @return il {@code Register} associato a questo nodo
     */
    public char getRegister() { return getAttribute() == null ? (char) -1 : attribute.getRegister(); }

    @Override
    public String toString() { return "[NodeId: " + this.idName + ']'; }

    /**
     * @param visitor
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
