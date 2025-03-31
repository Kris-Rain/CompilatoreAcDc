package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import scanner.SyntacticException;
import scanner.Scanner;
import java.io.FileNotFoundException;
import java.util.HashMap;

class TestParser {
    private final HashMap<String, Parser> parsers = new HashMap<>();
    private final String filePath = "src/test/data/testParser/";
    private final String[] testFiles = new String[] {
            //"programma_vuoto.txt",
            //"testSoloDichPrint1.txt",
            //"testSoloDichPrintEcc1.txt",
            //"testSoloDichPrintEcc2.txt",
            "testParserCorretto1.txt",
            "testParserCorretto2.txt",
            //"testParserCorretto3.txt",
            "testParserEcc_0.txt",
            "testParserEcc_1.txt",
            "testParserEcc_2.txt",
            "testParserEcc_3.txt",
            "testParserEcc_4.txt",
            "testParserEcc_5.txt",
            "testParserEcc_6.txt",
            "testParserEcc_7.txt",
            //"testParserEcc_8.txt",
            //"testParserEcc_9.txt"
    };

    @BeforeEach
    void setUpParsers() throws FileNotFoundException {
        for (String testFile : testFiles) {
            parsers.put(testFile, new Parser(new Scanner(filePath + testFile)));
        }
    }
/*
    @Test
    void testParserCorretti() {
        String[] files = new String[] { "testParserCorretto1.txt", "testParserCorretto2.txt", "testParserCorretto3.txt" };
        for(String file : files)
            assertDoesNotThrow(() -> parsers.get(file).parse());
    }
 */

    @Test
    void parse_throwsSyntacticException_whenUnexpectedToken() {
        Parser parser = parsers.get("testParserEcc_0.txt");
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void match_throwsSyntacticException_whenUnexpectedToken() {
        Parser parser = parsers.get("testParserEcc_1.txt");
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void parseDSs_throwsSyntacticException_whenInvalidDeclarationsAndStatements() {
        Parser parser = parsers.get("testParserEcc_2.txt");
        assertThrows(SyntacticException.class, parser::parse);
    }

    @Test
    void parseDcl_throwsSyntacticException_whenInvalidDeclaration() {
        Parser parser = parsers.get("testParserEcc_3.txt");
        assertThrows(SyntacticException.class, parser::parse);
    }
/*
    @Test
    void parseStm_parsesCorrectly_whenValidStatement() {
        Parser parser = parsers.get("testSoloDichPrint1.txt");
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void parseStm_throwsSyntacticException_whenInvalidStatement() {
        Parser parser = parsers.get("testSoloDichPrintEcc1.txt");
        assertThrows(SyntacticException.class, parser::parse);
    }
 */
}