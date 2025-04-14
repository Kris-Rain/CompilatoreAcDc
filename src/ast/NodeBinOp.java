package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr {
    private NodeExpr left;
    private NodeExpr right;
    private LangOper op;
    private LangType type;

    public NodeBinOp(NodeExpr left, LangOper op, NodeExpr right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public NodeExpr getLeft() {
        return this.left;
    }

    public NodeExpr getRight() {
        return this.right;
    }

    public LangOper getOp() {
        return this.op;
    }

    public void setOp(LangOper op) { this.op = op; }

    @Override
    public String toString() { return "[NodeBinOp: " + left.toString() + " " + op.toString() + " " + right.toString() + "]"; }

    /**
     *
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
