package symbolTable;

import ast.LangType;
import java.util.HashMap;

public class SymbolTable {

    private static HashMap<String, Attribute> table = new HashMap<>();

    public static class Attribute {
        private LangType type;
        private char register;

        public Attribute(LangType type) {
            this.type = type;
            try {
                this.register = Register.newRegister();
            } catch (RegisterException e) {
                this.register = (char) -1;
            }
        }

        public LangType getType() {
            return this.type;
        }

        /**
         * Ritorna il registro {@code char} di questo attributo.
         *
         * @return il registro associato a questo attributo
         */
        public char getRegister() {
            return register;
        }
    }

    public static void init() {
        table = new HashMap<>();
    }

    public static boolean enter(String id, Attribute entry) {
        if (table.containsKey(id)) return false; // already defined
        table.put(id, entry);
        return true; // successfully added
    }

    public static Attribute lookup(String id) { return table.get(id); }

    public static String toStr() {
        return table.toString();
    }

    public static int size() {
        return table.size();
    }
}
