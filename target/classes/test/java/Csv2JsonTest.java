import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sound.midi.SysexMessage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Csv2JsonTest {

    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @Mock
    private CliOptions cliOptions;

    @Mock
    private DelimiterDetector delimiterDetector;

    @InjectMocks
    private Csv2Json csv2Json;



    @BeforeEach
    void before() {
        System.setErr(new PrintStream(errorStream));
    }

    @AfterEach
    void after() {
        System.setErr(originalErr);
    }

    @Test
    void notCSVfile() {

        String inputPath = "C:\\Users\\rahul\\Downloads\\text.json";
        String[] args = {inputPath, "Output.json", "--pretty"};
       // cliOptions = CliOptions.parse(args);
        //Csv2Json.main(args);

        Throwable exception = assertThrows(RuntimeException.class, () -> Csv2Json.main(args));
        //System.out.println(cliOptions.output());
        assertEquals("Error: Input file is not a .csv file\r\n", errorStream.toString());
        //assertEquals();
    }

    @Test
    void noSuchFile() {
        String inputPath = "C:\\Users\\rahul\\Downloads\\wall.csv";
        String[] args = {inputPath, "Output.json", "--pretty"};
        // cliOptions = CliOptions.parse(args);
        Csv2Json.main(args);

        Throwable exception = assertThrows(NoSuchFileException.class, () -> Csv2Json.main(args));
        assertEquals("vyuer", exception.getMessage());
    }

}
