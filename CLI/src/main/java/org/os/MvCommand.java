package org.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class MvCommand {
    public static void execute(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: mv <source(s)> <destination>");
            return;
        }

        String[] sources = Arrays.copyOfRange(args, 1, args.length - 1);
        String target = args[args.length - 1];

        // Convert target to absolute path
        Path destination = Paths.get(target).toAbsolutePath();

        try {
            if (sources.length > 1) {
                // Multiple source files - destination must be a directory
                if (!Files.isDirectory(destination)) {
                    System.err.println("Target must be a directory when moving multiple files");
                    return;
                }

                for (String source : sources) {
                    Path sourcePath = Paths.get(source).toAbsolutePath();
                    if (!Files.exists(sourcePath)) {
                        System.err.println("Source file not found: " + source);
                        continue;
                    }
                    Path targetPath = destination.resolve(sourcePath.getFileName());
                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                // Single source file
                Path sourcePath = Paths.get(sources[0]).toAbsolutePath();
                if (!Files.exists(sourcePath)) {
                    System.err.println("Source file not found: " + sources[0]);
                    return;
                }

                if (Files.isDirectory(destination)) {
                    // Moving to directory - preserve filename
                    Path targetPath = destination.resolve(sourcePath.getFileName());
                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    // Renaming or moving with new name
                    Files.move(sourcePath, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}