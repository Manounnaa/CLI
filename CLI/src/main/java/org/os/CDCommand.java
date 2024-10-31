package org.os;

import java.io.File;
import java.io.IOException;

public class CDCommand {

    public static void execute(String dirName) {
        String newDirPath;

        switch (dirName) {
            case "/":
                newDirPath = "/";
                break;

            case "~":
                newDirPath = System.getProperty("user.home");
                break;

            case "":
                newDirPath = "/";
                break;

            case "..":
                File currentDirectory = new File(DirectoryUtil.getCurrentDirectory());
                File parentDirectory = currentDirectory.getParentFile();
                if (parentDirectory != null) {
                    newDirPath = parentDirectory.getAbsolutePath();
                } else {
                    System.out.println("cd: No parent directory");
                    return;
                }
                break;

            default:
                newDirPath = handlePath(dirName);
                if (newDirPath == null) {
                    return;
                }
                break;
        }

        DirectoryUtil.setCurrentDirectory(newDirPath);
    }
    private static String handlePath(String path) {
        File newDir = new File(path);

        if (!newDir.isAbsolute()) {
            newDir = new File(DirectoryUtil.getCurrentDirectory(), path);
        }

        if (!newDir.exists()) {
            System.out.println("cd: " + path + ": No such file or directory");
            return null;
        }
        if (!newDir.isDirectory()) {
            System.out.println("cd: not a directory: " + path);
            return null;
        }

        try {
            return newDir.getCanonicalPath(); // Use getCanonicalPath to remove ./ or ../
        } catch (IOException e) {
            System.out.println("cd: error resolving path: " + e.getMessage());
            return null;
        }
    }

}
