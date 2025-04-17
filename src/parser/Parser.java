package parser;

import ast.*;
import scanner.LexicalException;
import scanner.*;
import token.*;
import java.util.ArrayList;

/**
 * Classe responsabile del parsing del linguaggio <em>ac</em>, implementata come un parser top-down a discesa ricorsiva.
 * Riguarda dunque l'analisi sintattica del linguaggio.
 * <p>La classe {@code Parser} utilizza uno {@link Scanner} per ottenere i token uno alla volta e costruire l'AST del programma.</p>
 * <p>In caso di errori sintattici, viene lanciata un'eccezione di tipo {@link SyntacticException} con un messaggio descrittivo.</p>
 *
 * @see Scanner
 * @see SyntacticException
 * @see NodeProgram
 * @see Token
 * @author Kristian Rigo (matr. 20046665)
 */
public class Parser {
    private final Scanner sc;
    private Token opAssignOper;
    private Token opAssignVal;

    /**
     * Costruisce un nuovo oggetto {@link Parser} utilizzando lo {@link Scanner} specificato.
     *
     * @param scanner Lo scanner da utilizzare per il parsing.
     */
    public Parser(Scanner scanner) {
        this.sc = scanner;
        opAssignOper = opAssignVal = null;
    }

    /**
     * Avvia il parsing del programma.
     * <br>Se il parsing ha successo, restituisce un oggetto {@link NodeProgram} che rappresenta l'AST del programma.
     * <br>Se fallisce in caso di errore sintattico, viene lanciata un'eccezione {@link SyntacticException}.
     *
     * @return Un oggetto {@link NodeProgram} che rappresenta l'AST del programma.
     * @throws SyntacticException Se si verifica un errore sintattico durante il parsing.
     */
    public NodeProgram parse() throws SyntacticException { return this.parsePrg(); }

