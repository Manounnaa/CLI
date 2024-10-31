package org.os;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CatCommand {

    /**
     * Executes the cat command with optional redirection to append output to a file.
     * @param tokens The command tokens, where the last token may be a redirection target.
     */
    public static String execute(String[] tokens) {

        StringBuilder output = new StringBuilder();

        String targetFile = null;
        int fileEndIndex = tokens.length;
        // Standard output for cat command without redirection
        for (int i = 1; i < tokens.length; i++) {
            String fileName = tokens[i];
            File file = new File(fileName);

            if (!file.exists()) {
                output.append("cat: '").append(fileName).append("': No such file or directory\n");
                continue;
            }

            if (file.isDirectory()) {
                output.append("cat: '").append(fileName).append("': Is a directory\n");
                continue;
            }

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    output.append(scanner.nextLine()).append("\n");
                }
            } catch (FileNotFoundException e) {
                output.append("cat: error reading file: ").append(e.getMessage()).append("\n");
            }
        }
        return output.toString();

    }

}
