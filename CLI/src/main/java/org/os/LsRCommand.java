package org.os;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LsRCommand {
    /**
     * listing all files in  dir in reverse order
     * @param directoryPath dir'spath' to list files from
     * @return reversed filesname
     */
    public static List<String> execute(String directoryPath) {
        File directory = new File(directoryPath); // obj file creation for specified directory
        List<String> fileNames = new ArrayList<>(); // initialization for holding file names
        // check if dir exists &valid
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("LsRCommand error: '" + directoryPath + "' is not a valid directory ");
            return fileNames; // retur empty list
        }
        // looping through all files in dir
        for (File file : directory.listFiles()) {
            // adding names to list
            fileNames.add(file.getName());
        }
        // sorting by reverse order
        Collections.sort(fileNames, Collections.reverseOrder());
        fileNames.forEach(fileName -> System.out.println("File: " + fileName));
        return fileNames; // return file names
    }
}
