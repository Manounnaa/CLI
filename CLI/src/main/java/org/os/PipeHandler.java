package org.os;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class PipeHandler {
    private static final Map<String, String> COMMAND_TYPES = new HashMap<>();
    //type of command
    static {
        COMMAND_TYPES.put("ls", "output");
        COMMAND_TYPES.put("cat", "output");
        COMMAND_TYPES.put("sort", "input/output");
    }
    //captures output to use as input for other commands
    //returns an array of strings (lines)
    public static String[] captureOutput(CommandExecutor command) {

        PrintStream originalOut = System.out;
        //captures console outputand stores it in the memory instead of displaying it
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //changing the output stream from console to the outputStream above
        PrintStream newOut = new PrintStream(outputStream);
        System.setOut(newOut);
        String result = command.execute();
        System.setOut(originalOut);
        //split in line breaks, new lines.
        return result != null ? result.split("\\R") : new String[0];
    }

    @FunctionalInterface
    interface CommandExecutor {
        String execute();
    }

    public static String handlePipe(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Empty command");
            return "Empty command";
        }
        //split the pipe command into parts
        String[] pipeParts = input.split("\\|");
        String[] currentOutput = new String[0];

        for (int i = 0; i < pipeParts.length; i++) {
            String[] commandParts = pipeParts[i].trim().split("\\s+");
            String command = commandParts[0];

            if (!COMMAND_TYPES.containsKey(command)) {
                System.out.println("Unknown command: " + command);
                return "Unknown command: " + command;
            }
            //check if this is the last commnd
            boolean isLastCommand = i == pipeParts.length - 1;

            try {
                switch (command) {
                    case "ls":
                        if (isLastCommand) {
                            String result = LsCommand.execute(commandParts);
                            System.out.println(result);
                        } else {
                        //if not last command then we will capture the output
                            currentOutput = captureOutput(() -> LsCommand.execute(commandParts));
                        }
                        break;

                    case "cat":
                        if (isLastCommand) {
                            String result = CatCommand.execute(commandParts);
                            System.out.println(result);
                        } else {
                            currentOutput = captureOutput(() -> CatCommand.execute(commandParts));
                        }
                        break;

                    case "sort":
                        //if first command
                        if (currentOutput.length == 0 && i == 0) {
                            String result = SortNameCommand.execute(commandParts);
                            System.out.println(result);
                        } else if (isLastCommand) {
                            // Extract sort arguments (skip the 'sort' command itself)
                            String[] sortArgs = new String[commandParts.length - 1];
                            //takes n elements starting at pos 1 from command parts and copies it into sortArgs starting pos 1
                            System.arraycopy(commandParts, 1, sortArgs, 0, commandParts.length - 1);

                            String[] sortedOutput = SortNameCommand.execute(currentOutput, sortArgs);
                            for (String line : sortedOutput) {
                                System.out.println(line);
                            }
                        } else {
                            String[] sortArgs = new String[commandParts.length - 1];
                            System.arraycopy(commandParts, 1, sortArgs, 0, commandParts.length - 1);
                            currentOutput = SortNameCommand.execute(currentOutput, sortArgs);
                        }
                        break;

                    default:
                        System.out.println("Unsupported command: " + command);
                        return "Unsupported command: " + command;
                }
            } catch (Exception e) {
                System.err.println("Error executing command '" + command + "': " + e.getMessage());
                return "Error executing command '" + command + "': " + e.getMessage();
            }
        }
        return "";
    }
}
