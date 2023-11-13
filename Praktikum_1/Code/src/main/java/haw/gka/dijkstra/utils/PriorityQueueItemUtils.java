package haw.gka.dijkstra.utils;

import haw.gka.dijkstra.models.PriorityQueueItem;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PriorityQueueItemUtils {
    public static final String ATTRIBUTE_WEIGHT = "weight";

    public static PriorityQueueItem initializeEmptyPriorityQueueItem() {
        PriorityQueueItem priorityQueueItem = new PriorityQueueItem();
        priorityQueueItem.setDistance(0);
        priorityQueueItem.setNodes(Collections.emptyList());
        return priorityQueueItem;
    }

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

        int concatenatedDistance = initialPriorityQueueItem.getDistance() + adjacentPriorityQueueItem.getDistance();
        concatenatedPriorityQueueItem.setDistance(concatenatedDistance);

        List<MultiNode> concatenatedNodes = new LinkedList<>();
        concatenatedNodes.addAll(initialPriorityQueueItem.getNodes());
        concatenatedNodes.addAll(adjacentPriorityQueueItem.getNodes());
        concatenatedPriorityQueueItem.setNodes(concatenatedNodes);

        return concatenatedPriorityQueueItem;
    }
}
