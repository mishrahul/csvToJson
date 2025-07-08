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

    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        // redirect System.out to a byteArrayOutputStream
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restore() {
        // restore the original system.out after each test
        System.setOut(originalOut);
    }

    @BeforeEach
    void before() {
        System.setErr(new PrintStream(errorStream));
    }

    @AfterEach
    void after() {
        System.setErr(originalErr);
    }

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
        String[] args =  {"C:\\Users\\rahul\\Downloads\\thisFileDoesNotExist.csv", "fileName.json"};
        CliOptions cliObj =CliOptions.parse(args);

        String expectedInput = "C:\\Users\\rahul\\Downloads\\walmart.csv";
        String expectedOutput = "dummyFile.json";
        boolean expectedPretty = false;

        assertNotEquals(expectedInput, cliObj.input());
        assertNotEquals(expectedOutput, cliObj.output());
        assertEquals(expectedPretty, cliObj.prettyPrint());
    }

    @Test
    void testSyntaxHelp1() {
        String[] helpArgs = {"--help"};
        CliOptions opts = CliOptions.parse(helpArgs);
        String expectedOutput = "Usage syntax: java -jar csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar inputFileName.csv outputFileName.json [--pretty] OR [--help]\r\n";
        assertEquals(expectedOutput, outputStream.toString());
        assertNull(opts);
    }

    @Test
    void testSyntaxHelp2() {
        String[] helpArgs = {"-h"};
        CliOptions opts = CliOptions.parse(helpArgs);
        String expectedOutput = "Usage syntax: java -jar csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar inputFileName.csv outputFileName.json [--pretty] OR [--help]\r\n";
        assertEquals(expectedOutput, outputStream.toString());
        assertNull(opts);
    }



    @Test
    void testCLIParse_InsufficientArgs() {
        String[] arg = {"one argument"};
        //CliOptions cliObj = CliOptions.parse(arg);
        Throwable exception = assertThrows(RuntimeException.class, () -> CliOptions.parse(arg));
        assertEquals("Insufficient arguments, use [--help] or [-h] for help", exception.getMessage());
    }

    @Test
    void testCLIParse_NoPretty() {
        String[] args = {"inputFileName.csv", "outputFileName.json"};
        CliOptions opts = CliOptions.parse(args);

        assertFalse(opts.prettyPrint());
    }

    @Test
    void testCLIParse_UnknownArg() {
        String[] args = {"inputFileName.csv", "outputFileName.json", "--prettyTypo"};

        CliOptions opts = CliOptions.parse(args);

        assertEquals("Unknown argument: "+ args[2]+"\r\n", errorStream.toString());
    }





}
