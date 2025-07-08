import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.csv.*;

import java.io.*;
import java.nio.file.*;
import java.util.Map;



public final class Csv2Json {

    static CliOptions opts;
    public static void main(String[] args) {
        try {
            opts = CliOptions.parse(args);
            if (opts==null) {
                return;
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return;
        }


        try {
            if(!CSVfileValidator.isValidFile(opts)) {
                System.err.println("The input file is not a valid .csv file");
                return;
            }
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return;
        }

        try {
            BufferedReader in = Files.newBufferedReader(Paths.get(opts.input()));
            JsonGenerator out = new JsonFactory().createGenerator(Files.newBufferedWriter(Paths.get(opts.output())));

            char delimiter = DelimiterDetector.detectDelimiter(opts.input());
            if (delimiter == 'X') {
                System.err.println("The input csv file has an illegal delimiter");
                return;
            }

            jsonWriter(in, out, opts.prettyPrint(), delimiter);


        }

        catch (Exception e) {
            System.err.println("An exception occured: " + e.getMessage());
        }

    }

    static void jsonWriter(BufferedReader in, JsonGenerator out, boolean pretty, char delimiter) throws IOException {
        if (pretty) out.useDefaultPrettyPrinter();


        CSVParser parser = CSVFormat.DEFAULT
                .builder()
                .setDelimiter(delimiter)
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
        in.close();
        out.close();
    }
}
