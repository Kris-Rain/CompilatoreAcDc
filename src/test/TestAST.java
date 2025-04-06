package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import scanner.Scanner;
import java.util.HashMap;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAST {
    private final HashMap<String, Parser> parsers = new HashMap<>();
    private final String[] testFiles = new String[]{
            "testSemplice.txt",
            "testAST_0.txt",
            "testAST_1.txt",
            "testAST_2.txt",
            "testOpAssignEquality_first.txt",
            "testOpAssignEquality_second.txt"
    };

    @BeforeEach
    void setUpParsers() throws FileNotFoundException {
        String filePath = "src/test/data/testAST/";
        for (int i = 0; i < testFiles.length; i++)
            parsers.put(testFiles[i], new Parser(new Scanner(filePath + testFiles[i])));
    }

    @Test
    void testSemplice() throws FileNotFoundException {
        testASTBuild("testSemplice.txt", "[NodeProgram: ["
                + "[NodeDecl: id = [NodeId: a], type = INT, init = null], [NodeDecl: id = [NodeId: b], type = INT, init = null], [NodeDecl: id = [NodeId: c], type = FLOAT, init = null], "
                + "[NodePrint: [NodeId: c]], [NodePrint: [NodeId: a]], [NodePrint: [NodeId: b]]]]");
    }

    @Test
    void testASTCorretti() throws FileNotFoundException {
        testASTBuild("testAST_0.txt", "[NodeProgram: ["
                + "[NodeDecl: id = [NodeId: b], type = FLOAT, init = null], [NodeDecl: id = [NodeId: a], type = INT, init = null], [NodeAssign: [NodeId: a] -> [NodeConst: INT, 5]], "
                + "[NodeAssign: [NodeId: b] -> [NodeBinOp: [NodeDeref: [NodeId: b]] PLUS [NodeConst: FLOAT, 3.2]]], [NodePrint: [NodeId: b]], "
                + "[NodeAssign: [NodeId: a] -> [NodeBinOp: [NodeBinOp: [NodeConst: INT, 3] PLUS [NodeBinOp: [NodeBinOp: [NodeConst: INT, 5] TIMES [NodeConst: INT, 7]] DIV [NodeConst: FLOAT, 8.3]]] MINUS [NodeDeref: [NodeId: b]]]], "
                + "[NodePrint: [NodeId: a]]]]"
        );
    }

    @Test
    void testAST_1() throws FileNotFoundException {
        testASTBuild("testAST_1.txt", "[NodeProgram: ["
                + "[NodeDecl: id = [NodeId: x], type = INT, init = [NodeConst: INT, 5]], "
                + "[NodeDecl: id = [NodeId: y], type = FLOAT, init = [NodeConst: FLOAT, 1.5]], "
                + "[NodeAssign: [NodeId: x] -> [NodeBinOp: [NodeDeref: [NodeId: x]] PLUS [NodeBinOp: [NodeBinOp: [NodeDeref: [NodeId: y]] TIMES [NodeDeref: [NodeId: y]]] PLUS [NodeBinOp: [NodeConst: INT, 4] TIMES [NodeConst: INT, 5]]]]], "
                + "[NodeAssign: [NodeId: y] -> [NodeBinOp: [NodeDeref: [NodeId: y]] DIV [NodeConst: FLOAT, 0.05]]], "
                + "[NodePrint: [NodeId: y]], "
                + "[NodeAssign: [NodeId: x] -> [NodeConst: INT, 0]], "
                + "[NodeAssign: [NodeId: y] -> [NodeBinOp: [NodeBinOp: [NodeConst: FLOAT, 1.5] PLUS [NodeBinOp: [NodeConst: FLOAT, 2.4] DIV [NodeConst: INT, 3]]] PLUS [NodeBinOp: [NodeConst: INT, 4] TIMES [NodeConst: INT, 5]]]], "
                + "[NodeAssign: [NodeId: x] -> [NodeBinOp: [NodeDeref: [NodeId: x]] MINUS [NodeBinOp: [NodeDeref: [NodeId: y]] PLUS [NodeDeref: [NodeId: y]]]]], "
                + "[NodePrint: [NodeId: x]]]]"
        );
    }

    @Test
    void testAST_2() throws FileNotFoundException {
        testASTBuild("testAST_2.txt", "[NodeProgram: "
                + "[[NodeDecl: id = [NodeId: x], type = FLOAT, init = null], "
                + "[NodeAssign: [NodeId: x] -> [NodeBinOp: [NodeConst: FLOAT, 5.99] PLUS [NodeConst: FLOAT, 0.01]]], "
                + "[NodeDecl: id = [NodeId: y], type = INT, init = [NodeConst: INT, 3]], "
                + "[NodeAssign: [NodeId: x] -> [NodeBinOp: [NodeDeref: [NodeId: x]] TIMES [NodeBinOp: [NodeConst: INT, 3] DIV [NodeConst: FLOAT, 4.5]]]], "
                + "[NodeAssign: [NodeId: x] -> [NodeBinOp: [NodeBinOp: [NodeDeref: [NodeId: x]] TIMES [NodeConst: INT, 3]] DIV [NodeConst: FLOAT, 4.5]]], "
                + "[NodeAssign: [NodeId: y] -> [NodeBinOp: [NodeDeref: [NodeId: y]] DIV [NodeBinOp: [NodeBinOp: [NodeDeref: [NodeId: x]] PLUS [NodeBinOp: [NodeConst: INT, 11] TIMES [NodeConst: INT, 3]]] PLUS [NodeConst: INT, 5]]]], "
                + "[NodePrint: [NodeId: y]], "
                + "[NodePrint: [NodeId: x]]]]"
        );
    }

    @Test
    void testOpAssignEquality() throws FileNotFoundException {
        assertEquals(
                assertDoesNotThrow(() -> parsers.get("testOpAssignEquality_first.txt").parse()).toString(),
                assertDoesNotThrow(() -> parsers.get("testOpAssignEquality_second.txt").parse()).toString()
        );
    }

    private void testASTBuild(String fileName, String expected) throws FileNotFoundException {
        Parser parser = parsers.get(fileName);
        assertEquals(expected, assertDoesNotThrow(parser::parse).toString());
    }
}
