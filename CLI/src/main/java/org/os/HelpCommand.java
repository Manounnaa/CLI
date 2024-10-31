package org.os;

public class HelpCommand {

    public static void execute() {
        System.out.println("Available commands:\n" +
                "mkdir <directory> - Create a new directory\n" +
                "cd <directory> - Change directory\n" +
                "mv <source> <destination> - Move or rename files\n" +
                "pwd - Print working directory\n" +
                "rmdir <directory> - Remove a directory\n" +
                "rm <file> - Delete a file\n" +
                "ls - List directory contents\n" +
                "ls -a - List all contents in the directory, including hidden files\n" +
                "ls -r - List directory contents recursively\n" +
                "> - Redirect output to a file\n" +
                ">> - Append output to a file\n" +
                "touch <file> - Create a new file or update the timestamp of an existing file\n" +
                "cat <file> - Display the contents of a file\n" +
                "| - Pipe output from one command to another\n" +
                "help - Display available commands\n" +
                "exit - Exit program");
    }
}
