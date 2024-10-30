package org.os;

import java.io.FileWriter;
import java.io.IOException;

    public class OverwriteCommand {
        /**
         * overwriteing content of file
         * @param fileName files'sname to write to
         * @param content to write into file
         */
        public static void execute(String fileName, String content) {
            //to ensure filewriter is closed automatically
            try (FileWriter writer = new FileWriter(fileName, false)) { // 'false' to overwrite file
                writer.write(content);
                System.out.println( fileName); //confirmation message
            } catch (IOException e) { //catching exceptions may occur
                System.out.println("Unable to write to file '" + fileName + "': " + e.getMessage());
            }
        }
    }

