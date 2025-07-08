import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DelimiterDetector {


    public static char detectDelimiter(String inputFilePathStr)  {
        char delimiter = 'X';

        try {
            Path path = Path.of(inputFilePathStr);
            if(!Files.exists(path)) throw new FileNotFoundException();
            else {
                BufferedReader reader = Files.newBufferedReader(path);
                String line = reader.readLine();
                //System.out.println(line.length());
                if (line == null) {
                    throw new IOException("Error reading the file");

                } else {
                    if (line.contains(";")) delimiter = ';';
                    else if (line.contains("|")) delimiter = '|';
                    else if (line.contains("\t")) delimiter = '\t';
                    else if (line.contains(",")) delimiter = ',';
                }

            }

        } catch (FileNotFoundException e) {
            System.err.println("The file does not exist");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());

        }


        return delimiter;
    }

}
