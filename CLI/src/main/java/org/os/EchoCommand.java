package org.os;

public class EchoCommand {
    public static String execute(String[] tokens) {
        StringBuilder message = new StringBuilder();

        for (int i = 1; i < tokens.length; i++) {
            message.append(tokens[i]).append(" ");
        }

        return message.toString().trim();
    }
}
