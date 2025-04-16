package test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import ast.NodeProgram;
import ast.TypeDescriptor;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.TypeCheckingVisitor;

class TestTypeChecking {

    private final HashMap<String, Parser> parsers = new HashMap<>();
    private final String[] testFiles = new String[] {
            "0_multipliErrori.txt",
            "1_dicRipetute.txt",
            "2_idNonDec.txt",
            "3_idNonDec",
            "4_tipoNonCompatibile.txt",
            "5_corretto.txt",
            "6_corretto.txt",
            "7_corretto.txt"
    };

    @BeforeEach
    void setUpParsers() throws FileNotFoundException {
        SymbolTable.init();
        String filePath = "src/test/data/testTypeChecking/";
        for(String testFile : testFiles) parsers.put(testFile, new Parser(new Scanner(filePath + testFile)));
    }

    @Test
    void testMultipliErrori() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("0_multipliErrori.txt");
        assertTrue(res.isError());
        assertEquals("""
                Semantic error assignment of <x>: impossible conversion from FLOAT to INT
                Semantic error <FLOAT x>: identifier <x> already declared
                Semantic error <INT y>: identifier <y> already declared
                Semantic error: identifier <a> not declared
                Semantic error: identifier <z> not declared
                Semantic error: identifier <w> not declared
                """, res.getMsg());
    }

    @Test
    void testDicRipetute() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("1_dicRipetute.txt");
        assertTrue(res.isError());
        assertEquals("Semantic error <FLOAT a>: identifier <a> already declared\n", res.getMsg());
    }

    @Test
    void testIdNonDecOne() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("2_idNonDec.txt");
        assertTrue(res.isError());
        assertEquals("Semantic error: identifier <b> not declared\n", res.getMsg());

    }

    @Test
    void testIdNonDecTwo() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("3_idNonDec");
        assertTrue(res.isError());
        assertEquals("Semantic error: identifier <c> not declared\n", res.getMsg());
    }

    @Test
    void testTipoNonCompatibile() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("4_tipoNonCompatibile.txt");
        assertTrue(res.isError());
        assertEquals("Semantic error assignment of <b>: impossible conversion from FLOAT to INT\n", res.getMsg());
    }

    @Test
    void testCorretto() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("5_corretto.txt");
        assertFalse(res.isError());
        assertTrue(res.isOk());
        assertTrue(res.getMsg().isEmpty());
    }

    @Test
    void testCorrettoTwo() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("6_corretto.txt");
        assertFalse(res.isError());
        assertTrue(res.isOk());
        assertTrue(res.getMsg().isEmpty());
    }

    @Test
    void testCorrettoThree() throws SyntacticException {
        TypeDescriptor res = semanticAnalysis("7_corretto.txt");
        assertFalse(res.isError());
        assertTrue(res.isOk());
        assertTrue(res.getMsg().isEmpty());
    }

    private TypeDescriptor semanticAnalysis(String fileName) throws SyntacticException {
        Parser parser = parsers.get(fileName);
        NodeProgram nodePrg = parser.parse();
        TypeCheckingVisitor tcVisitor = new TypeCheckingVisitor();
        nodePrg.accept(tcVisitor);
        return tcVisitor.getResult();
    }
}
