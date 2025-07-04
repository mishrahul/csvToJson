import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DelimiterDetectorTest {

    @Test
    void testGetDelimiter() throws IOException {

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
