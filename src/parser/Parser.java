package parser;

import ast.*;
import scanner.LexicalException;
import scanner.*;
import token.*;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class Parser {
    private final Scanner sc;
    private Token opAssignOper;
    private Token opAssignVal;

    public Parser(Scanner scanner) {
        this.sc = scanner;
        opAssignOper = opAssignVal = null;
    }

    public NodeProgram parse() throws SyntacticException {
        return this.parsePrg();
    }

    private NodeProgram parsePrg() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parsePrg: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //Prg -> DSs $
            case TYFLOAT, TYINT, ID, PRINT, EOF -> {
                ArrayList<NodeDecSt> decSts = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(decSts);
            }
            default -> throw new SyntacticException(tk.getLine(), "TYFLOAT, TYINT, ID, PRINT or EOF", tk.getType());
        }
    }

    private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseDSs: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //DSs -> Dcl DSs
            case TYFLOAT, TYINT -> {
                NodeDecl decl = parseDcl();
                ArrayList<NodeDecSt> decSts = parseDSs();
                decSts.add(0, decl);
                return decSts;
            }
            //DSs -> Stm DSs
            case ID, PRINT -> {
                NodeStm stm = parseStm();
                ArrayList<NodeDecSt> decSts = parseDSs();
                decSts.add(0, stm);
                return decSts;
            }
            //DSs -> ε
            case EOF -> { return new ArrayList<>(); }
            default -> throw new SyntacticException(tk.getLine(), "TYFLOAT, TYINT, ID, PRINT or EOF", tk.getType());
        }
    }

    private NodeDecl parseDcl() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseDcl: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //Dcl -> Ty id DclP
            case TYFLOAT, TYINT -> {
                LangType type = parseTy();
                NodeId id = new NodeId(match(TokenType.ID).getVal());
                NodeExpr expr = parseDclP();
                return new NodeDecl(id, type, expr);
            }
            default -> throw new SyntacticException(tk.getLine(), "TYFLOAT or TYINT", tk.getType());
        }
    }

    private NodeExpr parseDclP() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseDclP: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //DclP -> ;
            case SEMI -> {
                match(TokenType.SEMI);
                return null;
            }
            //DclP -> = Exp ;
            case ASS -> {
                match(TokenType.ASS);
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);
                return expr;
            }
            default -> throw new SyntacticException(tk.getLine(), "SEMI or ASS", tk.getType());
        }
    }

    private LangType parseTy() throws SyntacticException {
        return anyMatch(TokenType.TYFLOAT, TokenType.TYINT).getType() == TokenType.TYFLOAT ? LangType.FLOAT : LangType.INT;
    }

    private NodeStm parseStm() throws SyntacticException {
        Token tk = anyMatch(TokenType.ID, TokenType.PRINT);

        System.out.println("parseStm: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //Stm -> id Op Exp ;
            case ID -> {
                //NodeId id = new NodeId(match(TokenType.ID).getVal());
                opAssignOper = parseOp();
                System.out.println("opAssignOper value: " + opAssignOper.getType() + " " + opAssignOper.getLine() + " " + opAssignOper.getVal());
                if(opAssignOper.getType() != TokenType.ASS) opAssignVal = tk;
                NodeExpr expr = parseExp();
                /*
                if (op.getType() == TokenType.OP_ASS) {
                    switch (op.getVal()) {
                        case "+=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.PLUS, expr);
                        case "-=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.MINUS, expr);
                        case "*=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.TIMES, expr);
                        case "/=" -> expr = new NodeBinOp(new NodeDeref(id), LangOper.DIV, expr);
                    }
                }
                */
                match(TokenType.SEMI);
                return new NodeAssign(new NodeId(tk.getVal()), expr);
            }
            //Stm -> print id ;
            case PRINT -> {
                //match(TokenType.PRINT);
                NodePrint nodePrint = new NodePrint(new NodeId(match(TokenType.ID).getVal()));
                match(TokenType.SEMI);
                return nodePrint;
            }
            default -> throw new SyntacticException(tk.getLine(), "ID or PRINT", tk.getType());
        }
    }

    private NodeStm parseStm_NICO_VERSION() throws SyntacticException {
        Token token = anyMatch(TokenType.PRINT, TokenType.ID);
        if(token.getType() == TokenType.PRINT) {
            NodePrint node = new NodePrint(new NodeId(match(TokenType.ID).getVal()));
            match(TokenType.SEMI);
            return node;
        }
        else {
            if(parseOp().getType() == TokenType.OP_ASS){
                opAssignOper = new UnaryOperator<Token>() {
                    @Override
                    public Token apply(Token tok) {
                        return switch (tok.getVal()) {
                            case "+=" -> new Token(TokenType.PLUS, tok.getLine());
                            case "-=" -> new Token(TokenType.MINUS, tok.getLine());
                            case "*=" -> new Token(TokenType.TIMES, tok.getLine());
                            case "/=" -> new Token(TokenType.DIVIDE, tok.getLine());
                            default -> null;
                        };
                    }
                }.apply(match(TokenType.ASS));
                if(opAssignOper != null) opAssignVal = token;
            }
            NodeExpr expr = parseExp();
            match(TokenType.SEMI);
            return new NodeAssign(new NodeId(token.getVal()), expr);
        }
    }

    private Token parseOp() throws SyntacticException {
        Token op = anyMatch(TokenType.ASS, TokenType.OP_ASS);
        if (op.getType() == TokenType.OP_ASS) {
            switch (op.getVal()) {
                case "+=" -> {
                    return new Token(TokenType.PLUS, op.getLine());
                }
                case "-=" -> {
                    return new Token(TokenType.MINUS, op.getLine());
                }
                case "*=" -> {
                    return new Token(TokenType.TIMES, op.getLine());
                }
                case "/=" -> {
                    return new Token(TokenType.DIVIDE, op.getLine());
                }
            }
        }
        return op;
    }

    private NodeExpr parseExp() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseExp: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //Exp -> Tr ExpP
            case INT, FLOAT, ID -> {
                NodeExpr tr = parseTr();
                return parseExpP(tr);
            }
            default -> throw new SyntacticException(tk.getLine(), "INT, FLOAT or ID", tk.getType());
        }
    }

    private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseExpP: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //ExpP -> - Tr ExpP, ExpP -> + Tr ExpP
            case MINUS, PLUS -> {
                NodeBinOp binOp = new NodeBinOp(
                        left,
                        anyMatch(TokenType.MINUS, TokenType.PLUS).getType() == TokenType.MINUS ? LangOper.MINUS : LangOper.PLUS,
                        parseTr()
                );
                return parseExpP(binOp);
            }
            //ExpP -> ε
            case SEMI -> {
                return left;
            }
            default -> throw new SyntacticException(tk.getLine(), "MINUS, PLUS or EOF", tk.getType());
        }
    }

    private NodeExpr parseTr() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseTr: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //Tr -> Val TrP
            case INT, FLOAT, ID -> {
                NodeExpr val = parseVal();
                return parseTrP(val);
            }
            default -> throw new SyntacticException(tk.getLine(), "INT, FLOAT or ID", tk.getType());
        }
    }

    private NodeExpr parseTrP(NodeExpr left) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseTrP: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //TrP -> * Val TrP, TrP -> / Val TrP
            case TIMES, DIVIDE -> {
                NodeBinOp binOp = new NodeBinOp(
                        left,
                        anyMatch(TokenType.TIMES, TokenType.DIVIDE).getType() == TokenType.TIMES ? LangOper.TIMES : LangOper.DIV,
                        parseVal()
                );
                return parseTrP(binOp);
            }
            //TrP -> ε
            case MINUS, PLUS, SEMI -> {
                return left;
            }
            default -> throw new SyntacticException(tk.getLine(), "TIMES, DIVIDE, MINUS, PLUS or SEMI", tk.getType());
        }
    }

    private NodeExpr parseVal() throws SyntacticException {
        Token tk = anyMatch(TokenType.ID, TokenType.INT, TokenType.FLOAT);

        System.out.println("parseVal: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            //Val -> TYINT, Val -> TYFLOAT
            case INT, FLOAT -> {
                match(tk.getType());
                return new NodeConst(tk.getVal(), tk.getType() == TokenType.INT ? LangType.INT : LangType.FLOAT);
            }
            //Val -> id
            case ID -> {
                return new NodeDeref(new NodeId(match(TokenType.ID).getVal()));
            }
            default -> throw new SyntacticException(tk.getLine(), "INT, FLOAT or ID", tk.getType());
        }
    }

    private Token match(TokenType type) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("match: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        if (type.equals(tk.getType())) return nextToken();
        else throw new SyntacticException(tk.getLine(), type.toString(), tk.getType());
    }

    private Token anyMatch(TokenType firstType, TokenType... types) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("anyMatch: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        if (firstType.equals(tk.getType())) return nextToken();
        for (TokenType type : types) {
            if (type.equals(tk.getType())) return nextToken();
        }
        throw new SyntacticException(tk.getLine(), firstType + " or " + TokenType.toStringEach(types), tk.getType());
    }

    private Token peekToken() throws SyntacticException {
        Token token;
        if(opAssignVal != null) token = opAssignVal;
        else if(opAssignOper != null) token = opAssignOper;
        else {
            try {
                token = sc.peekToken();
            } catch(LexicalException e) {
                throw new SyntacticException(e.getMessage(), e);
            }
        }
        return token;
    }

    private Token nextToken() throws SyntacticException {
        Token token;
        if(opAssignVal != null) {
            token = opAssignVal;
            opAssignVal = null;
        }
        else if(opAssignOper != null) {
            token = opAssignOper;
            opAssignOper = null;
        }
        else {
            try {
                return sc.nextToken();
            } catch (LexicalException e) {
                throw new SyntacticException(e.getMessage(), e);
            }
        }
        return token;
    }
}
