package symbolTable;

import java.util.ArrayList;

public class Register {
    private static ArrayList<Character> registers = generateRegisters();

    /**
     * Ritorna un registro <em>dc</em> disponibile.
     *
     * @return il prossimo registro disponibile
     */
    public static char newRegister() throws RegisterException {
        try {
            return registers.remove(0);
        } catch (IndexOutOfBoundsException e) {
            throw new RegisterException("No registers available", e);
        }
    }

    /**
     * Genera un nuovo registro <em>dc</em> disponibile.
     */
    public static void init() { registers = generateRegisters(); }

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
