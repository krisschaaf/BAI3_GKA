package haw.gka.kruskal;

import haw.gka.GraphGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.algorithm.Kruskal;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class KruskalTest {

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

        MultiGraph compareGraph = haw.gka.kruskal.Kruskal.createMinimalSpanningForrest(testGraph);
        Kruskal builtInAlgo = new Kruskal();
        builtInAlgo.init(testGraph);
        builtInAlgo.compute();
        Iterable<Edge> builtInResult = builtInAlgo.getTreeEdges();

        for(Edge e : builtInResult){
            assertEquals(e.getSourceNode().getId(), compareGraph.getEdge(e.getId()).getSourceNode().getId());
            assertEquals(e.getTargetNode().getId(), compareGraph.getEdge(e.getId()).getTargetNode().getId());
            assertNotEquals(e.getId(),"BD");
            assertNotEquals(e.getId(),"BE");
            assertNotEquals(e.getId(),"BC");
            assertNotEquals(e.getId(),"CH");
            assertNotEquals(e.getId(), "GI");
            assertNotEquals(e.getId(), "IJ");
            assertNotEquals(e.getId(), "GJ");
        }

        assertNull(compareGraph.getEdge("BD"));
        assertNull(compareGraph.getEdge("BE"));
        assertNull(compareGraph.getEdge("BC"));
        assertNull(compareGraph.getEdge("CH"));
        assertNull(compareGraph.getEdge("GI"));
        assertNull(compareGraph.getEdge("IJ"));
        assertNull(compareGraph.getEdge("GJ"));

        assertNotNull(compareGraph.getEdge("AB"));
        assertNotNull(compareGraph.getEdge("AC"));
        assertNotNull(compareGraph.getEdge("DE"));
        assertNotNull(compareGraph.getEdge("CE"));
        assertNotNull(compareGraph.getEdge("CG"));
        assertNotNull(compareGraph.getEdge("GF"));
        assertNotNull(compareGraph.getEdge("GH"));
        assertNotNull(compareGraph.getEdge("HJ"));
        assertNotNull(compareGraph.getEdge("FI"));
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


        MultiGraph compareGraph = haw.gka.kruskal.Kruskal.createMinimalSpanningForrest(testGraph);

        assertNull(compareGraph.getEdge("BE"));
        assertNull(compareGraph.getEdge("BC"));
        assertNull(compareGraph.getEdge("CH"));
        assertNull(compareGraph.getEdge("GI"));
        assertNull(compareGraph.getEdge("IJ"));
        assertNull(compareGraph.getEdge("GJ"));
        assertNull(compareGraph.getEdge("AC"));
        assertNull(compareGraph.getEdge("LM"));

        assertNotNull(compareGraph.getEdge("BD"));
        assertNotNull(compareGraph.getEdge("AB"));
        assertNotNull(compareGraph.getEdge("DE"));
        assertNotNull(compareGraph.getEdge("CE"));
        assertNotNull(compareGraph.getEdge("CG"));
        assertNotNull(compareGraph.getEdge("GF"));
        assertNotNull(compareGraph.getEdge("GH"));
        assertNotNull(compareGraph.getEdge("HJ"));
        assertNotNull(compareGraph.getEdge("FI"));

        assertNotNull(compareGraph.getEdge("KM"));
        assertNotNull(compareGraph.getEdge("KL"));
    }

    @Test
    public void testWeightlessGraphRefused(){
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
        testGraph.addEdge("AB2","A", "B", false);
        testGraph.addEdge("AC","A", "C", false);
        testGraph.addEdge("BD","B", "D", false);
        testGraph.addEdge("BE","B", "E", false);
        testGraph.addEdge("BC","B", "C", false);
        testGraph.addEdge("CG","C", "G", false);

        assertThrows(NullPointerException.class, () -> haw.gka.kruskal.Kruskal.createMinimalSpanningForrest(testGraph));
    }
}
