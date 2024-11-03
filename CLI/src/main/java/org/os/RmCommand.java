package org.os;

import java.io.File;

public class RmCommand {

    /**
     * Executes the rm command to delete one or more specified files.
     * If a specified file is a directory, an error message is shown.
     *
     * @param fileNames The names of the files to be deleted
     */
    public static void execute(String[] fileNames) {
        String currentDir = DirectoryUtil.getCurrentDirectory(); // Get the current directory

        for (String fileName : fileNames) {
            if ("rm".equals(fileName)) {
                continue;
            }
            File file = new File(currentDir, fileName);

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
}
