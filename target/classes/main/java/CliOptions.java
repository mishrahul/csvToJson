import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public final class CliOptions {
    private final String input;
    private final String output;
    private final boolean prettyPrint;

    private static final String jarFileNmae = "csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar";
    private static final String usageSyntax = "Usage syntax: java -jar " + jarFileNmae + " inputFileName.csv outputFileName.json [--pretty] OR [--help]";

    private CliOptions(String input, String output, boolean prettyPrint) {
        this.input = input;
        this.output = output;
        this.prettyPrint = prettyPrint;

    }

    public static CliOptions parse(String[] args) {
        if(args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))) {
            System.out.println(usageSyntax);
            return null;

        }
        else if (args.length < 2) {
            throw new RuntimeException("Insufficient arguments, use [--help] or [-h] for help");
        }


        String input = args[0];
        String output = args[1];
        boolean pretty = false;



        for (int i = 2; i < args.length; i++) {

            if (args[i].equals("--pretty")) {
                pretty = true;
            }
            else {
                System.err.println("Unknown argument: " + args[i]);
            }
        }

        return new CliOptions(input, output, pretty);
    }


    public String input() { return input; }
    public String output() { return output; }
    public boolean prettyPrint() { return prettyPrint; }
}
