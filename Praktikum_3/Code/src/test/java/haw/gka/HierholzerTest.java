package haw.gka;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class HierholzerTest {

    @Test
    public void testSimpleEulerGraph(){
        Graph test = new SingleGraph("test");
		test.addNode("1");
		test.addNode("2");
		test.addNode("3");
		test.addNode("4");
		test.addNode("5");
		test.addNode("6");
		test.addNode("7");
		test.addNode("8");
		test.addNode("9");

		test.addEdge("1", "1","2", false);
		test.addEdge("2", "1","3", false);
		test.addEdge("3", "1","7", false);
		test.addEdge("4", "1","8", false);
		test.addEdge("5", "2","3", false);
		test.addEdge("6", "3","4", false);
		test.addEdge("7", "3","7", false);
		test.addEdge("8", "4","5", false);
		test.addEdge("9", "4","7", false);
		test.addEdge("10", "4","9", false);
		test.addEdge("11", "5","9", false);
		test.addEdge("12", "6","7", false);
		test.addEdge("13", "6","9", false);
		test.addEdge("14", "7","9", false);
		test.addEdge("15", "7","8", false);

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
    public void testGeneratedEulerGraph() throws Exception {
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