    /**
     * Effettua il parsing del programma principale.
     *
     * @return Un oggetto {@link NodeProgram} che rappresenta il nodo principale dell'AST.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeProgram parsePrg() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parsePrg: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case TYFLOAT, TYINT, ID, PRINT, EOF -> {
                ArrayList<NodeDecSt> decSts = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(decSts);
            }
            default -> throw new SyntacticException(tk.getLine(), "TYFLOAT, TYINT, ID, PRINT or EOF", tk.getType());
        }
    }

    /**
     * Effettua il parsing di una lista di dichiarazioni o istruzioni.
     *
     * @return Una lista di nodi {@link NodeDecSt} che rappresentano dichiarazioni o istruzioni.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseDSs: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case TYFLOAT, TYINT -> {
                NodeDecl decl = parseDcl();
                ArrayList<NodeDecSt> decSts = parseDSs();
                decSts.add(0, decl);
                return decSts;
            }
            case ID, PRINT -> {
                NodeStm stm = parseStm();
                ArrayList<NodeDecSt> decSts = parseDSs();
                decSts.add(0, stm);
                return decSts;
            }
            case EOF -> { return new ArrayList<>(); }
            default -> throw new SyntacticException(tk.getLine(), "TYFLOAT, TYINT, ID, PRINT or EOF", tk.getType());
        }
    }

    /**
     * Effettua il parsing di una dichiarazione.
     *
     * @return Un oggetto {@link NodeDecl} che rappresenta una dichiarazione.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeDecl parseDcl() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseDcl: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case TYFLOAT, TYINT -> {
                LangType type = parseTy();
                NodeId id = new NodeId(match(TokenType.ID).getVal());
                NodeExpr expr = parseDclP();
                return new NodeDecl(id, type, expr);
            }
            default -> throw new SyntacticException(tk.getLine(), "TYFLOAT or TYINT", tk.getType());
        }
    }

    /**
     * Effettua il parsing della parte opzionale di una dichiarazione.
     *
     * @return Un oggetto {@link NodeExpr} che rappresenta l'espressione associata alla dichiarazione, o null se assente.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeExpr parseDclP() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseDclP: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case SEMI -> {
                match(TokenType.SEMI);
                return null;
            }
            case ASS -> {
                match(TokenType.ASS);
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);
                return expr;
            }
            default -> throw new SyntacticException(tk.getLine(), "SEMI or ASS", tk.getType());
        }
    }

    /**
     * Effettua il parsing di un tipo di dato.
     *
     * @return Un oggetto {@link LangType} che rappresenta il tipo di dato.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private LangType parseTy() throws SyntacticException {
        return anyMatch(TokenType.TYFLOAT, TokenType.TYINT).getType() == TokenType.TYFLOAT ? LangType.FLOAT : LangType.INT;
    }

    /**
     * Effettua il parsing di un'istruzione.
     *
     * @return Un oggetto {@link NodeStm} che rappresenta l'istruzione.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeStm parseStm() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseStm: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case ID -> {
                NodeId id = new NodeId(match(TokenType.ID).getVal());
                opAssignOper = parseOp();
                if (opAssignOper != null) opAssignVal = tk;
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);
                return new NodeAssign(id, expr);
            }
            case PRINT -> {
                match(TokenType.PRINT);
                NodePrint nodePrint = new NodePrint(new NodeId(match(TokenType.ID).getVal()));
                match(TokenType.SEMI);
                return nodePrint;
            }
            default -> throw new SyntacticException(tk.getLine(), "ID or PRINT", tk.getType());
        }
    }

    /**
     * Effettua il parsing di un operatore di assegnamento.
     *
     * @return Un oggetto {@link Token} che rappresenta l'operatore, o null se non presente.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private Token parseOp() throws SyntacticException {
        Token op = anyMatch(TokenType.OP_ASS, TokenType.ASS);

        System.out.println("parseOp: " + op.getType() + " " + op.getLine() + " " + op.getVal());

        if (op.getType() == TokenType.OP_ASS) {
            switch (op.getVal()) {
                case "+=" -> { return new Token(TokenType.PLUS, op.getLine()); }
                case "-=" -> { return new Token(TokenType.MINUS, op.getLine()); }
                case "*=" -> { return new Token(TokenType.TIMES, op.getLine()); }
                case "/=" -> { return new Token(TokenType.DIVIDE, op.getLine()); }
            }
        }
        return null;
    }

    /**
     * Effettua il parsing di un'espressione.
     *
     * @return Un oggetto {@link NodeExpr} che rappresenta l'espressione.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeExpr parseExp() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseExp: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case INT, FLOAT, ID -> {
                NodeExpr tr = parseTr();
                return parseExpP(tr);
            }
            default -> throw new SyntacticException(tk.getLine(), "INT, FLOAT or ID", tk.getType());
        }
    }

    /**
     * Effettua il parsing della parte opzionale di un'espressione.
     *
     * @param left L'espressione già parsificata.
     * @return Un oggetto {@link NodeExpr} che rappresenta l'espressione completa.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseExpP: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case MINUS, PLUS -> {
                NodeBinOp binOp = new NodeBinOp(
                        left,
                        anyMatch(TokenType.MINUS, TokenType.PLUS).getType() == TokenType.MINUS ? LangOper.MINUS : LangOper.PLUS,
                        parseTr()
                );
                return parseExpP(binOp);
            }
            case SEMI -> { return left; }
            default -> throw new SyntacticException(tk.getLine(), "MINUS, PLUS or EOF", tk.getType());
        }
    }

    /**
     * Effettua il parsing di un termine.
     *
     * @return Un oggetto {@link NodeExpr} che rappresenta il termine.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeExpr parseTr() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseTr: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case INT, FLOAT, ID -> {
                NodeExpr val = parseVal();
                return parseTrP(val);
            }
            default -> throw new SyntacticException(tk.getLine(), "INT, FLOAT or ID", tk.getType());
        }
    }

    /**
     * Effettua il parsing della parte opzionale di un termine.
     *
     * @param left Il termine già parsificato.
     * @return Un oggetto {@link NodeExpr} che rappresenta il termine completo.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeExpr parseTrP(NodeExpr left) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseTrP: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case TIMES, DIVIDE -> {
                NodeBinOp binOp = new NodeBinOp(
                        left,
                        anyMatch(TokenType.TIMES, TokenType.DIVIDE).getType() == TokenType.TIMES ? LangOper.TIMES : LangOper.DIV,
                        parseVal()
                );
                return parseTrP(binOp);
            }
            case MINUS, PLUS, SEMI -> { return left; }
            default -> throw new SyntacticException(tk.getLine(), "TIMES, DIVIDE, MINUS, PLUS or SEMI", tk.getType());
        }
    }

    /**
     * Effettua il parsing di un valore.
     *
     * @return Un oggetto {@link NodeExpr} che rappresenta il valore.
     * @throws SyntacticException Se si verifica un errore sintattico.
     */
    private NodeExpr parseVal() throws SyntacticException {
        Token tk = peekToken();

        System.out.println("parseVal: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        switch (tk.getType()) {
            case INT, FLOAT -> {
                match(tk.getType());
                return new NodeConst(tk.getVal(), tk.getType() == TokenType.INT ? LangType.INT : LangType.FLOAT);
            }
            case ID -> { return new NodeDeref(new NodeId(match(TokenType.ID).getVal())); }
            default -> throw new SyntacticException(tk.getLine(), "INT, FLOAT or ID", tk.getType());
        }
    }

