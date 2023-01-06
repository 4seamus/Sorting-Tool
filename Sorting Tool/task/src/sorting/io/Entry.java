package sorting.io;

import sorting.common.CliParameterValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Entry {
    public static Map<String, Integer> readTokens(Scanner scanner, CliParameterValue dataType) {
        Map<String, Integer> tokens = new HashMap<>();
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (dataType == CliParameterValue.LONG) {
                try {
                    Long.parseLong(token);
                } catch (NumberFormatException e) {
                    System.out.printf("\"%s\" is not a long. It will be skipped.\n", token);
                    continue;
                }
            }
            if (tokens.containsKey(token)) {
                tokens.put(token, tokens.get(token) + 1);
            } else {
                tokens.put(token, 1);
            }
        }
        return tokens;
    }

    public static Map<String, Integer> readLines(Scanner scanner) {
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
}
