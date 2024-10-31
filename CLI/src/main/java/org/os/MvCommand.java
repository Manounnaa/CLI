package org.os;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class MvCommand {
    public static String execute(String[] args) {
        if (args.length < 3) {
            return "Error: Usage: mv <source(s)> <destination>";
        }

        StringBuilder result = new StringBuilder();
        String[] sources = Arrays.copyOfRange(args, 1, args.length - 1);
        String target = args[args.length - 1];

        // Convert target to absolute path
        Path destination = Paths.get(target).toAbsolutePath();

        try {
            if (sources.length > 1) {
                // Multiple source files - destination must be a directory
                if (!Files.isDirectory(destination)) {
                    return "Error: Target must be a directory when moving multiple files";
                }

                for (String source : sources) {
                    Path sourcePath = Paths.get(source).toAbsolutePath();
                    if (!Files.exists(sourcePath)) {
                        result.append("Error: Source file not found: ").append(source).append("\n");
                        continue;
                    }
                    Path targetPath = destination.resolve(sourcePath.getFileName());
                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                // Single source file
                Path sourcePath = Paths.get(sources[0]).toAbsolutePath();
                if (!Files.exists(sourcePath)) {
                    return "Error: Source file not found: " + sources[0];
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
            return "Error: " + e.getMessage();
        }

        return result.length() > 0 ? result.toString().trim() : "";
    }
}
