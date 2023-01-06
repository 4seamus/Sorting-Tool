package sorting;

import sorting.common.CliParameterValue;

import static sorting.io.Entry.*;
import static sorting.io.Display.*;
import static sorting.io.Validation.*;

import java.util.*;

//TODO
// - add logging via JUL
// - add javadoc
public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
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

        // handle input
        inputCaptured = switch (dataType) {
            case LINE -> readLines(scanner);
            case WORD, LONG -> readTokens(scanner, dataType);
            default -> throw new RuntimeException("Invalid parameter value: " + dataType);
        };

        // handle output
        switch (dataType) {
            case LINE -> processLine(inputCaptured, sortingType);
            case WORD -> processWord(inputCaptured, sortingType);
            case LONG -> processLong(inputCaptured, sortingType);
            default -> throw new RuntimeException("Invalid parameter value: " + dataType);
        }
    }
}
