package org.os;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MvCommand {

    public static void execute(String[] args) {
        if (args.length < 3) {
            System.err.println("Error: Usage: mv <source(s)> <destination>");
            return;
        }

        String[] sources = Arrays.copyOfRange(args, 1, args.length - 1);
        String target = args[args.length - 1];

        Path currentDir = Paths.get(DirectoryUtil.getCurrentDirectory()).normalize(); // Get the current directory
        Path destination = currentDir.resolve(target).normalize();

        // Collect paths matching wildcard patterns
        List<Path> expandedSources = new ArrayList<>();
        for (String source : sources) {
            try {
                if (source.contains("*")) {
                    // Use DirectoryStream to match wildcard patterns
                    try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDir, source)) {
                        for (Path entry : stream) {
                            expandedSources.add(entry);
                        }
                    }
                } else {
                    Path src = currentDir.resolve(source).normalize();
                    if (Files.exists(src)) {
                        expandedSources.add(src);
                    } else {
                        System.err.println("Source file does not exist: " + source);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error processing source: " + source + " - " + e.getMessage());
            }
        }

        try {
            if (expandedSources.size() > 1) {
                if (!Files.isDirectory(destination)) {
                    System.err.println("Error: Destination must be an existing directory when moving multiple files.");
                    return;
                }
                for (Path src : expandedSources) {
                    Path dest = destination.resolve(src.getFileName());
                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                }
            } else if (expandedSources.size() == 1) {
                Path src = expandedSources.get(0);
                if (Files.isDirectory(destination)) {
                    Path dest = destination.resolve(src.getFileName());
                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Files.move(src, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}


//----------------------------------------
//package org.os;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class MvCommand {
//
//    public static void execute(String[] args) {
//        if (args.length < 3) {
//            System.err.println("Error: Usage: mv <source(s)> <destination>");
//            return ;
//        }
//
//        String[] sources = Arrays.copyOfRange(args, 1, args.length - 1);
//        String target = args[args.length - 1];
//
//        Path currentDir = Paths.get(DirectoryUtil.getCurrentDirectory()).normalize(); // Get the current directory
//        Path destination = currentDir.resolve(target).normalize();
//
//
////        List<Path> expandedSources = new ArrayList<>(); //
//
//
//        try{
//            if(sources.length > 1){
//                if (!Files.isDirectory(destination)) {
//                    System.err.println("Destination must be an existing directory");
//                    return;
//                }
//                for (String source : sources) {
//                    Path src = currentDir.resolve(source).normalize();
//                    if (!Files.exists(src)) {
//                        System.err.println("Source file does not exist: " + source);
//                        continue;
//                    }
//                    Path dest = destination.resolve(src.getFileName());
//                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
//                }
//            }
//            else{
//                Path src = currentDir.resolve(sources[0]).normalize();
//                if (!Files.exists(src)) {
//                    System.err.println("Source file does not exist: " + sources[0]);
//                    return;
//                }
////                if destination is a directory then we are moving
//                if (Files.isDirectory(destination)) {
//                    // Moving to directory - preserve filename
//                    Path dest = destination.resolve(src.getFileName());
//                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
//                } else {
//                    // Renaming or moving to a new location with a new name
//                    Files.move(src, destination, StandardCopyOption.REPLACE_EXISTING);
//                }
//            }
//        }
//        catch(IOException e){
//            System.err.println("Error: " + e.getMessage());
//        }
//
//
//
//
//    }
//}


//--------------------------------
//wildcard remaining


//        int lastSlash = source.lastIndexOf('/');
//        String sourceFileName = lastSlash >= -1 ? source.substring(lastSlash + 1) : source ;
//        String targetFileName = lastSlash >= -1 ? target.substring(lastSlash + 1) : target ;
//        String sourcePath = lastSlash >= -1 ? source.substring(0, lastSlash) : source ;
//        String targetPath = lastSlash >= -1 ? target.substring(0, lastSlash) : target ;
//        if (sourceFileName == null || targetFileName == null) {
//            System.out.println("Error: source file or target file is null!");
//            return;
//        }
//        if (sourceFileName != targetFileName) {
//            //rename
//            return;
//        }
//        if (sourceFileName == targetFileName && source != target) {
//            //move
//        }
//        if (sourceFileName!=targetFileName && sourcePath != targetPath){
//            //move adn rename
//        }



//package org.os;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.Arrays;
//
//public class MvCommand {
//    public static String execute(String[] args) {
//        if (args.length < 3) {
//            return "Error: Usage: mv <source(s)> <destination>";
//        }
//
//        StringBuilder result = new StringBuilder();
//        String[] sources = Arrays.copyOfRange(args, 1, args.length - 1);
//        String target = args[args.length - 1];
//
//        // Convert target to absolute path
////        Path destination = Paths.get(target).toAbsolutePath();
////        Path destination = Paths.get(target).toAbsolutePath();
//
//        // Resolve destination relative to the current directory
//        Path currentDir = Paths.get(System.getProperty("user.dir"));
//        Path destination = currentDir.resolve(target).normalize();
//
//        try {
//            if (sources.length > 1) {
//                // Multiple source files - destination must be a directory
//                if (!Files.isDirectory(destination)) {
//                    return "Error: Target must be a directory when moving multiple files";
//                }
//
//                for (String source : sources) {
//                    Path sourcePath = Paths.get(source).toAbsolutePath();
//                    if (!Files.exists(sourcePath)) {
//                        result.append("Error: Source file not found: ").append(source).append("\n");
//                        continue;
//                    }
//                    Path targetPath = destination.resolve(sourcePath.getFileName());
//                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
//                }
//            } else {
//                // Single source file
//                Path sourcePath = Paths.get(sources[0]).toAbsolutePath();
//                if (!Files.exists(sourcePath)) {
//                    return "Error: Source file not found: " + sources[0];
//                }
//
//                if (Files.isDirectory(destination)) {
//                    // Moving to directory - preserve filename
//                    Path targetPath = destination.resolve(sourcePath.getFileName());
//                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
//                } else {
//                    // Renaming or moving with new name
//                    Files.move(sourcePath, destination, StandardCopyOption.REPLACE_EXISTING);
//                }
//            }
//        } catch (IOException e) {
//            return "Error: " + e.getMessage();
//        }
//
//        return result.length() > 0 ? result.toString().trim() : "";
//    }
//}
