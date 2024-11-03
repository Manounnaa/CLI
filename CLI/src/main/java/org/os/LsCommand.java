package org.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class LsCommand {
    private static final String CURRENT_DIR = ".";
    private static final String PARENT_DIR = "..";

    public static String execute(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return "Error: Invalid command";
        }

        boolean reverseOrder = tokens.length > 1 && "-r".equals(tokens[1]);
        boolean showAll = tokens.length > 1 && "-a".equals(tokens[1]);
        String directory = DirectoryUtil.getCurrentDirectory();
        String pattern = "*";

        // Parse directory and pattern from arguments
        if (tokens.length > 1 && !reverseOrder && !showAll) {
            if (tokens[1].contains("*")) {
                pattern = tokens[1];
            } else {
                directory = resolveDirectory(tokens[1]);
            }
        }

        if (tokens.length > 2) {
            if (tokens[2].contains("*")) {
                pattern = tokens[2];
            } else {
                directory = resolveDirectory(tokens[2]);
            }
        }

        try {
            if (reverseOrder) {
                return lsDirectoryReverse(directory, pattern);
            } else if (showAll) {
                return lsShowAll(directory, pattern);
            } else {
                return lsDirectory(directory, pattern);
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Resolve current or parent directory if specified as "." or ".."
    private static String resolveDirectory(String dir) {
        if (CURRENT_DIR.equals(dir)) {
            return DirectoryUtil.getCurrentDirectory();
        } else if (PARENT_DIR.equals(dir)) {
            Path parentPath = Paths.get(DirectoryUtil.getCurrentDirectory()).getParent();
            return parentPath != null ? parentPath.toString() : DirectoryUtil.getCurrentDirectory();
        } else {
            return dir;
        }
    }

    public static String lsDirectory(String dir, String pattern) {
        StringBuilder output = new StringBuilder();
        Path path = Paths.get(dir).normalize();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
            for (Path entry : stream) {
                if (!Files.isHidden(entry)) {
                    output.append(entry.getFileName()).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
        return output.toString();
    }

    public static String lsDirectoryReverse(String dir, String pattern) {
        StringBuilder output = new StringBuilder();
        File directory = new File(dir);
        List<String> fileNames = new ArrayList<>();

        if (!directory.exists() || !directory.isDirectory()) {
            return "Error: '" + dir + "' is not a valid directory.";
        }

        Pattern regexPattern = Pattern.compile(pattern.replace("*", ".*"));
        File[] files = directory.listFiles();

        if (files == null) {
            return "Error: Unable to list directory contents";
        }

        for (File file : files) {
            if (!file.isHidden() && regexPattern.matcher(file.getName()).matches()) {
                fileNames.add(file.getName());
            }
        }

        Collections.sort(fileNames, Collections.reverseOrder());
        for (String name : fileNames) {
            output.append(name).append(System.lineSeparator());
        }

        return output.toString();
    }

    public static String lsShowAll(String dir, String pattern) {
        StringBuilder output = new StringBuilder();
        Path path = Paths.get(dir).normalize();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
            for (Path entry : stream) {
                output.append(entry.getFileName()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
        return output.toString();
    }
}


//------------------------------
//package org.os;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class LsCommand {
//    private static final String CURRENT_DIR = ".";
//
//    public static String execute(String[] tokens) {
//        if (tokens == null || tokens.length == 0) {
//            return "Error: Invalid command";
//        }
//
//        boolean reverseOrder = tokens.length > 1 && "-r".equals(tokens[1]);
//        boolean showAll = tokens.length > 1 && "-a".equals(tokens[1]);
//        String directory = DirectoryUtil.getCurrentDirectory();
//        String pattern = "*";
//
//        // Parse directory and pattern from arguments
//        if (tokens.length > 1 && !reverseOrder && !showAll) {
//            if (tokens[1].contains("*")) {
//                pattern = tokens[1];
//            } else {
//                directory = tokens[1];
//            }
//        }
//
//        if (tokens.length > 2) {
//            if (tokens[2].contains("*")) {
//                pattern = tokens[2];
//            } else {
//                directory = tokens[2];
//            }
//        }
//
//        try {
//            if (reverseOrder) {
//                return lsDirectoryReverse(directory, pattern);
//            } else if (showAll) {
//                return lsShowAll(directory, pattern);
//            } else {
//                return lsDirectory(directory, pattern);
//            }
//        } catch (Exception e) {
//            return "Error: " + e.getMessage();
//        }
//    }
//
//    public static String lsDirectory(String dir, String pattern) {
//        StringBuilder output = new StringBuilder();
//        Path path = Paths.get(dir).normalize();
//
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
//            for (Path entry : stream) {
//                if (!Files.isHidden(entry)) {
//                    output.append(entry.getFileName()).append(System.lineSeparator());
//                }
//            }
//        } catch (IOException e) {
//            return "Error: " + e.getMessage();
//        }
//        return output.toString();
//    }
//
//    public static String lsDirectoryReverse(String dir, String pattern) {
//        StringBuilder output = new StringBuilder();
//        File directory = new File(dir);
//        List<String> fileNames = new ArrayList<>();
//
//        if (!directory.exists() || !directory.isDirectory()) {
//            return "Error: '" + dir + "' is not a valid directory.";
//        }
//
//        Pattern regexPattern = Pattern.compile(pattern.replace("*", ".*"));
//        File[] files = directory.listFiles();
//
//        if (files == null) {
//            return "Error: Unable to list directory contents";
//        }
//
//        for (File file : files) {
//            if (!file.isHidden() && regexPattern.matcher(file.getName()).matches()) {
//                fileNames.add(file.getName());
//            }
//        }
//
//        Collections.sort(fileNames, Collections.reverseOrder());
//        for (String name : fileNames) {
//            output.append(name).append(System.lineSeparator());
//        }
//
//        return output.toString();
//    }
//
//    public static String lsShowAll(String dir, String pattern) {
//        StringBuilder output = new StringBuilder();
//        Path path = Paths.get(dir).normalize();
//
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
//            for (Path entry : stream) {
//                output.append(entry.getFileName()).append(System.lineSeparator());
//            }
//        } catch (IOException e) {
//            return "Error: " + e.getMessage();
//        }
//        return output.toString();
//    }
//}
