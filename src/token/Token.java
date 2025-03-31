package token;

public class Token {

	private final int line;
	private final TokenType type;
	private final String val;
	
	public Token(TokenType tipo, int line, String val) {
		this.type = tipo;
		this.line = line;
		this.val = val;
	}
	
	public Token(TokenType tipo, int line) {
		this(tipo, line, null);
	}

    // Getters per i campi
	public int getLine() {
		return this.line;
	}
	public TokenType getType(){
		return this.type;
	}
	public String getVal() {
		return this.val;
	}

	public String toString() {
		if(this.getVal() == null) return "<"+this.getType()+", r:"+this.getLine()+">";
		return "<"+this.getType()+", r:"+this.getLine()+", "+this.getVal()+">";
	}
	
}
