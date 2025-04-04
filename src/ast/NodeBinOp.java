package ast;

public class NodeBinOp extends NodeExpr {
    private NodeExpr left;
    private NodeExpr right;
    private LangOper op;

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

    @Override
    public String toString() {
        return "[NodeBinOp: " + this.left + " " + this.op + " " + this.right + "]";
    }
}
