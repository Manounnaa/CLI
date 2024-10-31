package org.os;

import java.io.File;

public class RmdirCommand {

    public static void execute(String dirName) {
        String currentDir = DirectoryUtil.getCurrentDirectory();
        File dir = new File(currentDir, dirName);
          if (dir.exists() && dir.isDirectory()) {
            if (dir.list().length == 0) {
                dir.delete();
            } else {
                System.out.println("rmdir: directory not empty: " + dirName);
            }
        } else {
            System.out.println("rmdir: no such directory: " + dirName);
        }
    }
}