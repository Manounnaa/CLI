package org.os;

import java.io.FileWriter;
import java.io.IOException;

public class AppendCommand {

    public static void execute(String fileName, String input) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(input + System.lineSeparator());
        } catch (IOException e) {
            System.out.println(">>: error appending to file: " + e.getMessage());
        }
    }
}
