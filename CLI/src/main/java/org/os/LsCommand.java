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
    public static String execute(String[] tokens) {
        boolean reverseOrder = tokens.length > 1 && "-r".equals(tokens[1]);
        boolean showAll = tokens.length > 1 && "-a".equals(tokens[1]);
        String directory = DirectoryUtil.getCurrentDirectory(); // Use DirectoryUtil for current directory

        String pattern = "*";
        StringBuilder output = new StringBuilder(); // Use StringBuilder to capture output


        if (tokens.length > 1 && !reverseOrder && !showAll) {
            pattern = tokens[1].contains("*") ? tokens[1] : "*";
            directory = tokens[1].contains("*") ? currentDirectory : tokens[1];
        } else if (tokens.length > 2) {
            pattern = tokens[2].contains("*") ? tokens[2] : "*";
            directory = tokens[2].contains("*") ? currentDirectory : tokens[2];
        }

        if (reverseOrder) {
            output.append(lsDirectoryReverse(directory, pattern));
        } else if (showAll) {
            output.append(lsShowAll(directory, pattern));
        } else {
            output.append(lsDirectory(directory, pattern));
        }
        return output.toString().trim();

    }

    // Method to change the current directory


    public static Object lsDirectory(String dir, String pattern) {
        StringBuilder output = new StringBuilder();

        Path path = Paths.get(dir);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
            for (Path entry : stream) {
                if (!Files.isHidden(entry)) { // Exclude hidden files
                    output.append(entry.getFileName()).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            output.append("Error: ").append(e.getMessage());
        }
        return output.toString();
    }

    public static Object lsDirectoryReverse(String dir, String pattern) {
        StringBuilder output = new StringBuilder();
        File directory = new File(dir);
        List<String> fileNames = new ArrayList<>();

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Error: '" + dir + "' is not a valid directory.");
            return output.toString();
        }

        Pattern regexPattern = Pattern.compile(pattern.replace("*", ".*"));

        for (File file : directory.listFiles()) {
            if (!file.isHidden() && regexPattern.matcher(file.getName()).matches()) {
                fileNames.add(file.getName());
            }
        }

        Collections.sort(fileNames, Collections.reverseOrder());
        fileNames.forEach(name -> output.append(name).append(System.lineSeparator()));

        return output.toString();
    }

    public static Object lsShowAll(String dir, String pattern) {
        StringBuilder output = new StringBuilder();

        Path path = Paths.get(dir);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
            for (Path entry : stream) {
                output.append(entry.getFileName()).append(System.lineSeparator()); // Show all files, including hidden
            }
        } catch (IOException e) {
            output.append("Error: ").append(e.getMessage());
        }
        return output.toString();
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
