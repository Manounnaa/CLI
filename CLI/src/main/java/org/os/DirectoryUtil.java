package org.os;

public class DirectoryUtil {

    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    public static void setCurrentDirectory(String path) {
        System.setProperty("user.dir", path);
    }
}
