package org.os;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandHandler commandHandler = new CommandHandler();

        while (true) {
            System.out.print("myCLI> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the CLI...");
                break;
            }

            // Pass input to CommandHandler
            commandHandler.executeCommand(input);
        }

        scanner.close();
    }
}
