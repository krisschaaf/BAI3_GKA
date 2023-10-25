package haw.gka.io;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void exampleTest() {
        assertTrue(true);
    }

    @Test
    public void testReadValidFiles(){
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\graphs\\";
        List<String> filesToAccept = new LinkedList<>();
        filesToAccept.add(path+"graph01.grph");
        filesToAccept.add(path+"graph02.grph");
        filesToAccept.add(path+"graph03.grph");
        filesToAccept.add(path+"graph04.grph");
        //graph05 shouldn't work because of syntax error: "::" but no weight specified
        //graph06 shouldn't work because of semantic error: doubled edge "f"
        //graph07 missing
        filesToAccept.add(path+"graph08.grph");
        //graph09 shouldn't work because of syntax error: triple edge a-gb-j
        filesToAccept.add(path+"eulerGraphs\\eulG1.grph");
        filesToAccept.add(path+"eulerGraphs\\eulG2.grph");
        filesToAccept.add(path+"eulerGraphs\\eulG3.grph");

        GraphFileReader reader = new GraphFileReader();
        for (String file : filesToAccept){
            assertDoesNotThrow(() -> reader.getGraphFromFile(file));
        }
    }

    @Test
    public void testReadInvalidFiles(){
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\graphs\\";
        List<String> filesToAccept = new LinkedList<>();
        //syntax error: "::" but no weight specified
        filesToAccept.add(path+"graph05.grph");
        //semantic error: doubled edge "f"
        filesToAccept.add(path+"graph06.grph");
        //syntax error: triple edge a-gb-j
        filesToAccept.add(path+"graph09.grph");
        //syntax error: missing semicolon
        filesToAccept.add(path+"fail01.grph");
        //syntax error: missing "#" in first line
        filesToAccept.add(path+"fail02.grph");
        //syntax error: two weights for one edge
        filesToAccept.add(path+"fail03.grph");
        //syntax error: only one node but with weight
        filesToAccept.add(path+"fail04.grph");
        GraphFileReader reader = new GraphFileReader();
        for (String file : filesToAccept){
            try{
                assertThrows(Exception.class,() -> reader.getGraphFromFile(file));
            } catch (Exception e){
                System.out.println(e.toString());
            }

        }
    }
}
