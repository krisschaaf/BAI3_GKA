package haw.gka.io;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
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
        List<String> filesToDenied = new LinkedList<>();
        //syntax error: "::" but no weight specified
        filesToDenied.add(path+"graph05.grph");
        //semantic error: doubled edge "f"
        filesToDenied.add(path+"graph06.grph");
        //syntax error: triple edge a-gb-j
        filesToDenied.add(path+"graph09.grph");
        //syntax error: missing semicolon
        filesToDenied.add(path+"fail01.grph");
        //syntax error: missing "#" in first line
        filesToDenied.add(path+"fail02.grph");
        //syntax error: two weights for one edge
        filesToDenied.add(path+"fail03.grph");
        //syntax error: only one node in one line but with weight
        filesToDenied.add(path+"fail04.grph");
        GraphFileReader reader = new GraphFileReader();
        for (String file : filesToDenied){
            assertThrows(Exception.class,() -> reader.getGraphFromFile(file));
        }
    }

    @Test
    public void testWriteFile(){
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\graphs\\";
        String file = path+"graph01.grph";
        GraphFileReader reader = new GraphFileReader();
        Graph graphPrev = null;
        try{
            // Read known graph04.grph file, deserialize and write back to a file (graphAfter.grph)
            graphPrev = reader.getGraphFromFile(file);
            GraphFileWriter writer = new GraphFileWriter();
            writer.writeFile(graphPrev, "graphAfter.grph");
        } catch (Exception e){
            fail(e.getMessage());
        }
        Graph graphAfter = null;
        try{
            // Read and deserialize newly written graphAfter.grph file
            graphAfter = reader.getGraphFromFile("graphAfter.grph");
        } catch (Exception e){
            fail(e.getMessage());
        }
        // Check if every previous edge is similar to the new one
        for(int i =0;i<graphPrev.getEdgeCount();i++){
            Edge edgePrev = graphPrev.getEdge(i);
            Edge edgeAfter = graphAfter.getEdge(edgePrev.getId());
            assertNotNull(edgeAfter);
            assertEquals(edgePrev.getAttribute("weight"), edgeAfter.getAttribute("weight"));
        }
        // Check if there is only desired edges in new graph
        for(int i =0;i<graphAfter.getEdgeCount();i++){
            assertNotNull(graphPrev.getEdge(graphAfter.getEdge(i).getId()));
        }
        // Check if every previous node is similar to the new one
        for(int i =0;i<graphPrev.getNodeCount();i++){
            Node nodePrev = graphPrev.getNode(i);
            Node nodeAfter = graphAfter.getNode(nodePrev.getId());
            assertNotNull(nodeAfter);
            assertEquals(nodePrev.getAttribute("attr"), nodeAfter.getAttribute("attr"));
        }
        // Check if there is only desired nodes in new graph
        for(int i =0;i<graphAfter.getNodeCount();i++){
            assertNotNull(graphPrev.getNode(graphAfter.getNode(i).getId()));
        }
    }
}
