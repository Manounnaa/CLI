package org.os;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CatCommand {

    /**
     * Displays the content of a file.
     * @param fileName The name of the file to display.
     */
    public static void execute(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("cat: '" + fileName + "': No such file or directory");
            return;
        }

        if (file.isDirectory()) {
            System.out.println("cat: '" + fileName + "': Is a directory");
            return;
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
