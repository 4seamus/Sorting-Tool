package sorting;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> inputCaptured;
        String opMode = args.length < 2 ? "word" : args[1];

        // handle input
        inputCaptured = switch (opMode) {
            case "line" -> readLines(scanner);
            default -> readTokens(scanner);
        };

        // handle output
        switch (opMode) {
            case "line" -> processLine(inputCaptured);
            case "word" -> processWord(inputCaptured);
            case "long" -> processLong(inputCaptured);
            default -> processWord(inputCaptured);
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

    static void processLong(Map<String, Integer> inputCaptured) {
        int inputCount = inputCaptured.values().stream().reduce(0, Integer::sum);
        System.out.printf("Total numbers: %d.\n", inputCount);
        long maxInput = inputCaptured.keySet().stream().mapToLong(Long::valueOf).max().orElse(0);
        int numOfOccurrences = inputCaptured.get(String.valueOf(maxInput));
        System.out.printf("The greatest number: %d (%d time(s), %.0f%%).\n",
                maxInput, numOfOccurrences, (numOfOccurrences / (double) inputCount * 100));
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
