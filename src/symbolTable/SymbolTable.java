package symbolTable;

import ast.LangType;
import ast.NodeId;

import java.util.HashMap;

/**
 * La classe {@code SymbolTable} è responsabile della gestione degli identificatori.
 * <p>Questa tabella associa a ogni identificatore un oggetto {@link Attribute}, che contiene informazioni sul tipo e sul registro associato.</p>
 * <p>La tabella è implementata come una struttura statica basata su {@link HashMap} e deve essere inizializzata tramite il metodo {@link #init()} prima dell'uso.</p>
 *
 * @see Attribute
 * @see NodeId
 * @see LangType
 * @see Register
 * @author Kristian Rigo (matr. 20046665)
 */
public class SymbolTable {

    private static HashMap<String, Attribute> table = new HashMap<>();

    /**
     * Classe membro statica di {@link SymbolTable} che rappresenta un attributo associato a un identificatore.
     * <p>Un attributo è composto da un tipo di dato {@link LangType} che rappresenta il tipo e può essere {@code int} o {@code float},
     * e da un registro {@code char} che identifica il registro <em>dc</em> utilizzato per memorizzare il valore dell'identificatore.</p>
     *
     * @see SymbolTable
     * @see LangType
     * @see Register
     * @author Kristian Rigo (matr. 20046665)
     */
    public static class Attribute {
        private final LangType type;
        private char register;

        /**
         * Crea un nuovo attributo con il tipo di dato specificato.
         * <p>Il registro viene assegnato automaticamente tramite il metodo {@link Register#newRegister()}.</p>
         * <p>Se non è possibile assegnare un registro, il valore predefinito sarà {@code (char) -1}.</p>
         *
         * @param type Il tipo di dato da associare all'attributo.
         */
        public Attribute(LangType type) {
            this.type = type;
            try {
                this.register = Register.newRegister();
            } catch (RegisterException e) {
                this.register = (char) -1;
            }
        }

        /**
         * Restituisce il tipo di dato {@link LangType} associato a questo attributo.
         *
         * @return Il tipo di dato come {@code LangType} di questo attributo.
         */
        public LangType getType() {
            return this.type;
        }

        /**
         * Restituisce il registro {@code char} associato a questo attributo.
         *
         * @return Il registro come carattere {@code char}.
         */
        public char getRegister() {
            return this.register;
        }
    }

    /**
     * Inizializza la {@link SymbolTable} a una nuova tabella di simboli vuota.
     */
    public static void init() {
        table = new HashMap<>();
    }

    /**
     * Inserisce un identificatore nella tabella, associandogli l'{@link Attribute} specificato.
     * <p>Se l'identificatore è già presente, il metodo non effettua modifiche e restituisce {@code false}.</p>
     *
     * @param idName La stringa che rappresenta l'identificatore da inserire.
     * @param entry L'{@code Attribute} da associare all'identificatore.
     * @return {@code true} se l'inserimento è avvenuto con successo, {@code false} altrimenti.
     */
    public static boolean enter(String idName, Attribute entry) {
        if (table.containsKey(idName)) return false;
        table.put(idName, entry);
        return true;
    }

    /**
     * Cerca un identificatore nella tabella e restituisce l'{@link Attribute} associato.
     * <p>Se l'identificatore non è presente, restituisce {@code null}.</p>
     *
     * @param id La stringa che rappresenta l'identificatore per cui si vuole cercare l'attributo.
     * @return L'{@link Attribute} associato all'identificatore, o {@code null} se non trovato.
     */
    public static Attribute lookup(String id) {
        return table.get(id);
    }

    /**
     * Restituisce una rappresentazione in formato {@code String} della {@link SymbolTable} utilizzando il metodo {@link HashMap#toString()}.
     *
     * @return Una rappresentazione in formato {@code String} della tabella.
     */
    public static String toStr() {
        return table.toString();
    }

    /**
     * Restituisce il numero di elementi presenti nella {@link SymbolTable}.
     *
     * @return La dimensione attuale della tabella.
     */
    public static int size() {
        return table.size();
    }
}