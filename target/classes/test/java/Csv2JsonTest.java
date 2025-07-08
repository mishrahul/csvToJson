import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Csv2JsonTest {

    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @Mock
    private CliOptions cliOptions;

    @Mock
    private DelimiterDetector delimiterDetector;

    @InjectMocks
    private Csv2Json csv2Json;



    @BeforeEach
    void before() {
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }

    @AfterEach
    void after() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    void parsedArgs_Null() {
        String[] args = {"--help"};

        Csv2Json.main(args);

        assertNull(Csv2Json.opts);
        //assertEquals("Usage syntax: java -jar csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar inputFileName.csv outputFileName.json [--pretty] OR [--help]\n", outStream.toString());

    }


    @Test
    void parseArgs_throwsRTExcepption() {
        String[] args = {"onlyOneArgument"};

        Csv2Json.main(args);

        //Throwable ex = assertThrows(RuntimeException.class, () -> Csv2Json.main(args));

        assertEquals("Insufficient arguments, use [--help] or [-h] for help\r\n", errStream.toString());
    }

    @Test
    void validator_isNotValidCSV() throws IOException {
        Path tempFile = Files.createTempFile("nonSCSVfile", ".html");

        String[] args = {tempFile.toString(), "output.json", "--pretty"};

        Csv2Json.main(args);
        assertEquals("The input file is not a valid .csv file\r\n", errStream.toString());
        Files.delete(tempFile);
    }

    @Test
    void validator_fileNotFound() {
        String[] args = {"nonExistentFile.null", "output.json"};

        Csv2Json.main(args);

        assertEquals("No such input file exists\r\n", errStream.toString());
    }

//    @Test
//    void bufferReader_readFile() throws IOException {
//
//        Path tempFile = Files.createTempFile("unreadable", "");
//        String[] args = {tempFile.toString(), "output.json", "--pretty"};
//
//       Csv2Json.main(args);
//
//    }

    @Test
    void testDelimiter_illegalDelimiter() throws IOException {
        Path tempFile = Files.createTempFile("illegalDelimiter", ".csv");
        Files.writeString(tempFile, "Name&Age&City\\nBob&35&Paris");

        String[] args = {tempFile.toString(), "output.json"};

        Csv2Json.main(args);
        assertEquals("The input csv file has an illegal delimiter\r\n", errStream.toString());
        Files.delete(tempFile);
    }

    @Test
    void testCsv2Json_main() throws Exception {
        Path tempInputFile = Files.createTempFile("tempIP", ".csv");
        Path tempOutputFile = Files.createTempFile("output", ".json");
        Files.writeString(tempInputFile, "Name,Age,City\nBob,35,Paris");


        String[] args = {tempInputFile.toString(), tempOutputFile.toString()};
        Csv2Json.main(args);

        assertEquals("[{\"Name\":\"Bob\",\"Age\":\"35\",\"City\":\"Paris\"}]", Files.readString(tempOutputFile));
        Files.delete(tempInputFile);
        Files.delete(tempOutputFile);

    }

    @Test
    void testJsonWriter() throws IOException {
        Path tempInputFile = Files.createTempFile("tempIP", ".csv");
        Path tempOutputFile = Files.createTempFile("output", ".json");
        Files.writeString(tempInputFile, "Name,Age,City\nBob,35,Paris");

        BufferedReader in = Files.newBufferedReader(tempInputFile);
        JsonGenerator out = new JsonFactory().createGenerator(Files.newBufferedWriter(tempOutputFile));
        Csv2Json.jsonWriter(in, out, false, ',');

        assertEquals("[{\"Name\":\"Bob\",\"Age\":\"35\",\"City\":\"Paris\"}]", Files.readString(tempOutputFile));
        Files.delete(tempInputFile);
        Files.delete(tempOutputFile);
    }

    @Test
    void testJsonWriter_prettyPrint() throws IOException {
        Path tempInputFile = Files.createTempFile("tempIP", ".csv");
        Path tempOutputFile = Files.createTempFile("output", ".json");
        Files.writeString(tempInputFile, "Name,Age,City\nBob,35,Paris");

        BufferedReader in = Files.newBufferedReader(tempInputFile);
        JsonGenerator out = new JsonFactory().createGenerator(Files.newBufferedWriter(tempOutputFile));
        Csv2Json.jsonWriter(in, out, true, ',');

        String expectedOutput = "[ {\r\n" +
                                    "  \"Name\" : \"Bob\",\r\n" +
                                    "  \"Age\" : \"35\",\r\n" +
                                    "  \"City\" : \"Paris\"\r\n" +
                                    "} ]";


        assertTrue(Files.readString(tempOutputFile).contains("\n"));
        assertEquals(expectedOutput, Files.readString(tempOutputFile));
        Files.delete(tempInputFile);
        Files.delete(tempOutputFile);

        //System.out.println(outStream.toString());
    }

    @Test
    void testJsonWriter_Delimiter() throws IOException {
        Path tempInputFile = Files.createTempFile("tempIP", ".csv");
        Path tempOutputFile = Files.createTempFile("output", ".json");
        Files.writeString(tempInputFile, "Name|Age|City\nBob|35|Paris");

        BufferedReader in = Files.newBufferedReader(tempInputFile);
        JsonGenerator out = new JsonFactory().createGenerator(Files.newBufferedWriter(tempOutputFile));
        Csv2Json.jsonWriter(in, out, false, '|');

        assertEquals("[{\"Name\":\"Bob\",\"Age\":\"35\",\"City\":\"Paris\"}]", Files.readString(tempOutputFile));
        Files.delete(tempInputFile);
        Files.delete(tempOutputFile);

    }

    @Test
    void testJsonWriter_Delimiter_PrettyPrint() throws IOException {
        Path tempInputFile = Files.createTempFile("tempIP", ".csv");
        Path tempOutputFile = Files.createTempFile("output", ".json");
        Files.writeString(tempInputFile, "Name;Age;City\nBob;35;Paris");

        BufferedReader in = Files.newBufferedReader(tempInputFile);
        JsonGenerator out = new JsonFactory().createGenerator(Files.newBufferedWriter(tempOutputFile));
        Csv2Json.jsonWriter(in, out, true, ';');

        String expectedOutput = "[ {\r\n" +
                                "  \"Name\" : \"Bob\",\r\n" +
                                "  \"Age\" : \"35\",\r\n" +
                                "  \"City\" : \"Paris\"\r\n" +
                                "} ]";


        assertTrue(Files.readString(tempOutputFile).contains("\n"));
        assertEquals(expectedOutput, Files.readString(tempOutputFile));
        Files.delete(tempInputFile);
        Files.delete(tempOutputFile);

    }

}