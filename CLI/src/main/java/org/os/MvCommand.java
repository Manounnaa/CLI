package org.os;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MvCommand {

    public static void execute(String[] args) {

        String[] sources = Arrays.copyOfRange(args, 0, args.length - 1);
        String target = args[args.length - 1];

        Path destination = Paths.get(target);

//        List<Path> expandedSources = new ArrayList<>(); //

        try{
            if(sources.length > 1){
                if (!Files.isDirectory(destination)) {
                    System.err.println("Destination must be an existing directory");
                    return;
                }
                for (String source : sources) {
                    Path src = Paths.get(source);
                    Path dest = destination.resolve(src.getFileName());
                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            else{
                Path src = Paths.get(sources[0]);
//                if destination is a directory then we are moving
                if(Files.isDirectory(destination)){
                    Path dest = destination.resolve(src.getFileName());
                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                }
                else{
//                    if dest is a file then we are renaming
                    Files.move(src, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch(IOException e){
            System.err.println("Error: " + e.getMessage());
        }



//        int lastSlash = source.lastIndexOf('/');
//        String sourceFileName = lastSlash >= -1 ? source.substring(lastSlash + 1) : source ;
//        String targetFileName = lastSlash >= -1 ? target.substring(lastSlash + 1) : target ;
//        String sourcePath = lastSlash >= -1 ? source.substring(0, lastSlash) : source ;
//        String targetPath = lastSlash >= -1 ? target.substring(0, lastSlash) : target ;
//        if (sourceFileName == null || targetFileName == null) {
//            System.out.println("Error: source file or target file is null!");
//            return;
//        }
//        if (sourceFileName != targetFileName) {
//            //rename
//            return;
//        }
//        if (sourceFileName == targetFileName && source != target) {
//            //move
//        }
//        if (sourceFileName!=targetFileName && sourcePath != targetPath){
//            //move adn rename
//        }

    }
}
//wildcard remaining
