package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenType;

class TestToken {

	@Test
	void testCostruttore() {
		Token tk1 = new Token(TokenType.SEMI, 1);
		Token tk2 = new Token(TokenType.OP_ASS, 2, "=");

		assertEquals(TokenType.SEMI, tk1.getType());
		assertEquals(1, tk1.getLine());
		assertEquals(TokenType.OP_ASS, tk2.getType());
		assertEquals(2, tk2.getLine());
	}

	@Test
	void testCostruttoreValore() {
		Token tk1 = new Token(TokenType.TIMES, 1, "*");
		Token tk2 = new Token(TokenType.OP_ASS, 2, "+=");
		Token tk3 = new Token(TokenType.ID, 5, "temp");

		assertEquals(TokenType.TIMES, tk1.getType());
		assertEquals(1, tk1.getLine());
		assertEquals("*", tk1.getVal());
		assertEquals(TokenType.OP_ASS, tk2.getType());
		assertEquals(2, tk2.getLine());
		assertEquals("+=", tk2.getVal());
		assertEquals(TokenType.ID, tk3.getType());
		assertEquals(5, tk3.getLine());
		assertEquals("temp", tk3.getVal());
	}

	@Test
	void testToString() {
		Token token = new Token(TokenType.SEMI, 1);

		assertEquals("<SEMI, r:1>", token.toString());
	}

	@Test
	void testToStringValore() {
		Token token = new Token(TokenType.ID, 1, "temp");

		assertEquals("<ID, r:1, temp>", token.toString());
	}
}