    /**
     * Confronta il token corrente con il tipo atteso e lo consuma.
     *
     * @param type Il tipo di token atteso.
     * @return Il token consumato.
     * @throws SyntacticException Se il token corrente non corrisponde al tipo atteso.
     */
    private Token match(TokenType type) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("match: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        if (type.equals(tk.getType())) return nextToken();
        else throw new SyntacticException(tk.getLine(), type.toString(), tk.getType());
    }

    /**
     * Confronta il token corrente con uno dei tipi attesi e lo consuma (ne basta uno).
     *
     * @param firstType Il primo tipo di token atteso.
     * @param types     Gli altri tipi di token attesi.
     * @return Il token consumato.
     * @throws SyntacticException Se il token corrente non corrisponde a nessuno dei tipi attesi.
     */
    private Token anyMatch(TokenType firstType, TokenType... types) throws SyntacticException {
        Token tk = peekToken();

        System.out.println("anyMatch: " + tk.getType() + " " + tk.getLine() + " " + tk.getVal());

        if (firstType.equals(tk.getType())) return nextToken();
        for (TokenType type : types) {
            if (type.equals(tk.getType())) return nextToken();
        }
        throw new SyntacticException(tk.getLine(), firstType + " or " + TokenType.toStringEach(types), tk.getType());
    }

    /**
     * Restituisce il prossimo token senza consumarlo.
     * <p>Se la variabile {@code opAssignVal} ha un valore, lo assegna al token. Esso corrisponde all'ID dell'assegnamento.</p>
     * <p>Se la variabile {@code opAssignOper} ha un valore, lo assegna al token. Esso corrisponde all'operatore dell'assegnamento.</p>
     * Mi permette di tenerli in memoria per la conversione da operatore di assegnamento composto a operatore di assegnamento semplice.
     * Ad esempio, se ho un'assegnazione del tipo {@code a += 1}, il token {@code opAssignVal} conterrà {@code a} e {@code opAssignOper} conterrà {@code +}
     * e l'espressione conterrà {@code 1}, in questo modo forzo la lettura a {@code a = a + 1}.
     *
     * @return Il prossimo token.
     * @throws SyntacticException Se si verifica un errore lessicale.
     */
    private Token peekToken() throws SyntacticException {
        Token token;
        if (opAssignVal != null) token = opAssignVal;
        else if (opAssignOper != null) token = opAssignOper;
        else {
            try {
                token = sc.peekToken();
            } catch (LexicalException e) {
                throw new SyntacticException(e.getMessage(), e);
            }
        }
        return token;
    }

    /**
     * Restituisce il prossimo token e lo consuma.
     * <p>Come nel {@link Parser#peekToken()} restituisco {@code opAssignVal} e {@code opAssignOper} nel caso in cui non fossero nulli.
     * Successivamente li setto a {@code null} per poterli riutilizzare per altri token.</p>
     *
     * @return Il prossimo token consumato.
     * @throws SyntacticException Se si verifica un errore lessicale.
     */
    private Token nextToken() throws SyntacticException {
        Token token;
        if (opAssignVal != null) {
            token = opAssignVal;
            opAssignVal = null;
        } else if (opAssignOper != null) {
            token = opAssignOper;
            opAssignOper = null;
        } else {
            try {
                return sc.nextToken();
            } catch (LexicalException e) {
                throw new SyntacticException(e.getMessage(), e);
            }
        }
        return token;
    }
}