package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;
import parser.Parser;
import token.*;

/**
 * La classe {@link Scanner} che si occupa dell'analisi lessicale del linguaggio <em>ac</em>.
 * <p>Lo scanner è un DFA (automa a stati finiti) che legge un file sorgente <em>ac</em> e restituisce i token uno alla volta.</p>
 * <p>In caso di errore lessicale, viene sollevata un'eccezione di tipo {@link LexicalException} con un messaggio descrittivo.</p>
 *
 * @see LexicalException
 * @see Token
 * @see TokenType
 * @see Parser
 * @author Kristian Rigo (matr. 20046665)
 */
public class Scanner {
    char nextChar;
    final char EOF = (char) -1;
    private int line;
    private final PushbackReader buffer;
    private ArrayList<Character> skpChars;
    private ArrayList<Character> letters;
    private ArrayList<Character> digits;
    private HashMap<Character, TokenType> operTkType;
    private HashMap<Character, TokenType> delimTkType;
    private HashMap<String, TokenType> keyWordsTkType;
    private Token nextTk;

    /**
     * Inizializza i campi della classe, inclusi gli insiemi di caratteri e le mappe per operatori e parole chiave.
     */
    private void initialize() {
        skpChars = new ArrayList<>();
        skpChars.add(' ');
        skpChars.add('\t');
        skpChars.add('\n');
        skpChars.add('\r');
        skpChars.add(EOF);

        letters = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            letters.add(c);
        }
        digits = new ArrayList<>();
        for (char c = '0'; c <= '9'; c++) {
            digits.add(c);
        }
        operTkType = new HashMap<>();
        operTkType.put('+', TokenType.PLUS);
        operTkType.put('-', TokenType.MINUS);
        operTkType.put('*', TokenType.TIMES);
        operTkType.put('/', TokenType.DIVIDE);

        delimTkType = new HashMap<>();
        delimTkType.put('=', TokenType.ASS);
        delimTkType.put(';', TokenType.SEMI);

