package haw.gka.io;

import haw.gka.exceptions.UnoperableGraphException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private static final String OS = System.getProperty("os.name");
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String PATH = OS.contains("windows")
            ? USER_DIR + "\\src\\main\\resources\\graphs\\"
            : USER_DIR + "/src/main/resources/graphs/";
    private static final String PATH_EULER = PATH + "/eulerGraphs/";

    @Test
    public void testReadValidFiles(){
        List<String> filesToAccept = new LinkedList<>();

        filesToAccept.add(PATH+"graph01.grph");
        filesToAccept.add(PATH+"graph02.grph");
        filesToAccept.add(PATH+"graph03.grph");
        filesToAccept.add(PATH+"graph04.grph");
        filesToAccept.add(PATH+"graph08.grph");
        filesToAccept.add(PATH_EULER + "eulG1.grph");
        filesToAccept.add(PATH_EULER + "eulG2.grph");
        filesToAccept.add(PATH_EULER + "eulG3.grph");

        GraphFileReader reader = new GraphFileReader();
        for (String file : filesToAccept){
            assertDoesNotThrow(() -> reader.getGraphFromFile(file));
        }
    }

    @Test
    public void testReadInvalidFiles(){

        List<String> filesToDenied = new LinkedList<>();
        //syntax error: "::" but no weight specified
        filesToDenied.add(PATH+"graph05.grph");
        //semantic error: doubled edge "f"
        filesToDenied.add(PATH+"graph06.grph");
        //syntax error: triple edge a-gb-j
        filesToDenied.add(PATH+"graph09.grph");
        //syntax error: missing semicolon
        filesToDenied.add(PATH+"fail01.grph");
        //syntax error: missing "#" in first line
        filesToDenied.add(PATH+"fail02.grph");
        //syntax error: two weights for one edge
        filesToDenied.add(PATH+"fail03.grph");
        //syntax error: only one node in one line but with weight
        filesToDenied.add(PATH+"fail04.grph");
        //semantic error: graph has both weighted and unweighted edges
        filesToDenied.add(PATH+"fail05.grph");
        GraphFileReader reader = new GraphFileReader();
        for (String file : filesToDenied){
            assertThrows(Exception.class,() -> reader.getGraphFromFile(file));
        }
    }

    @Test
    public void testWriteFile(){
        String file = PATH+"graph01.grph";
        GraphFileReader reader = new GraphFileReader();
        Graph graphPrev = null;
        try{
            // Lese graph04.grph, wandle in Graphobjekt und schreibe wieder in Datei (graphAfter.grph)
            graphPrev = reader.getGraphFromFile(file);
            GraphFileWriter writer = new GraphFileWriter();
            writer.writeFile(graphPrev, "graphAfter.grph");
        } catch (Exception e){
            fail(e.getMessage());
        }
        Graph graphAfter = null;
        try{
            // Lese und wandle das eben geschriebene graphAfter.grph file in ein Graphobjekt zurück
            graphAfter = reader.getGraphFromFile("graphAfter.grph");
        } catch (Exception e){
            fail(e.getMessage());
        }
        // Vergleiche die Zeilen
        for(int i =0;i<graphPrev.getEdgeCount();i++){
            Edge edgePrev = graphPrev.getEdge(i);
            Edge edgeAfter = graphAfter.getEdge(edgePrev.getId());
            assertNotNull(edgeAfter);
            if(edgePrev.getAttribute("weight") != null){
                assertEquals(edgePrev.getAttribute("weight").toString(), edgeAfter.getAttribute("weight").toString());
            }
        }
        // Prüfe ob keine Kanten zusätzlich geschrieben wurden
        for(int i =0;i<graphAfter.getEdgeCount();i++){
            assertNotNull(graphPrev.getEdge(graphAfter.getEdge(i).getId()));
        }
        // Vergleiche die Knoten
        for(int i =0;i<graphPrev.getNodeCount();i++){
            Node nodePrev = graphPrev.getNode(i);
            Node nodeAfter = graphAfter.getNode(nodePrev.getId());
            assertNotNull(nodeAfter);
            assertEquals(nodePrev.getAttribute("attr"), nodeAfter.getAttribute("attr"));
        }
        // Prüfe ob keine Knoten zusätzlich geschrieben wurden
        for(int i =0;i<graphAfter.getNodeCount();i++){
            assertNotNull(graphPrev.getNode(graphAfter.getNode(i).getId()));
        }
    }
}
