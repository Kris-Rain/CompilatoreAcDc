package visitor;

import ast.*;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attribute;

public class TypeCheckingVisitor implements IVisitor {

    private TypeDescriptor resType;

    /**
     * Crea un nuovo {@link TypeCheckingVisitor}, inizializzando il risultato a {@link TypeTD#OK} (un programma vuoto è corretto).
     */
    public TypeCheckingVisitor() {
        resType = new TypeDescriptor(TypeTD.OK);
    }

    /**
     * @param nodePrg: il nodo da visitare
     */
    @Override
    public void visit(NodeProgram nodePrg) {
        StringBuilder errorMessages = new StringBuilder();
        for (NodeDecSt decSt : nodePrg.getDecSts()) {
            decSt.accept(this);
            if(resType.isError()) errorMessages.append(resType.getMsg());
        }
        if(!errorMessages.isEmpty()) resType = new TypeDescriptor(TypeTD.ERROR, errorMessages.toString());
    }

    /**
     * @param nodeBinOp: il nodo da visitare
     */
    @Override
    public void visit(NodeBinOp nodeBinOp) {
        nodeBinOp.getLeft().accept(this);
        TypeDescriptor leftTD = resType;
        System.out.println("NodeBinOp: leftTD = " + leftTD.getType());
        nodeBinOp.getRight().accept(this);
        TypeDescriptor rightTD = resType;
        System.out.println("NodeBinOp: rightTD = " + rightTD.getType());

        if(!leftTD.isError() && !rightTD.isError()) {
            if(leftTD.isFloat() ^ rightTD.isFloat()) {
                if(nodeBinOp.getOp().equals(LangOper.DIV)) nodeBinOp.setOp(LangOper.DIV_FLOAT);
                resType = new TypeDescriptor(TypeTD.FLOAT);
            } else if (leftTD.isFloat() && rightTD.isFloat()) resType = new TypeDescriptor(TypeTD.FLOAT);
            else resType = new TypeDescriptor(TypeTD.INT);
        } else resType = new TypeDescriptor(TypeTD.ERROR, leftTD.getMsg() + rightTD.getMsg());
    }

    /**
     * @param nodeAssign: il nodo da visitare
     */
    @Override
    public void visit(NodeAssign nodeAssign) {
        nodeAssign.getId().accept(this);
        TypeDescriptor idTD = resType;
        System.out.println("NodeAssign: id = " + nodeAssign.getId() + ", idTD = " + idTD.getType());
        nodeAssign.getExpr().accept(this);
        TypeDescriptor exprTD = resType;
        System.out.println("NodeAssign: expr = " + nodeAssign.getExpr() + ", exprTD = " + exprTD.getType());

        if(!idTD.isError() && !exprTD.isError()) {
            if(exprTD.isCompatible(idTD)) resType = new TypeDescriptor(TypeTD.OK);
            else resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error assignment of <" + nodeAssign.getId().getIdName() + ">: impossible conversion from FLOAT to INT\n");
        } else resType = new TypeDescriptor(TypeTD.ERROR, idTD.getMsg() + exprTD.getMsg());
    }

    /**
     * @param nodeDecl: il nodo da visitare
     */
    @Override
    public void visit(NodeDecl nodeDecl) {
        NodeId nodeId = nodeDecl.getId();
        String idName = nodeId.getIdName();
        TypeDescriptor typeDecl = new TypeDescriptor(nodeDecl.getType() == LangType.INT ? TypeTD.INT : TypeTD.FLOAT);

        System.out.println("NodeDecl: idName = " + idName + ", typeDecl = " + typeDecl.getType());

        if(SymbolTable.lookup(idName) != null) { //se l'identificatore è già definito
            TypeDescriptor err = resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error <"+ typeDecl.getType() + " " + idName + ">: identifier <"+idName+"> already declared\n");

            if(nodeDecl.getInit() != null) {
                nodeDecl.getInit().accept(this);

                if(resType.isError() && !resType.isCompatible(typeDecl)) {
                    resType = new TypeDescriptor(TypeTD.ERROR,
                            err.getMsg() + "Semantic error declaration of <" + typeDecl.getType() + " " + idName + ">: impossible conversion from FLOAT to INT\n");
                } else resType = new TypeDescriptor(TypeTD.ERROR, err.getMsg() + resType.getMsg());
            }
        } else if (nodeDecl.getInit() != null) { //se l'identificatore non è già definito e c'è un'assegnazione
            nodeDecl.getInit().accept(this);
            if(!resType.isError()) {
                if(resType.isCompatible(typeDecl)) {
                    SymbolTable.enter(idName, new Attribute(nodeDecl.getType()));
                    System.out.println("NodeDecl: idName = " + idName + ", typeDecl = " + typeDecl.getType() + " inserted in the symbol table");
                    resType = new TypeDescriptor(TypeTD.OK);
                } else resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error declaration of <" + typeDecl.getType() + " " + idName + ">: impossible conversion from FLOAT to INT\n");
            }
        } else { //se l'identificatore non è già definito e non c'è un'assegnazione
            SymbolTable.enter(idName, new Attribute(nodeDecl.getType()));
            System.out.println("NodeDecl: idName = " + idName + ", typeDecl = " + typeDecl.getType() + " inserted in the symbol table");
            resType = new TypeDescriptor(TypeTD.OK);
        }
    }

    /**
     * @param nodeId: il nodo da visitare
     */
    @Override
    public void visit(NodeId nodeId) {
        Attribute entry = SymbolTable.lookup(nodeId.getIdName());

        System.out.println((entry != null) ? ("NodeId: idName = " + nodeId.getIdName() + ", attribute type = " + entry.getType()) : ("NodeId: idName = " + nodeId.getIdName() + ", attribute type = null"));

        if(entry == null) resType = new TypeDescriptor(TypeTD.ERROR, "Semantic error: identifier <" + nodeId.getIdName() + "> not declared\n");
        else resType = new TypeDescriptor(entry.getType() == LangType.INT ? TypeTD.INT : TypeTD.FLOAT);
    }

    /**
     * @param nodeConst: il nodo da visitare
     */
    @Override
    public void visit(NodeConst nodeConst) {
        resType = new TypeDescriptor(nodeConst.getType() == LangType.INT ? TypeTD.INT : TypeTD.FLOAT);
        System.out.println("NodeConst: value = " + nodeConst.getValue() + ", type = " + resType.getType());
    }

    /**
     * @param nodeDeref: il nodo da visitare
     */
    @Override
    public void visit(NodeDeref nodeDeref) { nodeDeref.getId().accept(this); }

    /**
     * @param nodePrint: il nodo da visitare
     */
    @Override
    public void visit(NodePrint nodePrint) {
        nodePrint.getId().accept(this);
        if(!resType.isError()) resType = new TypeDescriptor(TypeTD.OK);
        System.out.println("NodePrint: idName = " + nodePrint.getId().getIdName() + ", resType = " + resType.getType());
    }

    public TypeDescriptor getResult() {
        return resType;
    }
}
