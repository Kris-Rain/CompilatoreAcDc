package token;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum TokenType {
	INT,
	FLOAT,
	TYINT,
	TYFLOAT,
	ID,
	ASS,
	PLUS,
	MINUS,
	DIVIDE,
	TIMES,
	OP_ASS,
	PRINT,
	SEMI,
	EOF;

	public static String toStringEach(TokenType[] types) {
		return Arrays.stream(types).map(Enum::toString).collect(Collectors.joining(", "));
	}
}
