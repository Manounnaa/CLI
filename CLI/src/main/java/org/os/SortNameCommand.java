package org.os;

import java.util.Arrays;
import java.util.Comparator;

public class SortNameCommand {
    public static String execute(String[] commandParts) {
        if (commandParts == null || commandParts.length == 0) {
            return "";
        }

        // Get the input array to sort
        String[] sortedLines = commandParts;
        String[] args = Arrays.copyOfRange(commandParts, 1, commandParts.length);

        // Parse flags - make variables final
        final boolean reverse = parseReverse(args);
        final boolean numeric = parseNumeric(args);
        final boolean ignoreCase = !parseCaseSensitive(args);

        // Create appropriate comparator
        Comparator<String> comparator;

        if (numeric) {
            comparator = (s1, s2) -> {
                try {
                    double n1 = Double.parseDouble(s1.trim());
                    double n2 = Double.parseDouble(s2.trim());
                    return Double.compare(n1, n2);
                } catch (NumberFormatException e) {
                    return ignoreCase ? s1.compareToIgnoreCase(s2) : s1.compareTo(s2);
                }
            };
        } else {
            comparator = ignoreCase ?
                    String::compareToIgnoreCase :
                    String::compareTo;
        }

        // Apply reverse if needed
        if (reverse) {
            comparator = comparator.reversed();
        }

        // Sort the lines using the configured comparator
        Arrays.sort(sortedLines, comparator);

        // Convert the sorted array to a single string with line breaks
        return String.join("\n", sortedLines);
    }

    public static String[] execute(String[] input, String[] args) {
        if (input == null || input.length == 0) {
            return new String[0];
        }

        // Create a copy of the input array to avoid modifying the original
        String[] sortedLines = Arrays.copyOf(input, input.length);

        // Parse flags - make variables final
        final boolean reverse = parseReverse(args);
        final boolean numeric = parseNumeric(args);
        final boolean ignoreCase = !parseCaseSensitive(args);

        // Create appropriate comparator
        Comparator<String> comparator;

        if (numeric) {
            comparator = (s1, s2) -> {
                try {
                    double n1 = Double.parseDouble(s1.trim());
                    double n2 = Double.parseDouble(s2.trim());
                    return Double.compare(n1, n2);
                } catch (NumberFormatException e) {
                    return ignoreCase ? s1.compareToIgnoreCase(s2) : s1.compareTo(s2);
                }
            };
        } else {
            comparator = ignoreCase ?
                    String::compareToIgnoreCase :
                    String::compareTo;
        }

        // Apply reverse if needed
        if (reverse) {
            comparator = comparator.reversed();
        }

        // Sort the lines using the configured comparator
        Arrays.sort(sortedLines, comparator);

        return sortedLines;
    }

    private static boolean parseReverse(String[] args) {
        if (args == null) return false;
        return Arrays.stream(args)
                .map(String::toLowerCase)
                .anyMatch(arg -> arg.equals("-r") || arg.equals("--reverse"));
    }

    private static boolean parseNumeric(String[] args) {
        if (args == null) return false;
        return Arrays.stream(args)
                .map(String::toLowerCase)
                .anyMatch(arg -> arg.equals("-n") || arg.equals("--numeric"));
    }

    private static boolean parseCaseSensitive(String[] args) {
        if (args == null) return false;
        return Arrays.stream(args)
                .map(String::toLowerCase)
                .anyMatch(arg -> arg.equals("-c") || arg.equals("--case-sensitive"));
    }
}
