package parser;

import scanner.*;
import token.*;

public class Parser {
    private final Scanner sc;

    public Parser(Scanner scanner) {
        this.sc = scanner;
    }

    private Token match(TokenType type) throws SyntacticException {
        try {
            Token tk = sc.peekToken();
            if (type.equals(tk.getType())) return sc.nextToken();
            else throw new SyntacticException(tk.getLine(), type.toString(), tk.getType());
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception: " + e.getMessage());
        }
    }

    private void parseNT() {

    }

    private void parsePrg() throws SyntacticException {
        try {
            Token tk = sc.peekToken();
            switch (tk.getType()) {
                case TYFLOAT, TYINT, ID, PRINT, EOF -> { //Prg -> DSs $
                    parseDSs();
                    match(TokenType.EOF);
                    return;
                }
                default -> throw new SyntacticException(tk.getLine(),"TYFLOAT, TYINT, ID, PRINT or EOF", tk.getType());
            }
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception: " + e.getMessage());
        }
    }

    private void parseDSs() throws SyntacticException {
        try {
            Token tk = sc.peekToken();
            switch (tk.getType()) {
                case TYFLOAT, TYINT -> { //DSs -> Dcl DSs
                    parseDcl();
                    parseDSs();
                    return;
                }
                case ID, PRINT -> { //DSs -> Stm DSs
                    parseStm();
                    parseDSs();
                    return;
                }
                case EOF -> { //DSs -> ε
                    match(TokenType.EOF);
                    return;
                }
                default -> throw new SyntacticException(tk.getLine(), "TYFLOAT, TYINT, ID, PRINT or EOF", tk.getType());
            }
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception: " + e.getMessage());
        }
    }

    private void parseDcl() throws SyntacticException {
        try {
            Token tk = sc.peekToken();
            switch (tk.getType()) {
                case TYFLOAT, TYINT -> { //Dcl -> Ty id DclP
                    parseTy();
                    match(TokenType.ID);
                    parseDclP();
                    return;
                }
                default -> throw new SyntacticException(tk.getLine(), "TYFLOAT or TYINT", tk.getType());
            }
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception: " + e.getMessage());
        }
    }

    private void parseTy() throws SyntacticException {
        try {
            Token tk = sc.peekToken();
            switch (tk.getType()) {
                case TYFLOAT -> { //Ty -> TYFLOAT
                    match(TokenType.TYFLOAT);
                    return;
                }
                case TYINT -> { //Ty -> TYINT
                    match(TokenType.TYINT);
                    return;
                }
                default -> throw new SyntacticException(tk.getLine(), "TYFLOAT or TYINT", tk.getType());
            }
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception: " + e.getMessage());
        }
    }

    private void parseDclP() throws SyntacticException {
        try {
            Token tk = sc.peekToken();
            if (tk.getType().equals(TokenType.SEMI)) { //DclP -> ;
                    match(TokenType.SEMI);
                    return;
            }
            throw new SyntacticException(tk.getLine(), "SEMI", tk.getType());
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception: " + e.getMessage());
        }
    }

    private void parseStm() throws SyntacticException {
        try {
            Token tk = sc.peekToken();
            if (tk.getType().equals(TokenType.PRINT)) { //Stm -> print id ;
                match(TokenType.PRINT);
                match(TokenType.ID);
                match(TokenType.SEMI);
                return;
            }
            throw new SyntacticException(tk.getLine(), "PRINT", tk.getType());
        } catch (LexicalException e) {
            throw new SyntacticException("Lexical Exception: " + e.getMessage());
        }
    }

    public void parse() throws SyntacticException {
        this.parsePrg();
    }
}