        keyWordsTkType = new HashMap<>();
        keyWordsTkType.put("PRINT", TokenType.PRINT);
        keyWordsTkType.put("INT", TokenType.TYINT);
        keyWordsTkType.put("FLOAT", TokenType.TYFLOAT);
    }

    /**
     * Costruttore della classe {@link Scanner} a partire dal nome del file sorgente <em>ac</em> specificato.<br>
     * <p>Inizializza il buffer di lettura e i campi necessari per l'analisi lessicale.</p>
	 * <p>Se il file non viene trovato, viene sollevata un'eccezione di tipo {@link FileNotFoundException}.</p>
     *
     * @param fileName Il nome del file sorgente da analizzare.
     * @throws FileNotFoundException Se il file specificato non viene trovato.
     */
    public Scanner(String fileName) throws FileNotFoundException {
        this.buffer = new PushbackReader(new FileReader(fileName));
        this.nextTk = null;
        this.line = 1;

        // Inizializzo i campi skpChars, letters, digits, operTkType, delimTkType, keyWordsTkType
        initialize();
    }

    /**
     * Restituisce il prossimo token senza consumarlo.
	 * <p>Se il file è terminato, restituisce un token di tipo {@link TokenType#EOF}.</p>
	 * <p>Il metodo lancia un'eccezione di tipo {@link LexicalException} nel caso in cui:
	 * <ul>
	 *    <li>Il carattere letto non è valido (errore lessicale);</li>
	 *    <li>Si verifica un {@link IOException}: in questo caso specifico viene lanciata l'eccezione con la relativa riga.</li>
	 * </ul>
	 * </p>
	 *
	 * @return Il prossimo token.
     * @throws LexicalException Se si verifica un errore lessicale.
     */
    public Token peekToken() throws LexicalException {
        if (nextTk == null) nextTk = nextToken();

        return nextTk;
    }

    /**
     * Restituisce il prossimo token e lo consuma.
     * <p>Se il file è terminato, restituisce un token di tipo {@link TokenType#EOF}.</p>
	 * <p>Il metodo lancia un'eccezione di tipo {@link LexicalException} nel caso in cui:
	 * <ul>
	 *    <li>Il carattere letto non è valido (errore lessicale);</li>
	 *    <li>Si verifica un {@link IOException}: in questo caso specifico viene lanciata l'eccezione con la relativa riga.</li>
	 * </ul>
	 * </p>
	 *
     * @return Il prossimo token.
     * @throws LexicalException Se si verifica un errore lessicale.
     */
    public Token nextToken() throws LexicalException {
        if (nextTk != null) {
            Token tempTk = nextTk;
            nextTk = null;
            return tempTk;
        }

        do {
            nextChar = peekChar();
            if (skpChars.contains(nextChar)) {
                System.out.println("Current char (skpChar): " + nextChar);
                if (nextChar == EOF) return new Token(TokenType.EOF, line);
                if (nextChar == '\n') line++;
                readChar(); // Consuma il carattere
            }
        } while (skpChars.contains(nextChar));

        if (letters.contains(nextChar)) return scanId();
        if (operTkType.containsKey(nextChar) || delimTkType.containsKey(nextChar)) return scanOperator();
        if (digits.contains(nextChar)) return scanNumber();
        throw new LexicalException(this.line, nextChar);
    }

    /**
     * Effettua il parsing di un identificatore o di una parola chiave.
	 * <p>Nel caso in cui venga trovata una parola chiave, viene restituito un token di tipo {@link TokenType} relativo alla parola chiave.<br>
	 * Ad esempio: {@link TokenType#PRINT}, {@link TokenType#TYINT} o {@link TokenType#TYFLOAT}.</p>
	 * <p>Alternativamente, viene restituito un token di tipo {@link TokenType#ID}.</p>
     *
     * @return Un token di tipo {@link TokenType#ID} o una parola chiave.
     * @throws LexicalException Se si verifica un errore lessicale.
     */
    private Token scanId() throws LexicalException {
        StringBuilder id = new StringBuilder();

        while (letters.contains(nextChar = peekChar()) || digits.contains(nextChar)) {
            System.out.println("Current char (id): " + nextChar);
            id.append(readChar());
        }
        if (keyWordsTkType.containsKey(id.toString().toUpperCase())) {
            return new Token(keyWordsTkType.get(id.toString().toUpperCase()), line);
        }
        return new Token(TokenType.ID, line, id.toString());
    }

    /**
     * Effettua il parsing di un operatore o di un delimitatore.
	 * <p>Nel caso in cui venga trovato un operatore, se il successivo è un uguale (=), viene restituito un token di tipo {@link TokenType#OP_ASS}</p>
	 * <p>Altrimenti, viene restituito un token di tipo {@link TokenType} relativo al singolo operatore.<br>
	 * Ad esempio: {@link TokenType#PLUS}, {@link TokenType#MINUS}, {@link TokenType#TIMES} o {@link TokenType#DIVIDE}.</p>
	 * <p>Alternativamente viene restituito un token di tipo {@link TokenType} che può essere {@link TokenType#ASS} oppure {@link TokenType#SEMI}</p>
     *
     * @return Un token corrispondente all'operatore di assegnamento composto, operatore, assegnazione o al delimitatore.
     * @throws LexicalException Se si verifica un errore lessicale.
     */
    private Token scanOperator() throws LexicalException {
        char nextCharTemp = readChar();
        System.out.println("Current char (op_ass or delim): " + nextCharTemp);
        if (nextCharTemp != '=' && nextCharTemp != ';') {
            if (peekChar() == '=') {
                System.out.println("Current char (op_ass): " + peekChar());
                readChar();
                return new Token(TokenType.OP_ASS, line, nextCharTemp + "=");
            }
            return new Token(operTkType.get(nextCharTemp), line, String.valueOf(nextCharTemp));
        }
        return new Token(delimTkType.get(nextCharTemp), line);
    }

    /**
     * Effettua il parsing di un numero intero o decimale.
	 * <p>Continuo a leggere e consumare token in un {@code while} sia nel caso in cui siano numeri o nel caso in cui siano lettere,
	 * in questo ultimo caso però imposto una variabile {@code boolean} a {@code true} nel caso in cui fosse vero.
	 * Se arrivo in fondo e non trovo mai un {@code "."} e mai una lettera, viene restituito un token di tipo {@link TokenType#INT}</p>
	 * <p>Altrimenti lancio l'eccezione {@link LexicalException} passando per argomenti la riga e il valore nel formato {@code String}.</p>
	 *
     * @return Un token di tipo {@link TokenType#INT} o {@link TokenType#FLOAT}.
     * @throws LexicalException Se si verifica un errore lessicale.
     */
    private Token scanNumber() throws LexicalException {
        StringBuilder val = new StringBuilder();
        boolean isLetter = false;

        while (digits.contains(nextChar) || letters.contains(nextChar)) {
            System.out.println("Current char (digit): " + nextChar);
            if (letters.contains(nextChar)) isLetter = true;
            val.append(readChar());
            System.out.println("Val: " + val);
            nextChar = peekChar();
        }

        if (nextChar == '.') {
            val.append(readChar());
            System.out.println("Val: " + val);
            return scanFloat(val);
        }

        if (!isLetter) return new Token(TokenType.INT, line, val.toString());
        throw new LexicalException(this.line, val.toString());
    }

    /**
     * Effettua il parsing di un numero decimale.
	 * <p>Se però nel {@link Scanner#scanNumber()} trovo un {@code "."} allora entro in questo metodo.
	 * Anche qui continuo a leggere e consumare token in un {@code while} sia nel caso in cui siano numeri, lettere o ulteriori {@code "."}.
	 * In questi due ultimi casi però imposto due variabile {@code boolean} a {@code true} nel caso in cui fossero vero.
	 * In tutto questo incremento anche un contatore {@code count} che mi serve per capire se sono arrivato a 5 caratteri dopo il {@code "."}.
	 * Se arrivo in fondo e non trovo mai ne un {@code "."} ne una lettera ne ho trovato altri {@code "."}, viene restituito un token di tipo {@link TokenType#FLOAT}.</p>
	 * <p>Altrimenti lancio l'eccezione {@link LexicalException} passando per argomenti la riga e il valore nel formato {@code String}.</p>
	 *
     * @param val Il valore parziale del numero.
     * @return Un token di tipo {@link TokenType#FLOAT}.
     * @throws LexicalException Se si verifica un errore lessicale.
     */
    private Token scanFloat(StringBuilder val) throws LexicalException {
        int count = 0;
        boolean tooManyPoints = false, isLetter = false;
        nextChar = peekChar();

        while (digits.contains(nextChar) || nextChar == '.' || letters.contains(nextChar)) {
            System.out.println("Current char (float): " + nextChar);
            if (nextChar == '.') tooManyPoints = true;
            if (letters.contains(nextChar)) isLetter = true;
            val.append(readChar());
            System.out.println("Val: " + val + " count: " + count);
            nextChar = peekChar();
            count++;
        }

        if ((count <= 5 && !tooManyPoints && !isLetter)) return new Token(TokenType.FLOAT, line, val.toString());
        throw new LexicalException(this.line, val.toString());
    }

    /**
     * Legge il prossimo carattere dal buffer.
     *
     * @return Il carattere letto.
     * @throws LexicalException Se si verifica un errore di I/O.
     */
    private char readChar() throws LexicalException {
        try {
            return ((char) this.buffer.read());
        } catch (IOException e) {
            throw new LexicalException(this.line);
        }
    }

    /**
     * Legge il prossimo carattere dal buffer senza consumarlo.
     *
     * @return Il carattere letto.
     * @throws LexicalException Se si verifica un errore di I/O.
     */
    private char peekChar() throws LexicalException {
        char c;

        try {
            c = (char) buffer.read();
            buffer.unread(c);
        } catch (IOException e) {
            throw new LexicalException(this.line);
        }
        return c;
    }
}