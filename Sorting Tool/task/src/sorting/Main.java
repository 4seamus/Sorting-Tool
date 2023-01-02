package sorting;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Long, Integer> numbers = new HashMap<>();

        int entryCount = 0;
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            entryCount++;
            // write your code here
            if (numbers.containsKey(number)) {
                numbers.put(number, numbers.get(number) + 1);
            } else {
                numbers.put(number, 1);
            }
        }

        System.out.printf("Total numbers: %d.\n", entryCount);
        long maxEntered = numbers.keySet().stream().max(Long::compareTo).orElse(0L);
        int numOfOccurrences = numbers.get(maxEntered);
        System.out.printf("The greatest number: %d (%d time(s)).\n", maxEntered, numOfOccurrences);
    }
}
