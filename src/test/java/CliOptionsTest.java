import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CliOptionsTest {



    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

//    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
//    private final PrintStream originalErr = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to a ByteArrayOutputStream
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restore() {
        // Restore the original System.out after each test
        System.setOut(originalOut);
    }

//    @BeforeEach
//    void before() {
//        System.setErr(new PrintStream(errorStream));
//    }
//
//    @AfterEach
//    void after() {
//        System.setErr(originalErr);
//    }

    @Test
    void testCLIParse_Valid() {
        String[] args =  {"C:\\Users\\rahul\\Downloads\\walmart.csv", "dummyFile.json", "--pretty"};
        CliOptions cliObj =CliOptions.parse(args);

        String expectedInput = "C:\\Users\\rahul\\Downloads\\walmart.csv";
        String expectedOutput = "dummyFile.json";
        boolean pretty = true;

        assertEquals(expectedInput, cliObj.input());
        assertEquals(expectedOutput, cliObj.output());
        assertEquals(pretty, cliObj.prettyPrint());

    }

    @Test
    void testCLIParse_Invalid() {
        String[] args =  {"C:\\Users\\rahul\\Downloads\\thisFileDoesNotExist.csv", "fileNamec.json"};
        CliOptions cliObj =CliOptions.parse(args);

        String expectedInput = "C:\\Users\\rahul\\Downloads\\walmart.csv";
        String expectedOutput = "dummyFile.json";
        boolean expectedPretty = false;

        assertNotEquals(expectedInput, cliObj.input());
        assertNotEquals(expectedOutput, cliObj.output());
        assertEquals(expectedPretty, cliObj.prettyPrint());
    }

//    @Test
//    void testSyntaxHelp() {
//        String[] help1 = {"--hel"};
//        String expectedOPStream = "Usage syntax: java -jar csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar inputFileName.csv outputFileName.json [-d \",\" | \"\\t\"] [--pretty] [--help]";
//
//        CliOptions cliObj1 = CliOptions.parse(help1);
//
//        assertEquals(expectedOPStream+"\r\n", outputStream.toString());
//    }


    @Test
    void testCLIParse_InsufficientArgs() {
        String[] arg = {"one argument"};
        //CliOptions cliObj = CliOptions.parse(arg);
        Throwable exception = assertThrows(RuntimeException.class, () -> CliOptions.parse(arg));
       // assertEquals("Not a valid syntax, use [--help] or [-h] for help\r\n",errorStream.toString());
        assertEquals("Insufficient arguments, use [--help] or [-h] for help", exception.getMessage());
    }





}
