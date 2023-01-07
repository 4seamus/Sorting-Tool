package sorting;

import sorting.common.CliParameterValue;

import static sorting.io.Input.*;
import static sorting.io.Output.*;
import static sorting.io.Validation.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

//TODO
// - add logging via JUL
// - add javadoc
public class Main {
    public static void main(final String[] args) {
        Map<String, Integer> inputCaptured;

        try {
            validateParameters(args);
            validateDataType(args);
            validateSortingType(args);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }

        CliParameterValue dataType = parseDataType(args);
        CliParameterValue sortingType = parseSortingType(args);
        Scanner scanner = configureScanner(args);
        try (PrintWriter outputTarget = configureOutput(args)) {

            // handle input
            inputCaptured = switch (dataType) {
                case LINE -> readLines(scanner);
                case WORD, LONG -> readTokens(scanner, dataType);
                default -> throw new RuntimeException("Invalid parameter value: " + dataType);
            };

            // handle output
            switch (dataType) {
                case LINE -> processLine(inputCaptured, sortingType, outputTarget);
                case WORD -> processWord(inputCaptured, sortingType, outputTarget);
                case LONG -> processLong(inputCaptured, sortingType, outputTarget);
                default -> throw new RuntimeException("Invalid parameter value: " + dataType);
            }
        } catch (FileNotFoundException e) {
            System.out.println("I/O Error:");
            e.printStackTrace();
        }
    }
}
