package org.os;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MkdirCommand {

    public static void execute(String... dirNames) {
        for (String dirName : dirNames) {
        Path path = Paths.get(dirName);
        File file = path.toFile();


        if (file.exists()) {
            System.out.println("mkdir: cannot create directory '" + dirName + "': File exists");
            return;
        }

        try {
            Files.createDirectory(path);
        } catch (IOException e) {
            System.out.println("mkdir: cannot create directory '" + dirName + "': " + e.getMessage());
        }

    }
    }
}
