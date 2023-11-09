package haw.gka.dijkstra;

import haw.gka.dijkstra.models.PriorityQueueItem;
import haw.gka.exceptions.NodeNotFoundException;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DijkstraTest
{
    public static final String ATTRIBUTE_WEIGHT = "weight";
    public static final String ATTRIBUTE_UI_LABEL = "ui.label";

    @Test
    public void findShortestPathWhenDistanceToDestinationIsZero() throws NodeNotFoundException {
        MultiGraph graph = new MultiGraph("GraphWithZeros");
        MultiNode node1 = new MultiNode(graph, "node1");
        MultiNode node2 = new MultiNode(graph, "node2");
        MultiNode node3 = new MultiNode(graph, "node3");
        graph.addNode("node1").setAttribute(ATTRIBUTE_UI_LABEL, "Node 1");
        graph.addNode("node2").setAttribute(ATTRIBUTE_UI_LABEL, "Node 2");
        graph.addNode("node3").setAttribute(ATTRIBUTE_UI_LABEL, "Node 3");
        graph.addEdge("edge12", node1, node2).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("edge23", node2, node3).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("edge13", node1, node3).setAttribute(ATTRIBUTE_WEIGHT, 0);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(0);
        result.setNodes(Arrays.asList(node1,node3));

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(node1, node3, graph).toString());
    }
    
    @Test
    public void findShortestPathInGraphWithLoop() throws NodeNotFoundException {
        MultiGraph graph = new MultiGraph("GraphWithLoop");

        MultiNode node1 = new MultiNode(graph, "node1");
        MultiNode node2 = new MultiNode(graph, "node2");
        MultiNode node3 = new MultiNode(graph, "node3");
        MultiNode node4 = new MultiNode(graph, "node4");
        MultiNode node5 = new MultiNode(graph, "node5");
        MultiNode node6 = new MultiNode(graph, "node6");

        node1.setAttribute(ATTRIBUTE_UI_LABEL, "Node 1");
        node2.setAttribute(ATTRIBUTE_UI_LABEL, "Node 2");
        node3.setAttribute(ATTRIBUTE_UI_LABEL, "Node 3");
        node4.setAttribute(ATTRIBUTE_UI_LABEL, "Node 4");
        node5.setAttribute(ATTRIBUTE_UI_LABEL, "Node 5");
        node6.setAttribute(ATTRIBUTE_UI_LABEL, "Node 6");

        graph.addNode("node1").setAttribute(ATTRIBUTE_UI_LABEL, "Node 1");;
        graph.addNode("node2").setAttribute(ATTRIBUTE_UI_LABEL, "Node 2");
        graph.addNode("node3").setAttribute(ATTRIBUTE_UI_LABEL, "Node 3");
        graph.addNode("node4").setAttribute(ATTRIBUTE_UI_LABEL, "Node 4");
        graph.addNode("node5").setAttribute(ATTRIBUTE_UI_LABEL, "Node 5");
        graph.addNode("node6").setAttribute(ATTRIBUTE_UI_LABEL, "Node 6");
        graph.addEdge("edge12", node1, node2).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("edge23", node2, node3).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("edge34", node3, node4).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("edge45", node4, node5).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("edge52", node5, node2).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("edge36", node3, node6).setAttribute(ATTRIBUTE_WEIGHT, 100);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(102);
        result.setNodes(Arrays.asList(node1,node2,node3, node6));

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(node1, node6, graph).toString());
    }

    @Test
    public void findShortestPathInUndirectedGraph() throws NodeNotFoundException {
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


    @Test
    public void findShortestPathInDirectedGraph() throws NodeNotFoundException {
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

        graph.addEdge("e1", multiNodeA, multiNodeB, true).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("e2", multiNodeA, multiNodeC, true).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e3", multiNodeB, multiNodeD, true).setAttribute(ATTRIBUTE_WEIGHT, 2);
        graph.addEdge("e4", multiNodeB, multiNodeE, true).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e5", multiNodeB, multiNodeF, true).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e6", multiNodeC, multiNodeG, true).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e7", multiNodeF, multiNodeH, true).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e8", multiNodeG, multiNodeH, true).setAttribute(ATTRIBUTE_WEIGHT, 6);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(11);
        result.setNodes(Arrays.asList(multiNodeA,multiNodeB,multiNodeF,multiNodeH));

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(multiNodeA, multiNodeH, graph).toString());
    }

    @Test
    public void findShortestPathInMixedGraph() throws NodeNotFoundException {
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

        graph.addEdge("e1", multiNodeA, multiNodeB, true).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("e2", multiNodeA, multiNodeC, false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e3", multiNodeB, multiNodeD, true).setAttribute(ATTRIBUTE_WEIGHT, 2);
        graph.addEdge("e4", multiNodeB, multiNodeE, false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e5", multiNodeB, multiNodeF, true).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e6", multiNodeC, multiNodeG, false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e7", multiNodeF, multiNodeH, true).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e8", multiNodeG, multiNodeH, false).setAttribute(ATTRIBUTE_WEIGHT, 6);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(11);
        result.setNodes(Arrays.asList(multiNodeA,multiNodeB,multiNodeF,multiNodeH));

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(multiNodeA, multiNodeH, graph).toString());
    }

    @Test
    public void findShortestPathWhenGraphHasNonInverseMultiEdges() throws NodeNotFoundException {
        // Graphen bauen
        MultiGraph graph = new MultiGraph("foo");

        MultiNode multiNodeA = new MultiNode(graph, "A");
        MultiNode multiNodeB = new MultiNode(graph, "B");
        MultiNode multiNodeC = new MultiNode(graph, "C");
        MultiNode multiNodeD = new MultiNode(graph, "D");

        graph.addEdge("e1", multiNodeA, multiNodeB, false).setAttribute(ATTRIBUTE_WEIGHT, 10);
        graph.addEdge("e2", multiNodeA, multiNodeB, false).setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.addEdge("e3", multiNodeA, multiNodeC, false).setAttribute(ATTRIBUTE_WEIGHT, 2);
        graph.addEdge("e4", multiNodeB, multiNodeD, false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("e5", multiNodeC, multiNodeD, false).setAttribute(ATTRIBUTE_WEIGHT, 3);


        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(4);
        result.setNodes(Arrays.asList(multiNodeA,multiNodeB,multiNodeD));

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(multiNodeA, multiNodeD, graph).toString());
    }

    @Test
    public void returnEmptyItemWhenNoPathFound() throws NodeNotFoundException {
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

        graph.addEdge("e1", multiNodeA, multiNodeB, true).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("e2", multiNodeA, multiNodeC, false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e3", multiNodeB, multiNodeD, true).setAttribute(ATTRIBUTE_WEIGHT, 2);
        graph.addEdge("e4", multiNodeB, multiNodeE, false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("e5", multiNodeB, multiNodeF, true).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e6", multiNodeC, multiNodeG, false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e7", multiNodeH, multiNodeF, true).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("e8", multiNodeH, multiNodeG, true).setAttribute(ATTRIBUTE_WEIGHT, 6);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(0);
        result.setNodes(Collections.emptyList());

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(multiNodeA, multiNodeH, graph).toString());
    }

    @Test
    public void returnListWithStartNodeOnlyWhenStartAndEndNodeAreTheSame() throws NodeNotFoundException {
        MultiGraph graph = new MultiGraph("GraphWithZeros");
        MultiNode node1 = new MultiNode(graph, "node1");
        MultiNode node2 = new MultiNode(graph, "node2");
        MultiNode node3 = new MultiNode(graph, "node3");
        graph.addNode("node1").setAttribute(ATTRIBUTE_UI_LABEL, "Node 1");
        graph.addNode("node2").setAttribute(ATTRIBUTE_UI_LABEL, "Node 2");
        graph.addNode("node3").setAttribute(ATTRIBUTE_UI_LABEL, "Node 3");
        graph.addEdge("edge12", node1, node2);
        graph.addEdge("edge23", node2, node3);
        graph.addEdge("edge13", node1, node3);
        graph.getEdge("edge12").setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.getEdge("edge23").setAttribute(ATTRIBUTE_WEIGHT, 1);
        graph.getEdge("edge13").setAttribute(ATTRIBUTE_WEIGHT, 0);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(0);
        result.setNodes(Arrays.asList(node1));

        assertEquals(result.toString(), Dijkstra.calculateFastestPath(node1, node1, graph).toString());
    }

    @Test
    public void throwExceptionWhenStartNodeIsNotInGraph() {
        // Graphen bauen
        MultiGraph graph = new MultiGraph("foo");

        MultiNode multiNodeA = new MultiNode(graph, "A");
        MultiNode multiNodeB = new MultiNode(graph, "B");
        MultiNode multiNodeC = new MultiNode(graph, "C");
        MultiNode multiNodeD = new MultiNode(graph, "D");

        graph.addEdge("e1", multiNodeA, multiNodeB, false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("e2", multiNodeA, multiNodeC, false).setAttribute(ATTRIBUTE_WEIGHT, 6);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(0);
        result.setNodes(Collections.emptyList());

        assertThrows(NodeNotFoundException.class, () -> Dijkstra.calculateFastestPath(multiNodeD, multiNodeA, graph));
    }

    @Test
    public void throwExceptionWhenEndNodeIsNotInGraph() {
        // Graphen bauen
        MultiGraph graph = new MultiGraph("foo");

        MultiNode multiNodeA = new MultiNode(graph, "A");
        MultiNode multiNodeB = new MultiNode(graph, "B");
        MultiNode multiNodeC = new MultiNode(graph, "C");
        MultiNode multiNodeD = new MultiNode(graph, "D");

        graph.addEdge("e1", multiNodeA, multiNodeB, false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("e2", multiNodeA, multiNodeC, false).setAttribute(ATTRIBUTE_WEIGHT, 6);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(0);
        result.setNodes(Collections.emptyList());

        assertThrows(NodeNotFoundException.class, () -> Dijkstra.calculateFastestPath(multiNodeA, multiNodeD, graph));
    }
}
