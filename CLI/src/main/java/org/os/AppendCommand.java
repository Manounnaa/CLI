package org.os;

import java.io.FileWriter;
import java.io.IOException;

public class AppendCommand {

    public static void execute(String fileName, String input) {
        String currentDir = DirectoryUtil.getCurrentDirectory();
        FileWriter writer = null;

        try {
            // Create the FileWriter in the current directory
            writer = new FileWriter(currentDir + "/" + fileName, true);
            writer.write(input + System.lineSeparator());
        }
        catch (IOException e) {
            System.out.println(">>: error appending to file: " + e.getMessage());
        }
        finally {
            if (writer != null) {
                try {
                    writer.close(); // Close the FileWriter
                } catch (IOException e) {
                    System.out.println(">>: error closing file writer: " + e.getMessage());
                }
            }
        }
    }
}
