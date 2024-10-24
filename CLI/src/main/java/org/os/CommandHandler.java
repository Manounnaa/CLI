package org.os;

public class CommandHandler {

    public void executeCommand(String input) {
        // Check for piping
        if (input.contains("|")) {
            PipeHandler.handlePipe(input);
            return;  // Exit early to avoid further processing
        }

        // Check for redirection
        if (input.contains(">") || input.contains(">>")) {
            RedirectionHandler.handleRedirection(input);
            return;  // Exit early to avoid further processing
        }

        String[] tokens = input.split("\\s+");
        String command = tokens[0];

        switch (command) {
            case "pwd":
                PwdCommand.execute();
                break;

            case "mkdir":
                if (tokens.length > 1) {
                    MkdirCommand.execute(tokens[1]);
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
                LsCommand.execute(tokens);
                break;

            case "mv":
                if (tokens.length > 2) {
                    MvCommand.execute(tokens[1], tokens[2]);
                } else {
                    System.out.println("mv: missing operand");
                }
                break;

            case "rm":
                if (tokens.length > 1) {
                    RmCommand.execute(tokens[1]);
                } else {
                    System.out.println("rm: missing operand");
                }
                break;

            case "touch":
                if (tokens.length > 1) {
                    TouchCommand.execute(tokens[1]);
                } else {
                    System.out.println("touch: missing operand");
                }
                break;

            case "cat":
                if (tokens.length > 1) {
                    CatCommand.execute(tokens[1]);
                } else {
                    System.out.println("cat: missing operand");
                }
                break;

            default:
                System.out.println("Unknown command: " + command);
        }
    }
}
