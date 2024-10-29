package org.os;

import java.io.File;
import java.io.IOException;

public class TouchCommand {

    public static void execute(String fileName) {

        File file = new File(fileName);
        try {
             file.createNewFile(); // Returns true if created, false if already exists
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());


    }
}}
