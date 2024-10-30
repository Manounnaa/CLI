package org.os;
import java.io.File;
import java.io.IOException;
public class TouchCommand {

    /**
     *  creating new files
     * @param fileNames multiple file names
     */
    public static void execute(String... fileNames) { // ... >>allows multiple file names to be passed
        // iterate over each file name p
        for (String fileName : fileNames) {
            File file = new File(fileName); // create file with entered name
            try {
                //create newone
                if (file.createNewFile()) {
                    // if it created successfully >>print confirmation mess
                    System.out.println("TouchCommand: New file created -> " + fileName);
                } else {
                    // if it exists >>inform the user
                    System.out.println("TouchCommand: File already exists -> " + fileName);
                }
            } catch (IOException e) {
                //handling any exceptions exist during file creation
                System.out.println("TouchCommand error: Unable to create file '" + fileName + "': " + e.getMessage());
            }
        }
    }
}
