package visitor;

import ast.*;

/**
 * La classe {@code CodeGenerationVisitor} realizza l'interfaccia {@link IVisitor} ed è responsabile della generazione del codice.
 * <p>Questa classe attraversa l'AST e produce il codice <em>dc</em> corrispondente al linguaggio <em>ac</em> analizzato.</p>
 * <p>Prima di utilizzare questa classe, è necessario che l'AST sia stato verificato semanticamente tramite {@link TypeCheckingVisitor}.</p>
 *
 * @see IVisitor
 * @see TypeCheckingVisitor
 * @author Kristian Rigo (matr. 20046665)
 */
public class CodeGenerationVisitor implements IVisitor {
    private String codiceDc;
    private String log;

    /**
     * Crea un nuovo {@link CodeGenerationVisitor}, inizializzando il codice <em>dc</em> e il log come stringhe vuote.
     */
    public CodeGenerationVisitor() {
        codiceDc = log = "";
    }

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

    @Override
    public void visit(NodeDecl nodeDecl) {
        if(nodeDecl.getId().getRegister() == (char) -1) log = "Register not available for identifier <" + nodeDecl.getId().getIdName() + ">";
        else if(nodeDecl.getInit() != null) {
            nodeDecl.getInit().accept(this);
            codiceDc += "s" + nodeDecl.getId().getRegister() + " 0 k ";
        }
        System.out.println("NodeDecl: " + nodeDecl.getId().getIdName() + ", Register: " + nodeDecl.getId().getRegister() + ", Type: " + nodeDecl.getType() + ", CodiceDc: " + codiceDc);
    }

    @Override
    public void visit(NodeId nodeId) {
        codiceDc = "l" + nodeId.getRegister() + " ";

        System.out.println("NodeId: idName: " + nodeId.getIdName() + ", Register: " + nodeId.getRegister() + ", CodiceDc: " + codiceDc);
    }

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

    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getExpr().accept(this);

        codiceDc += "s" + nodeAssign.getId().getRegister() + " 0 k ";

        System.out.println("NodeAssign: " + nodeAssign.getId().getIdName() + ", Register: " + nodeAssign.getId().getRegister() + ", CodiceDc -> " + codiceDc);
    }

    @Override
    public void visit(NodeConst nodeConst) { codiceDc = nodeConst.getValue() + " "; }

    @Override
    public void visit(NodeDeref nodeDeref) { nodeDeref.getId().accept(this); }

    @Override
    public void visit(NodePrint nodePrint) { codiceDc = "l" + nodePrint.getId().getRegister() + " p P "; }

    /**
     * Restituisce il codice <em>dc</em> generato durante la visita dell'AST.
     *
     * @return Il codice <em>dc</em> generato.
     */
    public String getCode() {
        return codiceDc;
    }

    /**
     * Restituisce il log degli errori rilevati durante la visita dell'AST.
     * Se il log è vuoto, non sono stati rilevati errori.
     *
     * @return Il log degli errori rilevati.
     */
    public String getLog() {
        return log;
    }
}
