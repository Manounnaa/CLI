package org.os;

import java.nio.file.Paths;

public class PwdCommand {

    public static void execute() {
        System.out.println(getCurrentDirectory());
    }

    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }
}