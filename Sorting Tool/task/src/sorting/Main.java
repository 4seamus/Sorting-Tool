package sorting;

import java.util.*;

// TODO add logging via JUL
public class Main {
    public static void main(final String[] args) {
        // retaining: may be useful for validation // TODO retire or refactor
        // EnumSet<CliParametersValue> dataTypeValues = EnumSet.of(CliParametersValue.WORD, CliParametersValue.LONG, CliParametersValue.LINE);
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> inputCaptured;

        List<String> cliArgumentList = Arrays.asList(args);
        // parse dataType argument, default to 'word' if not provided
        String dataTypeParameter = CliParameter.DATATYPE.getCliParameter();
        int positionOfDataTypeParameter = cliArgumentList.indexOf(dataTypeParameter);
        CliParameterValue dataType = cliArgumentList.contains(dataTypeParameter) ?
                CliParameterValue.valueOf(cliArgumentList.get(positionOfDataTypeParameter + 1).toUpperCase()) : CliParameterValue.WORD;

        // parse sortingType argument, default to 'natural' if not provided
        String sortingTypeParameter = CliParameter.SORTINGTYPE.getCliParameter();
        int positionOfSortingTypeParameter = cliArgumentList.indexOf(sortingTypeParameter);
        CliParameterValue sortingType = cliArgumentList.contains(sortingTypeParameter) ?
                CliParameterValue.valueOf(cliArgumentList.get(positionOfSortingTypeParameter + 1).toUpperCase()) : CliParameterValue.NATURAL;

        // handle input
        inputCaptured = switch (dataType) {
            case LINE -> readLines(scanner);
            case WORD, LONG -> readTokens(scanner);
            default -> readTokens(scanner);
        };

        // handle output
        switch (dataType) {
            case LINE -> processLine(inputCaptured, sortingType);
            case WORD -> processWord(inputCaptured, sortingType);
            case LONG -> processLong(inputCaptured, sortingType);
            default -> throw new RuntimeException("Invalid parameter value: " + dataType);
        }
    }

    static Map<String, Integer> readTokens(Scanner scanner) {
        Map<String, Integer> tokens = new HashMap<>();
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (tokens.containsKey(token)) {
                tokens.put(token, tokens.get(token) + 1);
            } else {
                tokens.put(token, 1);
            }
        }
        return tokens;
    }

    static Map<String, Integer> readLines(Scanner scanner) {
        Map<String, Integer> lines = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (lines.containsKey(line)) {
                lines.put(line, lines.get(line) + 1);
            } else {
                lines.put(line, 1);
            }
        }
        return lines;
    }

    static void processLong(Map<String, Integer> inputCaptured, CliParameterValue sortingType) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        System.out.printf("Total numbers: %d.\n", inputCount);

        if (sortingType == CliParameterValue.NATURAL) {
            System.out.print("Sorted data:");

            inputCaptured.keySet().stream()
                    .mapToLong(Long::parseLong)
                    .sorted()
                    .forEach(key -> System.out.print((" " + key).repeat(inputCaptured.get(String.valueOf(key)))));
            System.out.println();
        } else {
            printNumericByCount(inputCaptured);
        }
    }

    static void processWord(Map<String, Integer> inputCaptured, CliParameterValue sortingType) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        System.out.printf("Total words: %d.\n", inputCount);

        if (sortingType == CliParameterValue.NATURAL) {
            System.out.print("Sorted data:");
            inputCaptured.keySet().stream()
                    .sorted()
                    .forEach(key -> System.out.print((" " + key).repeat(inputCaptured.get(key))));
        } else {
            printAlphaByCount(inputCaptured);
        }
    }

    static void processLine(Map<String, Integer> inputCaptured, CliParameterValue sortingType) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        System.out.printf("Total lines: %d.\n", inputCount);

        if (sortingType == CliParameterValue.NATURAL) {
            System.out.println("Sorted data:");
            inputCaptured.keySet().stream()
                    //.sorted(Comparator.comparingInt(String::length)) // sort by line length
                    .sorted() // spec requires natural sorting after stage 4 for lines
                    .forEach(key -> System.out.println(key.repeat(inputCaptured.get(key))));
        } else {
            printAlphaByCount(inputCaptured);
        }
    }

    private static void printAlphaByCount(Map<String, Integer> inputCaptured) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        // reverse sort inputCaptured by values, print stats as per spec
        inputCaptured.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue)
                        .thenComparing(Map.Entry::getKey))
                .forEach(entry -> System.out.printf("%s: %d time(s), %.0f%%\n",
                        entry.getKey(), entry.getValue(), (entry.getValue() / (double) inputCount * 100)));
    }

    private static void printNumericByCount(Map<String, Integer> inputCaptured) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        inputCaptured.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, Integer>::getValue)
                        .thenComparing(entry -> Long.parseLong(entry.getKey())))
                // .sorted(Comparator.comparingLong((Map.Entry<String, Integer> entry) -> Long.parseLong(entry.getKey()))
                //         .thenComparing(Map.Entry::getValue))
                .forEach(entry -> System.out.printf("%s: %d time(s), %.0f%%\n",
                        entry.getKey(), entry.getValue(), (entry.getValue() / (double) inputCount * 100)));
    }
}
