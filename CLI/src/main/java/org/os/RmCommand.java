package org.os;

import java.io.File;

public class RmCommand {

    /**
     * Executes the rm command to delete a specified file or directory.
     * If the specified file is a directory, an error message is shown.
     *
     * @param fileName The name of the file to be deleted
     */
    public static void execute(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("rm: cannot remove '" + fileName + "': No such file or directory");
        } else if (file.isDirectory()) {
            System.out.println("rm: cannot remove '" + fileName + "': Is a directory");
        } else if (file.delete()) {
            System.out.println("File '" + fileName + "' deleted");
        } else {
            System.out.println("rm: failed to delete '" + fileName + "'");
        }
    }
}
