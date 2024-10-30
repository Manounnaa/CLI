package main.java.org.os;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class PipeHandler {
    private static final Map<String, String> COMMAND_TYPES = new HashMap<>();
    static {
        COMMAND_TYPES.put("ls", "output");
        COMMAND_TYPES.put("cat", "output");
//        COMMAND_TYPES.put("grep", "input");
        COMMAND_TYPES.put("sort", "input/output");
//        COMMAND_TYPES.put("echo", "output");
        // Add other commands and their types as needed
    }
    public static String captureOutput(Runnable command){
        PrintStream out = System.out; //stores the system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //redirecting the system output
        System.setOut(new PrintStream(outputStream));
        command.run();
        System.setOut(out);
        return outputStream.toString();
    }

    public static void handlePipe(String input) {
        String[] pipeParts = input.split("\\|");
        int i = 0;
        String output = "", in = "";
        for(String pipePart : pipeParts){
            if(!COMMAND_TYPES.containsKey(pipePart)){
                System.out.println("Unknown command: " + pipePart);
                return;
            }
            if(COMMAND_TYPES.get(pipePart).equals("input") && i == 0 || COMMAND_TYPES.get(pipePart).equals("input/output") && i == 0){
                System.out.println("There is no input");
                return;
            }
            String[] command = pipePart.split(" ");
            if(command[0].equals("ls")){
                if(i == pipeParts.length - 1){
                    LsCommand.execute(command);
                }
                else{
                    output = captureOutput(() -> LsCommand.execute(command));
                }
            }
            else if(command[0] == "cat"){
//                org.os.CatCommand.execute(command[1]);
                if(i == pipeParts.length - 1){
                    org.os.CatCommand.execute(command[1]);
                }
                else{
                    output = captureOutput(() -> org.os.CatCommand.execute(command[1]));
                    in = captureOutput(() -> org.os.CatCommand.execute(command[1]));
                }
            }
            else if(command[0] == "sort"){
                if(i == pipeParts.length - 1){
                    LsCommand.execute(command);
                }
                else{
                    String finalIn = in;
                    output = captureOutput(() -> SortNameCommand.execute(finalIn));
                    in = captureOutput(() -> SortNameCommand.execute(finalIn));
                }
            }
            i++;
        }
    }
}
