package org.os;

public class EchoCommand {
    public static void execute(String[] tokens) {
        StringBuilder message = new StringBuilder();
        String targetFile = null;
        boolean redirect = false;

        if (tokens.length > 1) {
            for (int i = 1; i < tokens.length; i++) {
                if ((tokens[i].equals(">"))||(tokens[i].equals(">>"))) {
                    redirect = true;
                    targetFile = tokens[i + 1];
                    break;
                }
                 else {
                    if (i < tokens.length) {
                        message.append(tokens[i]).append(" ");
                    }
                }
            }
        }


        if (!redirect) {
            System.out.println(message.toString().trim());
        } else if (targetFile != null) {
            // Write the message to the target file
            try {
                if (tokens[tokens.length - 2].equals(">")) {
                    OverwriteCommand.execute(targetFile, message.toString().trim());
                } else {
                    AppendCommand.execute(targetFile, message.toString().trim());
                }
            } catch (Exception e) {
                System.out.println("Error writing to file '" + targetFile + "': " + e.getMessage());
            }
        }
    }
}
