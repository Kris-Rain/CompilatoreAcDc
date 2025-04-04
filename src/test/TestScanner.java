package test;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.LexicalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scanner.*;
import token.*;
import java.io.FileNotFoundException;
import java.util.HashMap;

class TestScanner {
	private final HashMap<String, Scanner> scanners = new HashMap<>();
	private final String filePath = "src/test/data/testScanner/";
	private final String[] testFiles = new String[] {
			"testEOF.txt",
			"testOpsDels.txt",
			"testKeywords.txt",
			"testIdKeyWords.txt",
			"testId.txt",
			"testInt.txt",
			"testFloat.txt",
			"testGenerale.txt",
			"caratteriNonCaratteri.txt",
			"caratteriSkip",
			"erroriNumbers.txt"
	};

	@BeforeEach
	public void setUpScanners() throws FileNotFoundException {
        for (String testFile : testFiles) {
            scanners.put(testFile, new Scanner(filePath + testFile));
        }
	}

	@Test
	void testEOF() throws LexicalException {
		assertEquals(TokenType.EOF, scanners.get("testEOF.txt").nextToken().getType());
	}

	@Test
	void testOpsDels() throws LexicalException {
		Scanner scanner = scanners.get("testOpsDels.txt");

		assertEquals("<PLUS, r:1, +>", scanner.nextToken().toString());
		assertEquals("<OP_ASS, r:1, /=>", scanner.nextToken().toString());

		assertEquals("<MINUS, r:2, ->", scanner.nextToken().toString());
		assertEquals("<TIMES, r:2, *>", scanner.nextToken().toString());

		assertEquals("<DIVIDE, r:3, />", scanner.nextToken().toString());

		assertEquals("<OP_ASS, r:5, +=>", scanner.nextToken().toString());

		assertEquals("<ASS, r:6>", scanner.nextToken().toString());
		assertEquals("<OP_ASS, r:6, -=>", scanner.nextToken().toString());

		assertEquals("<MINUS, r:8, ->", scanner.nextToken().toString());
		assertEquals("<ASS, r:8>", scanner.nextToken().toString());
		assertEquals("<OP_ASS, r:8, *=>", scanner.nextToken().toString());

		assertEquals("<SEMI, r:10>", scanner.nextToken().toString());
	}

	@Test
	void testKeywords() throws LexicalException {
		Scanner scanner = scanners.get("testKeywords.txt");

		assertEquals(TokenType.PRINT, scanner.nextToken().getType());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getType());
		assertEquals(TokenType.TYINT, scanner.nextToken().getType());
	}

	@Test
	void testIdKeywords() throws LexicalException {
		Scanner scanner = scanners.get("testIdKeyWords.txt");

		assertEquals(TokenType.TYINT, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getType());
		assertEquals(TokenType.PRINT, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.TYINT, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
	}

	@Test
	void testId() throws LexicalException {
		Scanner scanner = scanners.get("testId.txt");

		assertEquals("jskjdsfhkjdshkf", scanner.nextToken().getVal());
		assertEquals("printl", scanner.nextToken().getVal());
		assertEquals("ffloat", scanner.nextToken().getVal());
		assertEquals("hhhjj", scanner.nextToken().getVal());
	}

	@Test
	void testInt() throws LexicalException {
		Scanner scanner = scanners.get("testInt.txt");

		assertEquals("0050", scanner.nextToken().getVal());
		assertEquals("698", scanner.nextToken().getVal());
		assertEquals("560099", scanner.nextToken().getVal());
		assertEquals("1234", scanner.nextToken().getVal());
	}

	@Test
	void testFloat() throws LexicalException {
		Scanner scanner = scanners.get("testFloat.txt");

		assertEquals("098.8095", scanner.nextToken().getVal());
		assertEquals("0.", scanner.nextToken().getVal());
		assertEquals("98.", scanner.nextToken().getVal());
		assertEquals("89.99999", scanner.nextToken().getVal());
	}

	@Test
	void testGenerale() throws LexicalException {
		Scanner scanner = scanners.get("testGenerale.txt");

		assertEquals(TokenType.TYINT, scanner.peekToken().getType());
		assertEquals(TokenType.TYINT, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.peekToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.SEMI, scanner.peekToken().getType());
		assertEquals(TokenType.SEMI, scanner.nextToken().getType());

		assertEquals(TokenType.ID, scanner.peekToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.OP_ASS, scanner.peekToken().getType());
		assertEquals(TokenType.OP_ASS, scanner.nextToken().getType());
		assertEquals(TokenType.TYFLOAT, scanner.peekToken().getType());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getType());
		assertEquals(TokenType.SEMI, scanner.peekToken().getType());
		assertEquals(TokenType.SEMI, scanner.nextToken().getType());

		assertEquals(TokenType.TYFLOAT, scanner.peekToken().getType());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.peekToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.SEMI, scanner.peekToken().getType());
		assertEquals(TokenType.SEMI, scanner.nextToken().getType());

		assertEquals(TokenType.ID, scanner.peekToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.ASS, scanner.peekToken().getType());
		assertEquals(TokenType.ASS, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.peekToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.PLUS, scanner.peekToken().getType());
		assertEquals(TokenType.PLUS, scanner.nextToken().getType());
		assertEquals(TokenType.TYFLOAT, scanner.peekToken().getType());
		assertEquals(TokenType.TYFLOAT, scanner.nextToken().getType());
		assertEquals(TokenType.SEMI, scanner.peekToken().getType());
		assertEquals(TokenType.SEMI, scanner.nextToken().getType());

		assertEquals(TokenType.PRINT, scanner.peekToken().getType());
		assertEquals(TokenType.PRINT, scanner.nextToken().getType());
		assertEquals(TokenType.ID, scanner.peekToken().getType());
		assertEquals(TokenType.ID, scanner.nextToken().getType());
		assertEquals(TokenType.SEMI, scanner.peekToken().getType());
		assertEquals(TokenType.SEMI, scanner.nextToken().getType());
	}

	@Test
	void testCaratteriNonCaratteri() {
		Scanner scanner = scanners.get("caratteriNonCaratteri.txt");

		assertThrows(LexicalException.class, scanner::nextToken);
	}

	@Test
	void testCaratteriSkip() throws LexicalException {
		Scanner scanner = scanners.get("caratteriSkip");

		while(scanner.nextToken().getType() != TokenType.EOF) scanner.nextToken();
		assertEquals(TokenType.EOF, scanner.nextToken().getType());
	}

	@Test
	void testErroriNumbers() throws LexicalException {
		Scanner scanner = scanners.get("erroriNumbers.txt");

		assertEquals("0", scanner.nextToken().getVal());
		assertEquals("33", scanner.nextToken().getVal());
		LexicalException e = Assertions.assertThrows(LexicalException.class, scanner::nextToken);
		assertEquals("Lexical error at line 3: character sequence " + "'123.121212'" + " not recognized", e.getMessage());
		e = Assertions.assertThrows(LexicalException.class, scanner::nextToken);
		assertEquals("Lexical error at line 5: character sequence " + "'123.123.123'" + " not recognized", e.getMessage());
	}
}

