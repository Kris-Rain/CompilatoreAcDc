package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import java.io.FileNotFoundException;
import java.util.HashMap;

class TestParser {
    private final HashMap<String, Parser> parsers = new HashMap<>();
    private final String[] testFiles = new String[] {
            "testNico/programma_vuoto.txt",
            "testSoloDich.txt",
            "testSoloDichPrint.txt",
            "testNico/testSoloDichPrint1.txt",
            "testNico/testSoloDichPrintEcc1.txt",
            "testNico/testSoloDichPrintEcc2.txt",
            "testParserCorretto1.txt",
            "testParserCorretto2.txt",
            "testNico/testParserCorretto3.txt",
            "testParserEcc_0.txt",
            "testParserEcc_1.txt",
            "testParserEcc_2.txt",
            "testParserEcc_3.txt",
            "testParserEcc_4.txt",
            "testParserEcc_5.txt",
            "testParserEcc_6.txt",
            "testParserEcc_7.txt",
            "testNico/testParserEcc_8.txt",
            "testNico/testParserEcc_9.txt"
    };

    @BeforeEach
    void setUpParsers() throws FileNotFoundException {
        for (String testFile : testFiles) {
            String filePath = "src/test/data/testParser/";
            parsers.put(testFile, new Parser(new Scanner(filePath + testFile)));
        }
    }

    @Test
    void testProgrammaVuoto() {
        Parser parser = parsers.get("testNico/programma_vuoto.txt");
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testParserCorretti() {
        String[] files = new String[] { "testParserCorretto1.txt", "testParserCorretto2.txt", "testNico/testParserCorretto3.txt" };
        for(String file : files)
            assertDoesNotThrow(() -> parsers.get(file).parse());
    }

    @Test
    void testSoloDich() {
        Parser parser = parsers.get("testSoloDich.txt");
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testSoloDichPrint() {
        Parser parser = parsers.get("testSoloDichPrint.txt");
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testSoloDichPrint1() {
        Parser parser = parsers.get("testNico/testSoloDichPrint1.txt");
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testSoloDichPrintEcc1() {
        Parser parser = parsers.get("testNico/testSoloDichPrintEcc1.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected OP_ASS or ASS but found SEMI at line 3", e.getMessage());
    }

    @Test
    void testSoloDichPrintEcc2() {
        Parser parser = parsers.get("testNico/testSoloDichPrintEcc2.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected SEMI or ASS but found PRINT at line 3", e.getMessage());
    }

    @Test
    void testParseEcc0() {
        Parser parser = parsers.get("testParserEcc_0.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected OP_ASS or ASS but found SEMI at line 1", e.getMessage());
    }

    @Test
    void testParseEcc1() {
        Parser parser = parsers.get("testParserEcc_1.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected INT, FLOAT or ID but found TIMES at line 2", e.getMessage());
    }

    @Test
    void testParseEcc2() {
        Parser parser = parsers.get("testParserEcc_2.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected TYFLOAT, TYINT, ID, PRINT or EOF but found INT at line 3", e.getMessage());
    }

    @Test
    void testParseEcc3() {
        Parser parser = parsers.get("testParserEcc_3.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected OP_ASS or ASS but found PLUS at line 2", e.getMessage());
    }

    @Test
    void testParseEcc4() {
        Parser parser = parsers.get("testParserEcc_4.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected ID but found INT at line 2", e.getMessage());
    }

    @Test
    void testParseEcc5() {
        Parser parser = parsers.get("testParserEcc_5.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected ID but found INT at line 3", e.getMessage());
    }

    @Test
    void testParseEcc6() {
        Parser parser = parsers.get("testParserEcc_6.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected ID but found TYFLOAT at line 3", e.getMessage());
    }

    @Test
    void testParseEcc7() {
        Parser parser = parsers.get("testParserEcc_7.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected ID but found ASS at line 2", e.getMessage());
    }

    @Test
    void testParseEcc8() {
        Parser parser = parsers.get("testNico/testParserEcc_8.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected SEMI or ASS but found OP_ASS at line 3", e.getMessage());
    }

    @Test
    void testParseEcc9() {
        Parser parser = parsers.get("testNico/testParserEcc_9.txt");

        SyntacticException e = assertThrows(SyntacticException.class, parser::parse);
        assertEquals("Expected SEMI or ASS but found ID at line 4", e.getMessage());
    }
}