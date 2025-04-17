package visitor;

import ast.*;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attribute;

/**
 * La classe {@code TypeCheckingVisitor} implementa {@link IVisitor} per eseguire il controllo semantico
 * dell'AST, (albero sintattico astratto) ricavato dal codice <em>ac</em>.
 * <br>Il risultato del controllo è memorizzato in un oggetto {@link TypeDescriptor}, che può indicare
 * se il programma è semanticamente corretto o contiene errori.
 * <p>Se l'analisi ha successo, si può procedere con la generazione del codice <em>dc</em> tramite {@link CodeGenerationVisitor}.</p>
 *
 * @see IVisitor
 * @see CodeGenerationVisitor
 * @author Kristian Rigo (matr. 20046665)
 */
public class TypeCheckingVisitor implements IVisitor {

    private TypeDescriptor resType;

    /**
     * Crea un nuovo {@link TypeCheckingVisitor}, inizializzando il risultato a {@link TypeTD#OK}.
     * <br>Un programma vuoto è considerato semanticamente corretto.
     */
    public TypeCheckingVisitor() {
        resType = new TypeDescriptor(TypeTD.OK);
    }

    @Override
    public void visit(NodeProgram nodePrg) {
        StringBuilder errorMessages = new StringBuilder();
        for (NodeDecSt decSt : nodePrg.getDecSts()) {
            decSt.accept(this);
            if (resType.isError()) errorMessages.append(resType.getMsg());
        }
        if (!errorMessages.isEmpty()) resType = new TypeDescriptor(TypeTD.ERROR, errorMessages.toString());
    }

    @Override
    public void visit(NodeBinOp nodeBinOp) {
        nodeBinOp.getLeft().accept(this);
        TypeDescriptor leftTD = resType;
        System.out.println("NodeBinOp: leftTD = " + leftTD.getType());
        nodeBinOp.getRight().accept(this);
        TypeDescriptor rightTD = resType;
        System.out.println("NodeBinOp: rightTD = " + rightTD.getType());

        if (!leftTD.isError() && !rightTD.isError()) {
            if (leftTD.isFloat() ^ rightTD.isFloat()) {
                if (nodeBinOp.getOp().equals(LangOper.DIV)) nodeBinOp.setOp(LangOper.DIV_FLOAT);
                resType = new TypeDescriptor(TypeTD.FLOAT);
            } else if (leftTD.isFloat() && rightTD.isFloat()) resType = new TypeDescriptor(TypeTD.FLOAT);
            else resType = new TypeDescriptor(TypeTD.INT);
        } else resType = new TypeDescriptor(TypeTD.ERROR, leftTD.getMsg() + rightTD.getMsg());
    }

    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getId().accept(this);
        TypeDescriptor idTD = resType;
        System.out.println("NodeAssign: id = " + nodeAssign.getId() + ", idTD = " + idTD.getType());
        nodeAssign.getExpr().accept(this);
        TypeDescriptor exprTD = resType;
        System.out.println("NodeAssign: expr = " + nodeAssign.getExpr() + ", exprTD = " + exprTD.getType());

        if (!idTD.isError() && !exprTD.isError()) {
            if (exprTD.isCompatible(idTD)) resType = new TypeDescriptor(TypeTD.OK);
            else
                resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error assignment of <" + nodeAssign.getId().getIdName() + ">: impossible conversion from FLOAT to INT\n");
        } else resType = new TypeDescriptor(TypeTD.ERROR, idTD.getMsg() + exprTD.getMsg());
    }

    @Override
    public void visit(NodeDecl nodeDecl) {
        NodeId nodeId = nodeDecl.getId();
        String idName = nodeId.getIdName();
        TypeDescriptor typeDecl = new TypeDescriptor(nodeDecl.getType() == LangType.INT ? TypeTD.INT : TypeTD.FLOAT);

        System.out.println("NodeDecl: idName = " + idName + ", typeDecl = " + typeDecl.getType());

        if (SymbolTable.lookup(idName) != null) {
            TypeDescriptor err = resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error <" + typeDecl.getType() + " " + idName + ">: identifier <" + idName + "> already declared\n");

            if (nodeDecl.getInit() != null) {
                nodeDecl.getInit().accept(this);

                if (resType.isError() && !resType.isCompatible(typeDecl)) {
                    resType = new TypeDescriptor(TypeTD.ERROR, err.getMsg() + "Semantic error declaration of <" + typeDecl.getType() + " " + idName + ">: impossible conversion from FLOAT to INT\n");
                } else resType = new TypeDescriptor(TypeTD.ERROR, err.getMsg() + resType.getMsg());
            }
        } else if (nodeDecl.getInit() != null) {
            nodeDecl.getInit().accept(this);
            if (!resType.isError()) {
                if (resType.isCompatible(typeDecl)) {
                    SymbolTable.enter(idName, new Attribute(nodeDecl.getType()));
                    System.out.println("NodeDecl: idName = " + idName + ", typeDecl = " + typeDecl.getType() + " inserted in the symbol table");
                    resType = new TypeDescriptor(TypeTD.OK);
                } else
                    resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error declaration of <" + typeDecl.getType() + " " + idName + ">: impossible conversion from FLOAT to INT\n");
            }
        } else {
            SymbolTable.enter(idName, new Attribute(nodeDecl.getType()));
            System.out.println("NodeDecl: idName = " + idName + ", typeDecl = " + typeDecl.getType() + " inserted in the symbol table");
            resType = new TypeDescriptor(TypeTD.OK);
        }
    }

    @Override
    public void visit(NodeId nodeId) {
        Attribute entry = SymbolTable.lookup(nodeId.getIdName());

        System.out.println((entry != null) ? ("NodeId: idName = " + nodeId.getIdName() + ", attribute type = " + entry.getType()) : ("NodeId: idName = " + nodeId.getIdName() + ", attribute type = null"));

        if (entry == null) resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error: identifier <" + nodeId.getIdName() + "> not declared\n");
        else resType = new TypeDescriptor(entry.getType() == LangType.INT ? TypeTD.INT : TypeTD.FLOAT);
    }

    @Override
    public void visit(NodeConst nodeConst) {
        resType = new TypeDescriptor(nodeConst.getType() == LangType.INT ? TypeTD.INT : TypeTD.FLOAT);
        System.out.println("NodeConst: value = " + nodeConst.getValue() + ", type = " + resType.getType());
    }

    @Override
    public void visit(NodeDeref nodeDeref) {
        nodeDeref.getId().accept(this);
    }

    @Override
    public void visit(NodePrint nodePrint) {
        nodePrint.getId().accept(this);
        if (!resType.isError()) resType = new TypeDescriptor(TypeTD.OK);
        System.out.println("NodePrint: idName = " + nodePrint.getId().getIdName() + ", resType = " + resType.getType());
    }

    /**
     * Restituisce il risultato dell'analisi semantica. Se il risultato è di tipo {@link TypeTD#OK}, allora non ci sono stati errori.
     * Altrimenti, il risultato sarà di tipo {@link TypeTD#ERROR} e conterrà il messaggio di errore.
     *
     * @return Un oggetto {@link TypeDescriptor} che rappresenta il risultato del controllo semantico.
     */
    public TypeDescriptor getResult() {
        return resType;
    }
}