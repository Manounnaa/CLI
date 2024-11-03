package org.os;

public class CommandHandler {

    public void executeCommand(String input) {
        // Check for piping
        if (input.contains("|")) {
            PipeHandler.handlePipe(input);
            return;  // Exit early to avoid further processing
        }
        // Check for redirection
        boolean appendRedirect = false;
        boolean overwriteRedirect = false;
        String targetFile = null;

        // Check for redirection using >> or >
        if (input.contains(">>")) {
            String[] parts = input.split(">>");
            input = parts[0].trim(); // command part
            targetFile = parts[1].trim();
            appendRedirect = true;
        } else if (input.contains(">")) {
            String[] parts = input.split(">");
            input = parts[0].trim();
            targetFile = parts[1].trim();
            overwriteRedirect = true;
        }
        // Check for redirection

        String[] tokens = input.split("\\s+");
        String command = tokens[0];

        switch (command) {
            case "echo":
                String output = EchoCommand.execute(tokens);

                if (targetFile == null) {
                    System.out.println(output); // No redirection, print to console
                } else {
                    try {
                        if (appendRedirect) {
                            AppendCommand.execute(targetFile, output);
                        } else if (overwriteRedirect) {
                            OverwriteCommand.execute(targetFile, output);
                        }
                    } catch (Exception e) {
                        System.out.println("Error writing to file '" + targetFile + "': " + e.getMessage());
                    }
                }
                break;
            case "pwd":
                PwdCommand.execute();
                break;

            case "mkdir":
                if (tokens.length > 1) {
                    String[] dirNames = new String[tokens.length - 1];
                    System.arraycopy(tokens, 1, dirNames, 0, tokens.length - 1);
                    MkdirCommand.execute(dirNames);
                } else {
                    System.out.println("mkdir: missing operand");
                }
                break;

            case "rmdir":
                if (tokens.length > 1) {
                    RmdirCommand.execute(tokens[1]);
                } else {
                    System.out.println("rmdir: missing operand");
                }
                break;
                case "ls":
                     output = LsCommand.execute(tokens);
                    if (appendRedirect) {
                        AppendCommand.execute(targetFile, output);
                    } else if (overwriteRedirect) {
                        OverwriteCommand.execute(targetFile, output);
                    } else {
                        System.out.println(output);
                    }
                    break;
            case "mv":
                if (tokens.length > 2) {
                    MvCommand.execute(tokens);
                } else {
                    System.out.println("mv: missing operand");
                }
                break;

            case "rm":
                if (tokens.length > 1) {
                    String[] fileNames = new String[tokens.length - 1];
                    System.arraycopy(tokens, 1, fileNames, 0, tokens.length - 1); // Get all file names after "rm"
                    RmCommand.execute(fileNames); // Pass all file names to RmCommand
                } else {
                    System.out.println("rm: missing operand");
                }
                break;
            case "touch":
                if (tokens.length > 1) {
                    String[] fileNames = new String[tokens.length - 1];
                    System.arraycopy(tokens, 1, fileNames, 0, tokens.length - 1); // Get all file names
                    TouchCommand.execute(fileNames); // Pass all file names
                } else {
                    System.out.println("touch: missing operand");
                }
                break;
            case "cat":
                 output = CatCommand.execute(tokens);
                // Handle redirection
                if (appendRedirect) {
                    AppendCommand.execute(targetFile, output);
                } else if (overwriteRedirect) {
                    OverwriteCommand.execute(targetFile, output);
                } else {
                    System.out.print(output);
                }
                break;
            case "cd":
                if (tokens.length > 1) {
                    CDCommand.execute(tokens[1]);
                } else if (tokens[0].length()==1&&tokens[0].equals("cd")) {
                    CDCommand.execute("");

                }
                break;
            case "help":
                HelpCommand.execute();
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }
}
