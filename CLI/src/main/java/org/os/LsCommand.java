package main.java.org.os;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LsCommand {

    public static void execute(String[] tokens) {
        if(tokens.length == 1){
            lsDirectory(".", "*");
        }
        else{
            // If tokens[1] has a wildcard, pass it as the pattern
            String pattern = tokens[1].contains("*") ? tokens[1] : "*";
            String directory = tokens[1].contains("*") ? "." : tokens[1];
            lsDirectory(directory, pattern);
        }
    }

    public static void lsDirectory(String dir, String pattern) {
        Path path = Paths.get(dir);
        try {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, pattern)) {
                for (Path entry : stream) {
                    System.out.println(entry.getFileName());
                }
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}



//list of things to handle
//ls
//ls path
//ls path1 path2 path3....
//ls filename
//ls ../  ...relative path
//ls *extension  ....wild card
//ls path/*extension