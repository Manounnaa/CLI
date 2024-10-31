package org.os;
import java.io.File;
import java.io.IOException;
public class TouchCommand {

    /**
     *  creating new files
     * @param fileNames multiple file names
     */
    public static void execute(String... fileNames) { // ... >>allows multiple file names to be passed
        String currentDir = DirectoryUtil.getCurrentDirectory();

        for (String fileName : fileNames) { // iterate over each file name p
            File file = new File(currentDir, fileName); // Create file in the current directory
            try {
                file.createNewFile(); //create newone
            } catch (IOException e) {
                //handling any exceptions exist during file creation
                System.out.println("Unable to create file '" + fileName + "': " + e.getMessage());
            }
        }
    }
}
