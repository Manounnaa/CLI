package org.os;

import java.io.File;

public class CDCommand {

    public static void execute(String dirName) {
        String newDirPath;

        if (dirName.equals("..")) {
            File currentDirectory = new File(LsCommand.getCurrentDirectory());
            File parentDirectory = currentDirectory.getParentFile();

            if (parentDirectory != null) {
                newDirPath = parentDirectory.getAbsolutePath();
                File newDir = new File(newDirPath);
                System.setProperty("user.dir", newDir.getAbsolutePath());
            } else {
                System.out.println("cd: No parent directory");
                return;
            }
        } else {
            File newDir = new File(dirName);

            if (!newDir.exists()) {
                System.out.println("cd: " + dirName + ": No such file or directory");
                return;
            }
            if (!newDir.isDirectory()) {
                System.out.println("cd: not a directory: " + dirName);
                return;
            }

            newDirPath = newDir.getAbsolutePath();
            System.setProperty("user.dir", newDir.getAbsolutePath());
        }

        // Update LsCommand’s current directory
        LsCommand.setCurrentDirectory(newDirPath);
    }
}
