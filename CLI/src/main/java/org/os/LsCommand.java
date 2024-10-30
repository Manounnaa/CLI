package org.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class LsCommand {
    private static String currentDirectory = "."; // Track current directory
    public static void execute(String[] tokens) {
        boolean reverseOrder = tokens.length > 1 && "-r".equals(tokens[1]);
        boolean showAll = tokens.length > 1 && "-a".equals(tokens[1]);
        String directory = currentDirectory; // Default to the current directory

        String pattern = "*";

        if (tokens.length > 1 && !reverseOrder && !showAll) {
            pattern = tokens[1].contains("*") ? tokens[1] : "*";
            directory = tokens[1].contains("*") ? currentDirectory : tokens[1];
        } else if (tokens.length > 2) {
            pattern = tokens[2].contains("*") ? tokens[2] : "*";
            directory = tokens[2].contains("*") ? currentDirectory : tokens[2];
        }

        if (reverseOrder) {
            lsDirectoryReverse(directory, pattern);
        } else if (showAll) {
            lsShowAll(directory, pattern);
        } else {
            lsDirectory(directory, pattern);
        }
    }

    // Method to change the current directory
    public static String getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(String dir) {
        currentDirectory = dir;
    }

    public static void lsDirectory(String dir, String pattern) {
        Path path = Paths.get(dir);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
            for (Path entry : stream) {
                if (!Files.isHidden(entry)) { // Exclude hidden files
                    System.out.println(entry.getFileName());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void lsDirectoryReverse(String dir, String pattern) {
        File directory = new File(dir);
        List<String> fileNames = new ArrayList<>();

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Error: '" + dir + "' is not a valid directory.");
            return;
        }

        Pattern regexPattern = Pattern.compile(pattern.replace("*", ".*"));

        for (File file : directory.listFiles()) {
            if (!file.isHidden() && regexPattern.matcher(file.getName()).matches()) {
                fileNames.add(file.getName());
            }
        }

        Collections.sort(fileNames, Collections.reverseOrder());
        fileNames.forEach(System.out::println);
    }

    public static void lsShowAll(String dir, String pattern) {
        Path path = Paths.get(dir);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName()); // Show all files, including hidden
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
//list of things to handle
//ls
//ls path
//ls path1 path2 path3....
//ls filename
//ls ../  ...relative path
//ls *extension  ....wild card
//ls path/*extension
