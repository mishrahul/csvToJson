import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;

public final class CliOptions {
    private final String input;
    private final String output;
    //private final char delimiter;
    private final boolean prettyPrint;

    private static final String jarFileNmae = "csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar";
    private static final String usageSyntax = "Usage syntax: java -jar " + jarFileNmae + " inputFileName.csv outputFileName.json [-d \",\" | \"\\t\"] [--pretty] [--help]";

    private CliOptions(String input, String output, char delimiter, boolean prettyPrint) {
        this.input = input;
        this.output = output;
        //this.delimiter = delimiter;
        this.prettyPrint = prettyPrint;

    }

    public static CliOptions parse(String[] args) {
        if(args.length == 1 && (args[0].equals("--help") || args[0].equals("-h"))) {
            System.out.println(usageSyntax);
            System.exit(1);
        }
        if (args.length < 2) {
            System.err.println("Not a valid syntax, use [--help] or [-h] for help");
            System.exit(1);
        }


        String input = args[0];
        String output = args[1];
        char delimiter = ',';
        boolean pretty = false;

//        java -jar csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar
//        logi.jpg output.json --pretty
//


        for (int i = 2; i < args.length; i++) {
//            if (args[i].equals("-d") && i + 1 < args.length) {
//                String delimArg = args[++i];
//
//                if (delimArg.equals("\t")) {
//                    delimiter = '\t';
//                }
//                else if (delimArg.equals(",")) {
//                    continue;
//                }
//                else {
//                    System.err.println("Not a valid delimiter" +"\n" +
//                            "Only ',' and '\\t' are allowed as delimiters");
//                    System.exit(1);
//                }
//
//            }

            if (args[i].equals("--pretty")) {
                pretty = true;
            }
            else {
                System.err.println("Unknown argument: " + args[i]);
                System.exit(1);
            }
        }




        return new CliOptions(input, output, delimiter, pretty);
    }


    public String input() { return input; }
    public String output() { return output; }
    //public char delimiter() { return delimiter; }
    public boolean prettyPrint() { return prettyPrint; }
}
