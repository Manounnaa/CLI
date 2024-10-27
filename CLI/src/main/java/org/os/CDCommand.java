package org.os;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CDCommand {
    public static void execute(String dirName) {
        if (dirName.equals("..")) {
            String currentDir = System.getProperty("user.dir");
            File currentDirectory = new File(currentDir);
            File parentDirectory = currentDirectory.getParentFile();
            if (parentDirectory != null) {
                System.setProperty("user.dir", parentDirectory.getAbsolutePath());
            } else {
                System.out.println("cd: No parent directory");
            }
            return;
        }
        File newDir = new File(dirName);
        if (!newDir.exists()) {
            System.out.println("cd: " + dirName + ": No such file or directory");
            return;
        }
        if (!newDir.isDirectory()) {
            System.out.println("cd: not a directory: " + dirName);
            return;
        }
        System.setProperty("user.dir", newDir.getAbsolutePath());
    }

}