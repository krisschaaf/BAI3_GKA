package haw.gka.kruskal;

import haw.gka.exceptions.UnoperableGraphException;
import haw.gka.io.GraphFileReader;
import haw.gka.io.GraphFileWriter;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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
        KruskalImpl kruskal = new KruskalImpl();

        for(int i = 0; i < 10; i++){
            kruskal.createMinimalSpanningForrest(testGraph);
            testGraph = (MultiGraph) kruskal.getOutputGraph();

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
        KruskalImpl kruskal = new KruskalImpl();

        for (int i = 0; i < 10; i++){
            System.out.println(kruskal.createMinimalSpanningForrest(testGraph));
            testGraph = (MultiGraph) kruskal.getOutputGraph();


            assertNull(testGraph.getEdge("BE"));
            assertNull(testGraph.getEdge("BC"));
            assertNull(testGraph.getEdge("CH"));
            assertNull(testGraph.getEdge("GI"));
            assertNull(testGraph.getEdge("IJ"));
            assertNull(testGraph.getEdge("GJ"));
            assertNull(testGraph.getEdge("AC"));

            assertNotNull(testGraph.getEdge("BD"));
            assertNotNull(testGraph.getEdge("AB"));
            assertNotNull(testGraph.getEdge("DE"));
            assertNotNull(testGraph.getEdge("CE"));
            assertNotNull(testGraph.getEdge("CG"));
            assertNotNull(testGraph.getEdge("GF"));
            assertNotNull(testGraph.getEdge("GH"));
            assertNotNull(testGraph.getEdge("HJ"));
            assertNotNull(testGraph.getEdge("FI"));
        }

    }
}
