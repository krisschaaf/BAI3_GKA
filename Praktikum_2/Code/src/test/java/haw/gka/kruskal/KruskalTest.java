package haw.gka.kruskal;

import haw.gka.GraphGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class KruskalTest {

    public static final String ATTRIBUTE_WEIGHT = "weight";
    public static final String ATTRIBUTE_UI_LABEL = "ui.label";

    @Test
    public void testOptimalSolutionFound()  {
        MultiGraph testGraph = new MultiGraph("testGraph");
        testGraph.addNode("A");
        testGraph.addNode("B");
        testGraph.addNode("C");
        testGraph.addNode("D");
        testGraph.addNode("E");
        testGraph.addNode("F");
        testGraph.addNode("G");
        testGraph.addNode("H");
        testGraph.addNode("I");
        testGraph.addNode("J");

        testGraph.addEdge("AB","A", "B", false);
        testGraph.getEdge("AB").setAttribute("weight", 2);
        testGraph.addEdge("AC","A", "C", false);
        testGraph.getEdge("AC").setAttribute("weight", 4);
        testGraph.addEdge("BD","B", "D", false);
        testGraph.getEdge("BD").setAttribute("weight", 4);
        testGraph.addEdge("BE","B", "E", false);
        testGraph.getEdge("BE").setAttribute("weight", 9);
        testGraph.addEdge("BC","B", "C", false);
        testGraph.getEdge("BC").setAttribute("weight", 5);
        testGraph.addEdge("CG","C", "G", false);
        testGraph.getEdge("CG").setAttribute("weight", 2);
        testGraph.addEdge("CH","C", "H", false);
        testGraph.getEdge("CH").setAttribute("weight", 7);
        testGraph.addEdge("CE","C", "E", false);
        testGraph.getEdge("CE").setAttribute("weight", 1);
        testGraph.addEdge("DE","D", "E", false);
        testGraph.getEdge("DE").setAttribute("weight", 2);
        testGraph.addEdge("GF","G", "F", false);
        testGraph.getEdge("GF").setAttribute("weight", 1);
        testGraph.addEdge("GH","G", "H", false);
        testGraph.getEdge("GH").setAttribute("weight", 3);
        testGraph.addEdge("GJ","G", "J", false);
        testGraph.getEdge("GJ").setAttribute("weight", 8);
        testGraph.addEdge("GI","G", "I", false);
        testGraph.getEdge("GI").setAttribute("weight", 6);
        testGraph.addEdge("HJ","H", "J", false);
        testGraph.getEdge("HJ").setAttribute("weight", 5);
        testGraph.addEdge("FI","F", "I", false);
        testGraph.getEdge("FI").setAttribute("weight", 2);
        testGraph.addEdge("IJ","I", "J", false);
        testGraph.getEdge("IJ").setAttribute("weight", 6);

        for(int i = 0; i < 10; i++){
            testGraph = Kruskal.createMinimalSpanningForrest(testGraph);

            assertNull(testGraph.getEdge("BD"));
            assertNull(testGraph.getEdge("BE"));
            assertNull(testGraph.getEdge("BC"));
            assertNull(testGraph.getEdge("CH"));
            assertNull(testGraph.getEdge("GI"));
            assertNull(testGraph.getEdge("IJ"));
            assertNull(testGraph.getEdge("GJ"));

            assertNotNull(testGraph.getEdge("AB"));
            assertNotNull(testGraph.getEdge("AC"));
            assertNotNull(testGraph.getEdge("DE"));
            assertNotNull(testGraph.getEdge("CE"));
            assertNotNull(testGraph.getEdge("CG"));
            assertNotNull(testGraph.getEdge("GF"));
            assertNotNull(testGraph.getEdge("GH"));
            assertNotNull(testGraph.getEdge("HJ"));
            assertNotNull(testGraph.getEdge("FI"));
        }
    }
    @Test
    public void testOptimalSolutionFoundMultiEdges()  {
        MultiGraph testGraph = new MultiGraph("testGraph");
        testGraph.addNode("A");
        testGraph.addNode("B");
        testGraph.addNode("C");
        testGraph.addNode("D");
        testGraph.addNode("E");
        testGraph.addNode("F");
        testGraph.addNode("G");
        testGraph.addNode("H");
        testGraph.addNode("I");
        testGraph.addNode("J");

        testGraph.addNode("K");
        testGraph.addNode("L");
        testGraph.addNode("M");

        testGraph.addEdge("AB","A", "B", false);
        testGraph.getEdge("AB").setAttribute("weight", 2);
        testGraph.addEdge("AB2","A", "B", false);
        testGraph.getEdge("AB2").setAttribute("weight", 15);
        testGraph.addEdge("AC","A", "C", false);
        testGraph.getEdge("AC").setAttribute("weight", 4);
        testGraph.addEdge("BD","B", "D", false);
        testGraph.getEdge("BD").setAttribute("weight", 4);
        testGraph.addEdge("BE","B", "E", false);
        testGraph.getEdge("BE").setAttribute("weight", 9);
        testGraph.addEdge("BC","B", "C", false);
        testGraph.getEdge("BC").setAttribute("weight", 5);
        testGraph.addEdge("CG","C", "G", false);
        testGraph.getEdge("CG").setAttribute("weight", 2);
        testGraph.addEdge("CG2","C", "G", false);
        testGraph.getEdge("CG2").setAttribute("weight", 10);
        testGraph.addEdge("CH","C", "H", false);
        testGraph.getEdge("CH").setAttribute("weight", 7);
        testGraph.addEdge("CE","C", "E", false);
        testGraph.getEdge("CE").setAttribute("weight", 1);
        testGraph.addEdge("DE","D", "E", false);
        testGraph.getEdge("DE").setAttribute("weight", 2);
        testGraph.addEdge("GF","G", "F", false);
        testGraph.getEdge("GF").setAttribute("weight", 1);
        testGraph.addEdge("GH","G", "H", false);
        testGraph.getEdge("GH").setAttribute("weight", 3);
        testGraph.addEdge("GJ","G", "J", false);
        testGraph.getEdge("GJ").setAttribute("weight", 8);
        testGraph.addEdge("GI","G", "I", false);
        testGraph.getEdge("GI").setAttribute("weight", 6);
        testGraph.addEdge("HJ","H", "J", false);
        testGraph.getEdge("HJ").setAttribute("weight", 5);
        testGraph.addEdge("FI","F", "I", false);
        testGraph.getEdge("FI").setAttribute("weight", 2);
        testGraph.addEdge("IJ","I", "J", false);
        testGraph.getEdge("IJ").setAttribute("weight", 6);

        testGraph.addEdge("KL","K", "L", false);
        testGraph.getEdge("KL").setAttribute("weight", 5);
        testGraph.addEdge("KM","K", "M", false);
        testGraph.getEdge("KM").setAttribute("weight", 2);
        testGraph.addEdge("LM","L", "M", false);
        testGraph.getEdge("LM").setAttribute("weight", 6);

        for (int i = 0; i < 10; i++){
            testGraph = Kruskal.createMinimalSpanningForrest(testGraph);
            System.out.println(testGraph.edges().collect(Collectors.toList()));

            assertNull(testGraph.getEdge("BE"));
            assertNull(testGraph.getEdge("BC"));
            assertNull(testGraph.getEdge("CH"));
            assertNull(testGraph.getEdge("GI"));
            assertNull(testGraph.getEdge("IJ"));
            assertNull(testGraph.getEdge("GJ"));
            assertNull(testGraph.getEdge("AC"));
            assertNull(testGraph.getEdge("LM"));

            assertNotNull(testGraph.getEdge("BD"));
            assertNotNull(testGraph.getEdge("AB"));
            assertNotNull(testGraph.getEdge("DE"));
            assertNotNull(testGraph.getEdge("CE"));
            assertNotNull(testGraph.getEdge("CG"));
            assertNotNull(testGraph.getEdge("GF"));
            assertNotNull(testGraph.getEdge("GH"));
            assertNotNull(testGraph.getEdge("HJ"));
            assertNotNull(testGraph.getEdge("FI"));

            assertNotNull(testGraph.getEdge("KM"));
            assertNotNull(testGraph.getEdge("KL"));
        }
    }

    @Test
    public void succeedWithSingleGraph() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");
        graph.addNode("H");

        graph.addEdge("AB", "A", "B", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("AC", "A", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BD", "B", "D", false).setAttribute(ATTRIBUTE_WEIGHT, 2);
        graph.addEdge("BE", "B", "E", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BF", "B", "F", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("CG", "C", "G", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("FH", "F", "H", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("GH", "G", "H", false).setAttribute(ATTRIBUTE_WEIGHT, 6);

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph);

        // TODO add Assertion
    }

    @Test
    public void succeedWithNotConnectedGraphWhenOneGraphHasOnlyOneNode() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addNode("I"); // TODO: missing this node in result

        graph.addEdge("AB", "A", "B", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("AC", "A", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BD", "B", "D", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("CD", "C", "D", false).setAttribute(ATTRIBUTE_WEIGHT, 5);

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph);

        // TODO add Assertion
    }

    @Test
    public void succeedWithTwoNotConnectedGraphs() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        graph.addEdge("AB", "A", "B", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("AC", "A", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BC", "B", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 4);

        graph.addEdge("DE", "D", "E", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("DF", "D", "F", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("EF", "E", "F", false).setAttribute(ATTRIBUTE_WEIGHT, 4);

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph);

        // TODO add Assertion
    }

    @Test
    public void succeedWithSingleConnectedGraphWhenGraphHasOnlyOneNode() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph);

        // TODO: missing node in result
        // TODO add Assertion
    }

    @Test
    public void succeedWithRandomGraph() {
        MultiGraph graph = GraphGenerator.generateGraph(100, 100, 10, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph);

//        org.graphstream.algorithm.Kruskal kruskal = new org.graphstream.algorithm.Kruskal(); // TODO fix
//        kruskal.init(graph);
//        kruskal.compute();

        // TODO add Assertion
    }
}
