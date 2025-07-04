import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.csv.*;

import java.io.*;
import java.nio.file.*;
import java.util.Map;

public final class Csv2Json {
    public static void main(String[] args) throws IOException {
        CliOptions opts = CliOptions.parse(args);

        try (BufferedReader in = Files.newBufferedReader(Paths.get(opts.input()));
             JsonGenerator out = new JsonFactory()
                     .createGenerator(Files.newBufferedWriter(Paths.get(opts.output()))))
        {
            int inputFileLen = opts.input().length();
            if(!opts.input().subSequence(inputFileLen-3, inputFileLen).equals("csv")) {
                System.err.println("Error: Input file is not a valid .csv file");
                System.exit(1);
            }

            if (opts.prettyPrint()) {
                out.useDefaultPrettyPrinter();
            }

            CSVParser parser = CSVFormat.DEFAULT
                                        .builder()
                                        .setDelimiter(DelimiterDetector.detectDelimiter(opts.input()))
                                        .setHeader()
                                        .setSkipHeaderRecord(true)
                                        .build()
                                        .parse(in);

            out.writeStartArray();
            for (CSVRecord rec : parser) {
                out.writeStartObject();
                for (Map.Entry<String, String> e : rec.toMap().entrySet()) {
                    out.writeStringField(e.getKey(), e.getValue());
                }
                out.writeEndObject();
            }
            out.writeEndArray();
        }
        catch (NoSuchFileException e) {
            System.err.println(e.getMessage() + " File not found: Please ensure the input file path is correct.");
        }
        catch (IOException ioe) {
            System.err.println(ioe.getMessage() + " An error occured.");
        }

    }
}
