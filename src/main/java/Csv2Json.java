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

            if (opts.prettyPrint()) {
                out.useDefaultPrettyPrinter();
            }

            CSVParser parser = CSVFormat.DEFAULT
                                        .builder()
                                        .setDelimiter(opts.delimiter())
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
    }
}
