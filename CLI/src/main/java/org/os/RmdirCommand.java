package org.os;

import java.io.File;

public class RmdirCommand {

    public static void execute(String dirName) {
        File dir = new File(dirName);
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