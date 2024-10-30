package org.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LsCommand {

    public static void execute(String[] tokens) {

        boolean reverseOrder = tokens.length > 1 && "-r".equals(tokens[1]);
        boolean showAll = tokens.length > 1 && "-a".equals(tokens[1]);
        String directory = ".";

        String pattern = "*";

        if (tokens.length > 1 && !reverseOrder&& !showAll) {
            // If tokens[1] has a wildcard, pass it as the pattern
            pattern = tokens[1].contains("*") ? tokens[1] : "*";
            directory = tokens[1].contains("*") ? "." : tokens[1];
        } else if (tokens.length > 2) {
            pattern = tokens[2].contains("*") ? tokens[2] : "*";
            directory = tokens[2].contains("*") ? "." : tokens[2];
        }

        if (reverseOrder) {
            lsDirectoryReverse(directory, pattern);
        }else if (showAll) {
            lsShowAll(directory, pattern);
        }
        else {
            lsDirectory(directory, pattern);
        }
    }

    public static void lsDirectory(String dir, String pattern) {
        Path path = Paths.get(dir);
        try {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
                for (Path entry : stream) {
                    System.out.println(entry.getFileName());
                }
            }
        }
        catch (IOException e) {
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

        for (File file : directory.listFiles()) {
            if (file.getName().matches(pattern.replace("*", ".*"))) {
                fileNames.add(file.getName());
            }
        }

        Collections.sort(fileNames, Collections.reverseOrder());
        fileNames.forEach(fileName -> System.out.println("File: " + fileName));
    }
    public static void lsShowAll(String dir, String pattern) {
        // Todo
    } //showAll
}



//list of things to handle
//ls
//ls path
//ls path1 path2 path3....
//ls filename
//ls ../  ...relative path
//ls *extension  ....wild card
//ls path/*extension