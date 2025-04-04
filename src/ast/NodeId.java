package ast;

public class NodeId extends NodeAST {
    private String id;

    public NodeId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "[NodeId: " + id + ']';
    }
}
