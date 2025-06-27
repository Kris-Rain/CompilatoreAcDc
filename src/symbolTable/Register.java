package symbolTable;

import java.util.ArrayList;

/**
 * La classe {@code Register} gestisce i registri disponibili per l'allocazione durante la generazione del codice <em>dc</em> a partire da un codice <em>ac</em>.
 * <p>I registri sono rappresentati da caratteri {@code char} che identificano i registri <em>dc</em> e vengono utilizzati per memorizzare i valori degli identificatori.
 * Il totale dei registri disponibili è 64, che include lettere minuscole, maiuscole, numeri e due caratteri speciali.</p>
 * <p>La classe fornisce metodi per ottenere un registro disponibile tramite il metodo {@link #newRegister()} e per reimpostare l'elenco dei registri tramite il metodo {@link #init()}.</p>
 * <p>Prima di utilizzare la classe, è consigliato utilizzare quest'ultimo metodo.</p>
 *
 * @see SymbolTable
 * @see SymbolTable.Attribute
 * @author Kristian Rigo (matr. 20046665)
 */
public class Register {
    private static ArrayList<Character> registers = generateRegisters();

    /**
     * Restituisce un registro disponibile.
     * <p>Se non ci sono registri disponibili, viene sollevata un'eccezione di tipo {@link RegisterException}.</p>
     *
     * @return Il prossimo registro <em>dc</em> disponibile come carattere {@code char}.
     * @throws RegisterException Se non ci sono registri disponibili.
     */
    public static char newRegister() throws RegisterException {
        try {
            return registers.remove(0);
        } catch (IndexOutOfBoundsException e) {
            throw new RegisterException("Nessun registro disponibile", e);
        }
    }

    /**
     * Reimposta l'elenco dei registri disponibili.
     * <p>Questo metodo rigenera l'elenco completo dei registri, rendendoli nuovamente disponibili per l'allocazione.</p>
     * <p>Il loro totale è 64.</p>
     */
    public static void init() {
        registers = generateRegisters();
    }

    /**
     * Genera l'elenco completo dei registri disponibili.
     * <p>I registri includono lettere minuscole, maiuscole, numeri e due caratteri speciali.</p>
     *
     * @return Una lista di caratteri che rappresentano i registri disponibili.
     */
    private static ArrayList<Character> generateRegisters() {
        ArrayList<Character> registers = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            registers.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            registers.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            registers.add(c);
        }
        registers.add('_');
        registers.add('$');

        return registers;
    }
}