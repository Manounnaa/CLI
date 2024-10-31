package org.os;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.os.CatCommand;

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
    public static String[] captureOutput(Runnable command){
        PrintStream out = System.out; //stores the system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //redirecting the system output
        System.setOut(new PrintStream(outputStream));
        command.run();
        System.setOut(out);
        String output = outputStream.toString();
        return output.split("\\R");
    }

    public static void handlePipe(String input) {
        String[] pipeParts = input.split("\\|");
        int i = 0;
        String[] output = new String[0], in = new String[0];
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
                    //if its the last command we dont need to capture its output
                    LsCommand.execute(command);
                }
                else{
                    output = captureOutput(() -> LsCommand.execute(command));
                }
            }
            else if(command[0].equals("cat")){
//                org.os.CatCommand.execute(command[1]);
                if(i == pipeParts.length - 1){
                    //if last command, no need to store output
                    CatCommand.execute(command);
                }
                else{
                    //if not the last command, we will store the output
                    if(i>0){
                        output = captureOutput(() -> CatCommand.execute(command));
                    }
                    else{
                        output = captureOutput(() -> CatCommand.execute(command));
                    }
//                    in = captureOutput(() -> CatCommand.execute(command));
                }
            }
            else if(command[0].equals("sort")){
                if(i == pipeParts.length - 1){
                    SortNameCommand.execute(output);

                }
                else{
//                    String finalIn = in;
                    String[] finalOutput = output;
                    output = captureOutput(() -> SortNameCommand.execute(finalOutput));
//                    in = captureOutput(() -> SortNameCommand.execute(finalIn));
                }
            }
            i++;
        }
    }
}