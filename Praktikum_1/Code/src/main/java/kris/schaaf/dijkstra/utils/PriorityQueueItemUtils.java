package kris.schaaf.dijkstra.utils;

import kris.schaaf.dijkstra.models.PriorityQueueItem;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiNode;

import java.util.LinkedList;
import java.util.List;

public class PriorityQueueItemUtils {
    private static final String ATTRIBUTE_WEIGHT = "weight";

    public static PriorityQueueItem initializeStartingPriorityQueueItem(MultiNode startNode) {
        PriorityQueueItem startItem = new PriorityQueueItem();
        startItem.setDistance(0);
        startItem.addNode(startNode);
        return startItem;
    }

    public static PriorityQueueItem initializeAdjacentPriorityQueueItem(PriorityQueueItem initialPriorityQueueItem, MultiNode adjacentNode) {
        PriorityQueueItem adjacentPriorityQueueItem = new PriorityQueueItem();

        Edge incidentEdge = initialPriorityQueueItem.getLastNode().getEdgeBetween(adjacentNode);
        int weight = Integer.parseInt(incidentEdge.getAttribute(ATTRIBUTE_WEIGHT).toString());

        adjacentPriorityQueueItem.setDistance(weight);
        adjacentPriorityQueueItem.addNode(adjacentNode);

        return adjacentPriorityQueueItem;
    }

    public static PriorityQueueItem concatenatePriorityQueueItems(PriorityQueueItem initialPriorityQueueItem, PriorityQueueItem adjacentPriorityQueueItem) {
        PriorityQueueItem concatenatedPriorityQueueItem = new PriorityQueueItem();

        // Die Distanz ist die Distanz zwischen Ausgangsknoten und dessen adjazentem Knoten
        int concatenatedDistance = initialPriorityQueueItem.getDistance() + adjacentPriorityQueueItem.getDistance();
        concatenatedPriorityQueueItem.setDistance(concatenatedDistance);

        // Die Knoten sind die Knoten des Ausgangsitems konkateniert mit dem adjazenten Knoten
        List<MultiNode> concatenatedNodes = new LinkedList<>();
        concatenatedNodes.addAll(initialPriorityQueueItem.getNodes());
        concatenatedNodes.addAll(adjacentPriorityQueueItem.getNodes());
        concatenatedPriorityQueueItem.setNodes(concatenatedNodes);

        return concatenatedPriorityQueueItem;
    }
}
