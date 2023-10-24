package kris.schaaf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GrphFileReader {

    public static List<String> readFile(String path) {
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(path));

            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isDirected(List<String> fileContent) {
        String firstLine = fileContent.get(0);

        return firstLine.contains("#directed");
    }

    public static boolean isUndirectedAndContainsLength(List<String> fileContent) {
        if(isDirected(fileContent)) {
            throw new RuntimeException("FileContent of Graph is directed!");
        } else {
            for(int i = 1; i < fileContent.size(); i++) {
                if(!fileContent.get(i).contains("::")) {
                    return false;
                }
            }
        }
        return true;
    }
}