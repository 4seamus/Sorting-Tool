package sorting;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        // retaining: may be useful for validation // TODO retire or use
        // EnumSet<CliParametersValue> dataTypeValues = EnumSet.of(CliParametersValue.WORD, CliParametersValue.LONG, CliParametersValue.LINE);
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> inputCaptured;

        boolean sortRequested = Arrays.asList(args).contains(CliParameter.SORTINTEGERS.getCliParameter());

        List<String> cliArgumentList = Arrays.asList(args);
        String dataTypeParameter = CliParameter.DATATYPE.getCliParameter();
        int positionOfDataTypeParameter = cliArgumentList.indexOf(dataTypeParameter);
        CliParameterValue operationMode = cliArgumentList.contains(dataTypeParameter) ?
                CliParameterValue.valueOf(cliArgumentList.get(positionOfDataTypeParameter + 1).toUpperCase()) : CliParameterValue.WORD;

        // if the -sortIntegers argument is provided, ignore other arguments
        if (sortRequested) operationMode = CliParameterValue.LONG;

        // handle input
        inputCaptured = switch (operationMode) {
            case LINE -> readLines(scanner);
            case WORD, LONG -> readTokens(scanner);
        };

        // handle output
        switch (operationMode) {
            case LINE -> processLine(inputCaptured);
            case WORD -> processWord(inputCaptured);
            case LONG -> processLong(inputCaptured, sortRequested);
            default -> throw new RuntimeException("Invalid parameter value: " + operationMode);
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

    static void processLong(Map<String, Integer> inputCaptured, boolean sortRequested) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        System.out.printf("Total numbers: %d.\n", inputCount);

        if (sortRequested) {
            StringBuilder valuesSorted = new StringBuilder();
            long[] numbers = inputCaptured.keySet().stream()
                    .mapToLong(Long::parseLong)
                    .sorted().toArray();

            // map compresses entry (list of values -> value : count), uncompress for output
            for (long n : numbers) {
                for (long i = 0; i < inputCaptured.get(String.valueOf(n)); i++) {
                    valuesSorted.append(n).append(" ");
                }
            }
            System.out.println("Sorted data: " + valuesSorted);
        } else {
            long maxInput = inputCaptured.keySet().stream().mapToLong(Long::valueOf).max().orElse(0);
            int numOfOccurrences = inputCaptured.get(String.valueOf(maxInput));
            System.out.printf("The greatest number: %d (%d time(s), %.0f%%).\n",
                    maxInput, numOfOccurrences, (numOfOccurrences / (double) inputCount * 100));
        }
    }

    static void processWord(Map<String, Integer> inputCaptured) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        System.out.printf("Total words: %d.\n", inputCount);

        int maxLength = inputCaptured.keySet().stream()
                .mapToInt(String::length)
                //.filter(input -> input >= 0)
                .max()
                .orElse(0);

        List<String> maxInput = inputCaptured.keySet().stream()
                .filter(i -> i.length() == maxLength)
                .sorted()
                .toList();

        for (String input : maxInput) {
            int numOfOccurrences = inputCaptured.get(input);
            System.out.printf("The longest word: %s (%d time(s), %.0f%%).\n",
                    input, numOfOccurrences, (numOfOccurrences / (double) inputCount * 100));
        }
    }

    static void processLine(Map<String, Integer> inputCaptured) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        System.out.printf("Total lines: %d.\n", inputCount);

        int maxLength = inputCaptured.keySet().stream()
                .mapToInt(String::length)
                //.filter(input -> input >= 0)
                .max()
                .orElse(0);

        List<String> maxInput = inputCaptured.keySet().stream()
                .filter(i -> i.length() == maxLength)
                .sorted()
                .toList();

        for (String input : maxInput) {
            int numOfOccurrences = inputCaptured.get(input);
            System.out.printf("The longest line:\n%s\n(%d time(s), %.0f%%).\n",
                    input, numOfOccurrences, (numOfOccurrences / (double) inputCount * 100));
        }
    }
}
