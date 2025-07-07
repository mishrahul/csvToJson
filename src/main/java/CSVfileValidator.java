import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVfileValidator {

    public static boolean isValidFile(CliOptions args) throws FileNotFoundException {
        String inputFile = args.input();
        Path inputFilePath = Path.of(inputFile);

        if(Files.exists(inputFilePath)) {
            //if(Files.isReadable(inputFilePath)) {
                return isCSVfile(inputFile);
            //} else return false;
        }
        throw new FileNotFoundException("No such input file exists");

    }


    private static boolean isCSVfile(String inputFile) {
        String fileExtension = FilenameUtils.getExtension(inputFile);
        return fileExtension.equals("csv");
    }


}
