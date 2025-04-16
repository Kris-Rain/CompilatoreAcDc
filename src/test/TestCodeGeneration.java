package test;

import ast.NodeProgram;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import symbolTable.Register;
import symbolTable.SymbolTable;
import visitor.CodeGenerationVisitor;
import visitor.TypeCheckingVisitor;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCodeGeneration {

    private final HashMap<String, Parser> parsers = new HashMap<>();
    private final String[] testFiles = new String[] {
            "1_assign.txt",
            "2_divisioni.txt",
            "3_generale.txt",
            "4_registriFiniti.txt",
            "5_corretto.txt",
            "6_corretto.txt",
            "7_corretto.txt",
            "8_corretto.txt",
            "9_corretto.txt",
            "10_corretto.txt"
    };

    @BeforeEach
    void setup() throws FileNotFoundException {
        Register.init();
        SymbolTable.init();
        String filePath = "src/test/data/testCodeGeneration/";
        for (String testFile : testFiles) parsers.put(testFile, new Parser(new Scanner(filePath + testFile)));
    }

    @Test
    void testAssign() throws SyntacticException {
        assertEquals("1 6 / sa 0 k la p P", generateCode("1_assign.txt"));
    }

    @Test
    void testDivisioni() throws SyntacticException {
        assertEquals("0 sa 0 k la 1 + sa 0 k 6 sb 0 k 1.0 6 5 k / la lb / + sc 0 k la p P lb p P lc p P", generateCode("2_divisioni.txt"));
    }

    @Test
    void testGenerale() throws SyntacticException {
        assertEquals("5 3 + sa 0 k la 0.5 + sb 0 k la p P lb 4 5 k / sb 0 k lb p P lb 1 - sc 0 k lc lb * sc 0 k lc p P", generateCode("3_generale.txt"));
    }

    @Test
    void testRegistriFiniti() throws SyntacticException {
        CodeGenerationVisitor cgVisitor = generateCodeVisitor("4_registriFiniti.txt");
        assertFalse(cgVisitor.getLog().isEmpty());
        assertEquals("Register not available for identifier <abc>", cgVisitor.getLog());
        assertEquals("6 2 / sa 0 k la p P", cgVisitor.getCode());
    }

    @Test
    void testCorrettoZero() throws SyntacticException {
        assertEquals("3 sa 0 k 5.3 2 * sa 0 k la p P", generateCode("5_corretto.txt"));
    }

    @Test
    void testCorrettoOne() throws SyntacticException {
        assertEquals("5 sa 0 k 4 sb 0 k la 3.2 lb / - sb 0 k lb p P", generateCode("6_corretto.txt"));
    }

    @Test
    void testCorrettoTwo() throws SyntacticException {
        assertEquals("8 sa 0 k 10 sb 0 k la lb 7 / + sc 0 k la lb - sa 0 k lc p P la p P", generateCode("7_corretto.txt"));
    }

    @Test
    void testCorrettoThree() throws SyntacticException {
        assertEquals("12.5 sa 0 k 2 sb 0 k 12.5 lb 5 k / sc 0 k lc p P la lb 2 * + sa 0 k la p P", generateCode("8_corretto.txt"));
    }

    @Test
    void testCorrettoFour() throws SyntacticException {
        assertEquals("3 sa 0 k 0 sb 0 k la 1 + sa 0 k lb 1 * 7 3 / + sb 0 k lb la 5 k / sa 0 k lb p P la p P", generateCode("9_corretto.txt"));
    }

    @Test
    void testCorrettoFive() throws SyntacticException {
        assertEquals("3 4 * 2.5 + sa 0 k la p P lb sa 0 k la p P 5 sc 0 k la lc lc * + sa 0 k la 2 5 k / sa 0 k la p P lc p P", generateCode("10_corretto.txt"));
    }

    private CodeGenerationVisitor generateCodeVisitor(String fileName) throws SyntacticException {
        Parser parser = parsers.get(fileName);
        NodeProgram nodePrg = parser.parse();
        TypeCheckingVisitor tcVisitor = new TypeCheckingVisitor();
        CodeGenerationVisitor cgVisitor = new CodeGenerationVisitor();
        nodePrg.accept(tcVisitor);
        assertTrue(tcVisitor.getResult().isOk());
        nodePrg.accept(cgVisitor);

        return cgVisitor;
    }

    private String generateCode(String fileName) throws SyntacticException {
        return generateCodeVisitor(fileName).getCode();
    }
}
