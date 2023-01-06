package sorting.io;

import sorting.common.CliParameterValue;

import java.util.Comparator;
import java.util.Map;

public class Display {
    public static void processLong(Map<String, Integer> inputCaptured, CliParameterValue sortingType) {
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

    public static void processWord(Map<String, Integer> inputCaptured, CliParameterValue sortingType) {
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

    public static void processLine(Map<String, Integer> inputCaptured, CliParameterValue sortingType) {
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
