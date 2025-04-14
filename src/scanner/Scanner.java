package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;

import token.*;

public class Scanner {
	char nextChar;
	final char EOF = (char) -1; 
	private int line;
	private PushbackReader buffer;
	//insieme caratteri di skip (include EOF) e inizializzazione
	private ArrayList<Character> skpChars;
	//insieme lettere
	private ArrayList<Character> letters;
	//insieme cifre
	private ArrayList<Character> digits;
	//mapping fra caratteri '+', '-', '*', '/'  e il TokenType corrispondente
	private HashMap<Character, TokenType> operTkType;
	//mapping fra caratteri '=', ';' e il e il TokenType corrispondente
	private HashMap<Character, TokenType> delimTkType;
	//mapping fra le stringhe "print", "float", "int" e il TokenType  corrispondente
	private HashMap<String, TokenType> keyWordsTkType;
	//token corrente
	private Token nextTk;

	//inizializzazione dei campi
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

	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		this.nextTk = null;
		this.line = 1;

		// Inizializzo i campi skpChars, letters, digits, operTkType, delimTkType, keyWordsTkType
		initialize();
	}

	public Token peekToken() throws LexicalException {
		if (nextTk == null) nextTk = nextToken();

		return nextTk;
	}

	// nextToken ritorna il prossimo token nel file di input e legge i caratteri del token ritornato (avanzando fino al carattere successivo all'ultimo carattere del token)
	public Token nextToken() throws LexicalException {
		if(nextTk != null) {
			Token tempTk = nextTk;
			nextTk = null;
			return tempTk;
		}

        // nextChar contiene il prossimo carattere dell'input (non consumato).
		// Avanza nel buffer leggendo i carattere in skipChars incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF
		do {
			nextChar = peekChar();
			if (skpChars.contains(nextChar)) {
				System.out.println("Current char (skpChar): " + nextChar);
				if (nextChar == EOF) return new Token(TokenType.EOF, line);
				if (nextChar == '\n') line++;
				readChar(); //consumo il carattere
			}
		} while (skpChars.contains(nextChar));

		// Se nextChar e' in letters return scanId() che deve generare o un Token ID o parola chiave
		if (letters.contains(nextChar)) return scanId();
		// Se nextChar e' o in operators oppure delimitatore ritorna il Token associato con l'operatore o il delimitatore
		// Attenzione agli operatori di assegnamento!
		// Se nextChar e' ; o = ritorna il Token associato
		if (operTkType.containsKey(nextChar) || delimTkType.containsKey(nextChar)) return scanOperator();
		// Se nextChar e' in numbers return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token
		if (digits.contains(nextChar)) return scanNumber();
		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.
		throw new LexicalException(this.line, nextChar);
    }

	private Token scanId() throws LexicalException {
		StringBuilder id = new StringBuilder();

        while (letters.contains(nextChar = peekChar()) || digits.contains(nextChar)) {
            System.out.println("Current char (id): " + nextChar);
            id.append(readChar());
        }
        if (keyWordsTkType.containsKey(id.toString().toUpperCase())){
            return new Token(keyWordsTkType.get(id.toString().toUpperCase()), line);
        }
        return new Token(TokenType.ID, line, id.toString());
    }

	private Token scanOperator() throws LexicalException {
        char nextCharTemp = readChar();
        System.out.println("Current char (op_ass or delim): " + nextCharTemp);
        if(nextCharTemp != '=' && nextCharTemp != ';'){
            if(peekChar() == '='){
				System.out.println("Current char (op_ass): " + peekChar());
                readChar();
                return new Token(TokenType.OP_ASS, line,nextCharTemp + "=");
            }
            return new Token(operTkType.get(nextCharTemp), line, String.valueOf(nextCharTemp));
        }
        return new Token(delimTkType.get(nextCharTemp), line);
    }
		
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

        if(nextChar == '.') {
			val.append(readChar());
			System.out.println("Val: " + val);
			return scanFloat(val);
		}

        if(!isLetter) return new Token(TokenType.INT, line, val.toString());
		throw new LexicalException(this.line, val.toString());
    }

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

		if((count <= 5 && !tooManyPoints && !isLetter)) return new Token(TokenType.FLOAT, line, val.toString());
		throw new LexicalException(this.line, val.toString());
	}

	private char readChar() throws LexicalException  {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new LexicalException(this.line);
		}
	}

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
