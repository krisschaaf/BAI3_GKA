package haw.gka;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class HierholzerTest {
    @Test
    public void testIdentifiedEulerGraph() throws Exception {
        Graph test = GraphGenerator.createEulerGraph(100, 4000, "test");
        List<Graph> eulerCircles = Hierholzer.findEulerGraphs(test);

        HashSet<Edge> collectedEdges = new HashSet<Edge>();
        for (Graph g : eulerCircles){
            for(Node n : g.nodes().collect(Collectors.toList())){
                assertEquals((n.getDegree()%2),0);
            }
            collectedEdges.addAll(g.edges().collect(Collectors.toSet()));
        }
        assertEquals(test.getEdgeCount(),collectedEdges.size());
    }

    @Test
    public void testIdentifyNonEulerGraph() throws Exception {
        Random rnd = new Random();
        for(int i = 1; i < 10; i++){
            Graph test = GraphGenerator.createEulerGraph(100, i*1000, "test");
            test.removeEdge(rnd.nextInt(test.getEdgeCount()));
            assertThrows(Exception.class, () -> Hierholzer.findEulerGraphs(test));
        }
        for(int i = 1; i < 10; i++){
            Graph test = GraphGenerator.createEulerGraph(100, i*1000, "test");
            test.removeNode(rnd.nextInt(test.getNodeCount()));
            assertThrows(Exception.class, () -> Hierholzer.findEulerGraphs(test));
        }
    }

}
