package kris.schaaf;


import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DijkstraTest
{
    @Test
    void graph_1() {
        // Graphen bauen
        Node aNode = new Node("A");
        Node bNode = new Node("B");
        Node cNode = new Node("C");
        Node dNode = new Node("D");
        Node eNode = new Node("E");
        Node fNode = new Node("F");
        Node gNode = new Node("G");
        Node hNode = new Node("H");

        aNode.addDestination(bNode, 3);
        aNode.addDestination(cNode, 6);

        bNode.addDestination(dNode, 2);
        bNode.addDestination(eNode, 6);
        bNode.addDestination(fNode, 4);

        cNode.addDestination(gNode, 4);

        fNode.addDestination(hNode, 4);
        gNode.addDestination(hNode, 6);

        PriorityQueueItem result = new PriorityQueueItem();
        result.setDistance(11);
        result.setNodes(Arrays.asList(aNode, bNode, fNode, hNode));

        assertEquals(Dijkstra.initialize(aNode, hNode).toString(), result.toString());
    }
}
