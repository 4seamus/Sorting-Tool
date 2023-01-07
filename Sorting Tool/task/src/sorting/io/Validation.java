package sorting.io;

import sorting.common.CliParameter;
import sorting.common.CliParameterValue;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Validation { // parse and validate CLI parameters
    public static void validateParameters(String[] args) {
        Set<String> parameterLiterals = new HashSet<>();
        Arrays.stream(CliParameter.values()).forEach(e -> parameterLiterals.add(e.getCliParameter()));
        Arrays.stream(args)
                .filter(a -> a.startsWith("-"))
                .filter(a -> !parameterLiterals.contains(a))
                .forEach(a -> System.out.printf("\"%s\" is not a valid parameter. It will be skipped.\n", a));
    }

    public static void validateDataType(String[] args) {
        EnumSet<CliParameterValue> dataTypeValues = EnumSet.of(CliParameterValue.WORD, CliParameterValue.LONG, CliParameterValue.LINE);
        Set<String> dataTypeValueLiterals = new HashSet<>();
        dataTypeValues.forEach(e -> dataTypeValueLiterals.add(e.getCliParameterValue()));

        List<String> cliArgumentList = Arrays.asList(args);
        String dataTypeParameter = CliParameter.DATATYPE.getCliParameter();
        int positionOfDataTypeParameter = cliArgumentList.indexOf(dataTypeParameter);

        if (positionOfDataTypeParameter > -1
                && ((positionOfDataTypeParameter == cliArgumentList.size() -1)
                    || ((positionOfDataTypeParameter < cliArgumentList.size() - 1) && !dataTypeValueLiterals.contains(cliArgumentList.get(positionOfDataTypeParameter + 1))))) {
            throw new IllegalArgumentException("No data type defined!");
        }
    }

    public static void validateSortingType(String[] args) {
        EnumSet<CliParameterValue> sortingTypeValues = EnumSet.of(CliParameterValue.NATURAL, CliParameterValue.BYCOUNT);
        Set<String> sortingTypeValueLiterals = new HashSet<>();
        sortingTypeValues.forEach(e -> sortingTypeValueLiterals.add(e.getCliParameterValue()));

        List<String> cliArgumentList = Arrays.asList(args);
        String sortingTypeParameter = CliParameter.SORTINGTYPE.getCliParameter();
        int positionOfSortingTypeParameter = cliArgumentList.indexOf(sortingTypeParameter);

        if (positionOfSortingTypeParameter > -1
                && ((positionOfSortingTypeParameter == cliArgumentList.size() -1)
                || ((positionOfSortingTypeParameter < cliArgumentList.size() - 1) && !sortingTypeValueLiterals.contains(cliArgumentList.get(positionOfSortingTypeParameter + 1))))) {
            throw new IllegalArgumentException("No sorting type defined!");
        }
    }

    public static CliParameterValue parseDataType(String[] args) {
        List<String> cliArgumentList = Arrays.asList(args);
        String dataTypeParameter = CliParameter.DATATYPE.getCliParameter();
        int positionOfDataTypeParameter = cliArgumentList.indexOf(dataTypeParameter);

        // default data type is 'word'
        return cliArgumentList.contains(dataTypeParameter) ?
                CliParameterValue.valueOf(cliArgumentList.get(positionOfDataTypeParameter + 1).toUpperCase()) : CliParameterValue.WORD;
    }

    public static CliParameterValue parseSortingType(String[] args) {
        List<String> cliArgumentList = Arrays.asList(args);
        String sortingTypeParameter = CliParameter.SORTINGTYPE.getCliParameter();
        int positionOfSortingTypeParameter = cliArgumentList.indexOf(sortingTypeParameter);

        // default sorting type is 'natural'
        return cliArgumentList.contains(sortingTypeParameter) ?
                CliParameterValue.valueOf(cliArgumentList.get(positionOfSortingTypeParameter + 1).toUpperCase()) : CliParameterValue.NATURAL;
    }

    public static Scanner configureScanner(String[] args) {
        List<String> cliArgumentList = Arrays.asList(args);
        String inputFileParameter = CliParameter.INPUTFILE.getCliParameter();
        int positionOfInputFileParameter = cliArgumentList.indexOf(inputFileParameter);

        return cliArgumentList.contains(inputFileParameter) ?
                new Scanner(cliArgumentList.get(positionOfInputFileParameter + 1)) : new Scanner(System.in);
    }

    public static PrintWriter configureOutput(String[] args) throws FileNotFoundException {
        List<String> cliArgumentList = Arrays.asList(args);
        String outputFileParameter = CliParameter.OUTPUTFILE.getCliParameter();
        int positionOfOutputFileParameter = cliArgumentList.indexOf(outputFileParameter);

        PrintWriter printWriter = new PrintWriter(System.out, true);
        if (cliArgumentList.contains(outputFileParameter)) {
            String fileName = cliArgumentList.get(positionOfOutputFileParameter + 1);
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            printWriter = new PrintWriter(fileOutputStream, true);
        }
        return printWriter;
    }
}
