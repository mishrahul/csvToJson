import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DelimiterDetector {

    public static char detectDelimiter(String inputFilePathStr) throws IOException {
        char delimiter;

        BufferedReader reader = Files.newBufferedReader(Path.of(inputFilePathStr));
            if(reader.readLine().contains(";")) delimiter = ';';
            else if(reader.readLine().contains("|")) delimiter = '|';
            else if(reader.readLine().contains("\t")) delimiter = '\t';
            else delimiter = ',';


        return delimiter;
    }

}
