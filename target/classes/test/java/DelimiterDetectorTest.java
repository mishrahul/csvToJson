import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class DelimiterDetectorTest {

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


//    @Test
//    void testGetDelimiter_IOExceptionOccured() throws IOException {
//        Path tempFile = Files.createTempFile("emptyFile", ".csv");
//
//        tempFile.toFile().setReadable(false);
//
//        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//        PrintStream outStream = System.err;
//
//        System.setOut(new PrintStream(byteOut));
//        char actual = DelimiterDetector.detectDelimiter(tempFile.toString());
//
//        assertEquals("Error reading the file", byteOut.toString());
//
//        assertEquals('X', byteOut.toString());
//    }

    @Test
    void testGetDelimiter() {
        String path1 = "C:\\Users\\rahul\\Downloads\\comma.csv";
        String path2 = "C:\\Users\\rahul\\Downloads\\semicolon.csv";
        String path3 = "C:\\Users\\rahul\\Downloads\\tab.csv";
        String path4 = "C:\\Users\\rahul\\Downloads\\pipe.csv";
        String path5 = "C:\\Users\\rahul\\Downloads\\invalid.csv";

        char actualDel1 = DelimiterDetector.detectDelimiter(path1);
        char actualDel2 = DelimiterDetector.detectDelimiter(path2);
        char actualDel3 = DelimiterDetector.detectDelimiter(path3);
        char actualDel4 = DelimiterDetector.detectDelimiter(path4);
        char actualDel5 = DelimiterDetector.detectDelimiter(path5);

        assertEquals(',', actualDel1);
        assertEquals(';', actualDel2);
        assertEquals('\t', actualDel3);
        assertEquals('|', actualDel4);
        assertEquals('X', actualDel5);
    }
}
