package ast;

/*
    Enumeration for the types of operators.
    PLUS: +
    MINUS: -
    TIMES: *
    DIV: /
*/

public enum LangOper {
    PLUS,
    MINUS,
    TIMES,
    DIV,
    DIV_FLOAT;

    /**
     * Ritorna il simbolo corrispontente a questo operatore.
     *
     * @return il simbolo dell'operatore in formato {@code String}
     */
    public String getSymbol() {
        return switch (this) {
            case PLUS -> "+";
            case MINUS -> "-";
            case TIMES -> "*";
            case DIV, DIV_FLOAT -> "/";
        };
    }
}

