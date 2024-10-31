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
            String currentDir = DirectoryUtil.getCurrentDirectory(); // Get the current directory
            FileWriter writer = null;

            try {
                writer = new FileWriter(currentDir + "/" + fileName, false);
                writer.write(content);
            } catch (IOException e) {
                System.out.println("Unable to write to file '" + fileName + "': " + e.getMessage());
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        System.out.println(">>: error closing file writer: " + e.getMessage());
                    }
                }
            }
        }
    }

