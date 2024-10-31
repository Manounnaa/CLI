package org.os;

import java.util.Arrays;
import java.util.List;

public class SortNameCommand {

    public static String[] execute(String[] input) {
        // Convert the array to a list
        List<String> lines = Arrays.asList(input);

        // Sort the lines case-insensitively
        lines.sort(String::compareToIgnoreCase);

        // Print each sorted line
        return lines.toArray(new String[lines.size()]);
    }
}