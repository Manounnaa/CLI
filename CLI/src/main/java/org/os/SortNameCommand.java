package main.java.org.os;

import java.util.Arrays;
import java.util.List;

public class SortNameCommand {

    public static void execute(String input) {
        List<String> lines = Arrays.asList(input.split(System.lineSeparator())); //convert input to lines
        lines.sort(String::compareToIgnoreCase);
        lines.forEach(System.out::println);
    }
}
