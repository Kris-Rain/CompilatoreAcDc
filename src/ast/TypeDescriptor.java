package ast;

public class TypeDescriptor {
    private final TypeTD type;
    private final String msg;

    public TypeDescriptor(TypeTD type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public TypeDescriptor(TypeTD type) {
        this(type, "");
    }

    public TypeTD getType() {
        return this.type;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isCompatible(TypeDescriptor tD) {
        return this.type == tD.type || (this.type == TypeTD.INT && tD.type == TypeTD.FLOAT);
    }

    public boolean isError() {
        return this.type == TypeTD.ERROR;
    }

    public boolean isFloat() {
        return this.type == TypeTD.FLOAT;
    }

    public boolean isInt() {
        return this.type == TypeTD.INT;
    }

    public boolean isOk() {
        return this.type == TypeTD.OK;
    }
}
