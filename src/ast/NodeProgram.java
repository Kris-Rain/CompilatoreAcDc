package ast;

import visitor.IVisitor;

import java.util.ArrayList;

public class NodeProgram extends NodeAST {
    private final ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> decSts) {
        this.decSts = decSts;
    }

    public ArrayList<NodeDecSt> getDecls() {
        return this.decSts;
    }

    @Override
    public String toString() {
        return "[NodeProgram: " + decSts.toString() + ']';
    }

    /**
     *
     */
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public ArrayList<NodeDecSt> getDecSts() {
        return decSts;
    }
}
