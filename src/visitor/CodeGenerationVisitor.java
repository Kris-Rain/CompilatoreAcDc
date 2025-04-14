package visitor;

import ast.*;
import symbolTable.SymbolTable;
import token.TokenType;

import javax.management.Attribute;

public class CodeGenerationVisitor implements IVisitor {
    private String codiceDc;
    private String log;

    /**
     * Crea un nuovo {@link CodeGenerationVisitor}, inizializzando il codice <em>dc</em> e il log a stringhe vuote.
     */
    public CodeGenerationVisitor() {
        codiceDc = log = "";
    }

    /**
     * @param nodePrg
     */
    @Override
    public void visit(NodeProgram nodePrg) {
        StringBuilder codeBuild = new StringBuilder();
        for(NodeDecSt decSt : nodePrg.getDecSts()) {
            decSt.accept(this);
            if(!log.isEmpty()) break;
            codeBuild.append(codiceDc);
            codiceDc = "";
        }
        codiceDc = codeBuild.toString().strip();
    }

    /**
     * @param nodeDecl
     */
    @Override
    public void visit(NodeDecl nodeDecl) {
        if(nodeDecl.getId().getRegister() == (char) -1) log = "Register not available for identifier <" + nodeDecl.getId().getIdName() + ">";
        else if(nodeDecl.getInit() != null) {
            nodeDecl.getInit().accept(this);
            codiceDc += "s" + nodeDecl.getId().getRegister() + " 0 k ";
        }
        System.out.println("NodeDecl: " + nodeDecl.getId().getIdName() + ", Register: " + nodeDecl.getId().getRegister() + ", Type: " + nodeDecl.getType() + ", CodiceDc: " + codiceDc);
    }

    /**
     * @param nodeId
     */
    @Override
    public void visit(NodeId nodeId) {
        SymbolTable.Attribute entry = SymbolTable.lookup(nodeId.getIdName());
        codiceDc = "l" + nodeId.getRegister() + " ";

        System.out.println("NodeId: idName: " + nodeId.getIdName() + ", Register: " + nodeId.getRegister() + ", Type: " + entry.getType());
    }

    /**
     * @param nodeBinOp
     */
    @Override
    public void visit(NodeBinOp nodeBinOp) {
        nodeBinOp.getLeft().accept(this);
        String leftCode = codiceDc;
        nodeBinOp.getRight().accept(this);
        String rightCode = codiceDc;

        boolean isOpFloat = nodeBinOp.getOp() == LangOper.DIV_FLOAT;

        codiceDc = (isOpFloat) ? leftCode + rightCode + "5 k " + nodeBinOp.getOp().getSymbol() + " " : leftCode + rightCode + nodeBinOp.getOp().getSymbol() + " ";
        System.out.println("NodeBinOp: LeftCode -> " + leftCode + ", RightCode -> " + rightCode + ", Op -> " + nodeBinOp.getOp() + ", CodiceDc -> " + codiceDc);
    }

    /**
     * @param nodeAssign
     */
    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getExpr().accept(this);

        codiceDc += "s" + nodeAssign.getId().getRegister() + " 0 k ";

        System.out.println("NodeAssign: " + nodeAssign.getId().getIdName() + ", Register: " + nodeAssign.getId().getRegister() + ", CodiceDc -> " + codiceDc);
    }

    /**
     * @param nodeConst
     */
    @Override
    public void visit(NodeConst nodeConst) { codiceDc = nodeConst.getValue() + " "; }

    /**
     * @param nodeDeref
     */
    @Override
    public void visit(NodeDeref nodeDeref) { nodeDeref.getId().accept(this); }

    /**
     * @param nodePrint
     */
    @Override
    public void visit(NodePrint nodePrint) { codiceDc = "l" + nodePrint.getId().getRegister() + " p P "; }

    /**
     * Ritorna il codice <em>dc</em> generato con la visita dell'AST.
     *
     * @return il codice <em>dc</em> generato
     */
    public String getCode() {
        return codiceDc;
    }

    /**
     * Ritorna il log degli errori generati durante la visita dell'AST. Un log vuoto
     * indica che non sono stati generati errori.
     *
     * @return il log degli errori generati
     */
    public String getLog() {
        return log;
    }
}
