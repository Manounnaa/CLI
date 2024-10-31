package org.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class LsCommand {
    public static String execute(String[] tokens) {
        String currentDirectory = DirectoryUtil.getCurrentDirectory();
        boolean reverseOrder = tokens.length > 1 && "-r".equals(tokens[1]);
        boolean showAll = tokens.length > 1 && "-a".equals(tokens[1]);
        String directory = currentDirectory;
        String pattern = "*"; // Default pattern

        // Handle tokens for path and patterns
        if (tokens.length > 1) {
            if (tokens[1].contains("*")) {
                directory = currentDirectory;
                pattern = tokens[1];
            } else {
                directory = tokens[1];
                if (tokens.length > 2) {
                    pattern = tokens[2].contains("*") ? tokens[2] : "*";
                }
            }
        }

        Path resolvedPath = Paths.get(directory).normalize();
        StringBuilder output = new StringBuilder();

        if (reverseOrder) {
            output.append(lsDirectoryReverse(resolvedPath.toString(), pattern));
        } else if (showAll) {
            output.append(lsShowAll(resolvedPath.toString(), pattern));
        } else {
            output.append(lsDirectory(resolvedPath.toString(), pattern));
        }
        return output.toString().trim();
    }

    public static String lsDirectory(String dir, String pattern) {
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

    public static String lsDirectoryReverse(String dir, String pattern) {
        StringBuilder output = new StringBuilder();
        File directory = new File(dir);
        List<String> fileNames = new ArrayList<>();

        if (!directory.exists() || !directory.isDirectory()) {
            output.append("Error: '").append(dir).append("' is not a valid directory.");
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

    public static String lsShowAll(String dir, String pattern) {
        StringBuilder output = new StringBuilder();
        Path path = Paths.get(dir);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
            for (Path entry : stream) {
                output.append(entry.getFileName()).append(System.lineSeparator()); // Show all files
            }
        } catch (IOException e) {
            output.append("Error: ").append(e.getMessage());
        }
        return output.toString();
    }
}
