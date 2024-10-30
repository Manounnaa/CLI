package org.os;

public class RedirectionHandler {

    public static void handleRedirection(String input) {
        if (input.contains(">>")) {
            String[] parts = input.split(">>", 2);
            String content = parts[0].trim();
            String fileName = parts[1].trim();
            AppendCommand.execute(fileName, content);
        } else if (input.contains(">")) {
            String[] parts = input.split(">", 2);
            String content = parts[0].trim();
            String fileName = parts[1].trim();
            OverwriteCommand.execute(fileName, content);
        }
    }
}
