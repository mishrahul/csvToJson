import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class DelimiterDetectorTest {

    @TempDir
    Path tempDir;

    @Test
    void testGetDelimiter_NoFile() {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        PrintStream printStream = System.err;

        System.setErr(new PrintStream(byteOut));
        char actual = DelimiterDetector.detectDelimiter("nonExistentFile.null");

        assertEquals('X', actual);
        assertEquals("The file does not exist\r\n", byteOut.toString());

        System.setOut(printStream);
    }


    @Test
    void testDelimiterDetector_throwsIOexcepiton() throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        PrintStream outStream = System.err;

        System.setErr(new PrintStream(byteOut));

        Path tempFile = Files.createTempFile("file", "txt");


        //Throwable e = assertThrows(IOException.class, () -> DelimiterDetector.detectDelimiter(tempFile.toString()));

        char c = DelimiterDetector.detectDelimiter(tempFile.toString());
        assertEquals("Error reading the file\r\n", byteOut.toString());
        assertEquals('X', c);
        Files.delete(tempFile);

    }



    @Test
    void testDelimiter_comma() throws IOException {
        Path tempCsv = tempDir.resolve("comma.csv");

        Files.writeString(tempCsv, "Name,Age,City\\nBob,35,Paris");

        char actual = DelimiterDetector.detectDelimiter(tempCsv.toString());
        assertEquals(',', actual);
    }


    @Test
    void testDelimiter_pipe() throws IOException {
        Path tempCsv = tempDir.resolve("pipe.csv");

        Files.writeString(tempCsv, "Name|Age|City\\nBob|35|Paris");

        char actual = DelimiterDetector.detectDelimiter(tempCsv.toString());
        assertEquals('|', actual);
    }


    @Test
    void testDelimiter_semicolon() throws IOException {
        Path tempCsv = tempDir.resolve("semicolon.csv");

        Files.writeString(tempCsv, "Name;Age;City\\nBob;35;Paris");

        char actual = DelimiterDetector.detectDelimiter(tempCsv.toString());
        assertEquals(';', actual);
    }


    @Test
    void testDelimiter_tab() throws IOException {
        Path tempCsv = tempDir.resolve("tab.csv");

        Files.writeString(tempCsv, "Name\tAge\tCity\\nBob\t35\tParis");

        char actual = DelimiterDetector.detectDelimiter(tempCsv.toString());
        assertEquals('\t', actual);
    }


    @Test
    void testDelimiter_unknownDelimiter() throws IOException {
        Path tempCsv = tempDir.resolve("strange.csv");

        Files.writeString(tempCsv, "Name$Age$City\\nBob$35$Paris");

        char actual = DelimiterDetector.detectDelimiter(tempCsv.toString());
        assertEquals('X', actual);
    }
}
