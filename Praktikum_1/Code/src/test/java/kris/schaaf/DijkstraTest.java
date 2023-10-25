package kris.schaaf;


import kris.schaaf.dijkstra.Dijkstra;
import kris.schaaf.dijkstra.models.PriorityQueueItem;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DijkstraTest
{

    public static final String ATTRIBUTE_WEIGHT = "weight";

    @Test
    void graph_1() {
        // Graphen bauen
        MultiGraph graph = new MultiGraph("foo");

        MultiNode multiNodeA = new MultiNode(graph, "A");
        MultiNode multiNodeB = new MultiNode(graph, "B");
        MultiNode multiNodeC = new MultiNode(graph, "C");
        MultiNode multiNodeD = new MultiNode(graph, "D");
        MultiNode multiNodeE = new MultiNode(graph, "E");
        MultiNode multiNodeF = new MultiNode(graph, "F");
        MultiNode multiNodeG = new MultiNode(graph, "G");
        MultiNode multiNodeH = new MultiNode(graph, "H");

        graph.addEdge("e1", multiNodeA, multiNodeB, false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("e2", multiNodeA, multiNodeC, false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e3", multiNodeB, multiNodeD, false).setAttribute(ATTRIBUTE_WEIGHT, 2);
        graph.addEdge("e4", multiNodeB, multiNodeE, false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e5", multiNodeB, multiNodeF, false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e6", multiNodeC, multiNodeG, false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e7", multiNodeF, multiNodeH, false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e8", multiNodeG, multiNodeH, false).setAttribute(ATTRIBUTE_WEIGHT, 6);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(11);
        result.setNodes(Arrays.asList(multiNodeA,multiNodeB,multiNodeF,multiNodeH));

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(multiNodeA, multiNodeH, graph).toString());
    }
}
