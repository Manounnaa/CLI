package org.os;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CatCommand {

    /**
     * Executes the cat command with optional redirection to append output to a file.
     * @param tokens The command tokens, where the last token may be a redirection target.
     */
    public static void execute(String[] tokens) {
        boolean appendMode = false;
        String targetFile = null;
        int fileEndIndex = tokens.length;

        // Check for ">>" in the tokens to enable append mode
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals(">>")) {
                appendMode = true;
                targetFile = tokens[i + 1];
                fileEndIndex = i;  // Limit file names to those before ">>"
                break;
            }
        }

        if (appendMode && targetFile != null) {
            for (int i = 1; i < fileEndIndex; i++) {
                String fileName = tokens[i];
                File file = new File(fileName);

                if (!file.exists()) {
                    System.out.println("cat: '" + fileName + "': No such file or directory");
                    continue;
                }

                if (file.isDirectory()) {
                    System.out.println("cat: '" + fileName + "': Is a directory");
                    continue;
                }

                // Read the file and append its contents to the target file
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        System.out.println(line);
                        AppendCommand.execute(targetFile, line);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("cat: error reading file: " + e.getMessage());
                }
            }
        } else {
            // Standard output for cat command without redirection
            for (int i = 1; i < tokens.length; i++) {
                String fileName = tokens[i];
                File file = new File(fileName);

                if (!file.exists()) {
                    System.out.println("cat: '" + fileName + "': No such file or directory");
                    continue;
                }

                if (file.isDirectory()) {
                    System.out.println("cat: '" + fileName + "': Is a directory");
                    continue;
                }

                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        System.out.println(scanner.nextLine());
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("cat: error reading file: " + e.getMessage());
                }
            }
        }
    }
}
