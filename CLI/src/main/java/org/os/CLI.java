package org.os;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandHandler commandHandler = new CommandHandler();

        String currentDir = System.getProperty("user.dir");


        while (true) {
            System.out.print("myCLI" + currentDir.replace("\\", "/") + "> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the CLI...");
                break;
            }

            // Pass input to CommandHandler
            commandHandler.executeCommand(input);

            currentDir = System.getProperty("user.dir");
        }

        scanner.close();
    }
}
