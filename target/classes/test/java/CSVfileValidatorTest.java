import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class
CSVfileValidatorTest {

    @Mock
    private CliOptions opts;


    @Test
    void isValid_NoFileExists() throws IOException {

        String[] args = {"nonExistentFile.null", "output.json", "--pretty"};
        opts = CliOptions.parse(args);

        Throwable exception  = assertThrows(FileNotFoundException.class, () -> CSVfileValidator.isValidFile(opts));

        assertEquals("No such input file exists", exception.getMessage());
    }


    @Test
    void isValid_NotCSVfile() throws IOException {
        Path tempFile = Files.createTempFile("someFile", ".html");
        String[] args = {tempFile.toString(), "output.json", "--pretty"};

        assertTrue(Files.exists(Path.of(tempFile.toString())));
        opts = CliOptions.parse(args);
        assertFalse(CSVfileValidator.isValidFile(opts));

        Files.delete(tempFile);
    }

    @Test
    void isValid_isCSV_HappyPath() throws IOException {
        Path tempFile = Files.createTempFile("someFile", ".csv");

        String[] args = {tempFile.toString(), "output.json", "--pretty"};

        opts = CliOptions.parse(args);

        assertNotNull(opts);
        assertTrue(CSVfileValidator.isValidFile(opts));

        Files.delete(tempFile);
    }
}
