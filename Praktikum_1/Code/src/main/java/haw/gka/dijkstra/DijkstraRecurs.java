package haw.gka.dijkstra;

import haw.gka.dijkstra.models.PriorityQueueItem;
import haw.gka.dijkstra.utils.NodeUtils;
import haw.gka.dijkstra.utils.PriorityQueueItemUtils;
import haw.gka.dijkstra.utils.PriorityQueueUtils;
import haw.gka.exceptions.NodeNotFoundException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.*;

public class DijkstraRecurs {
    private static final String ATTRIBUTE_WEIGHT = "weight";

    public static PriorityQueueItem calculateFastestPathRecurs(MultiNode startNode, MultiNode endNode, MultiGraph graph) throws NodeNotFoundException {
        if (!NodeUtils.graphContainsNode(startNode, graph) || !NodeUtils.graphContainsNode(endNode, graph)) {
            throw new NodeNotFoundException("Could not find start or end node in graph");
        }

        PriorityQueue<PriorityQueueItem> priorityQueue = PriorityQueueUtils.initializePriorityQueue();
        PriorityQueue<PriorityQueueItem> finalPathsQueue = PriorityQueueUtils.initializePriorityQueue();

        PriorityQueueItem startItem = PriorityQueueItemUtils.initializeStartingPriorityQueueItem(startNode);
        priorityQueue.add(startItem);
        Set<MultiNode> closedList = new HashSet<>();
        finalPathsQueue =  recursStep(finalPathsQueue, priorityQueue, endNode,closedList, graph);

        if (startNode.equals(endNode)){
            return startItem;
        }

        if (finalPathsQueue.isEmpty()){
            PriorityQueueItem emptyResult = new PriorityQueueItem();
            emptyResult.setDistance(0);
            emptyResult.setNodes(Collections.emptyList());
            return emptyResult;
        } else return  (PriorityQueueItem) finalPathsQueue.poll();
    }

    public static PriorityQueue  recursStep(PriorityQueue finalPathsQueue,  PriorityQueue priorityQueue, MultiNode endNode,Set<MultiNode> closedList, MultiGraph graph){
        if (priorityQueue.isEmpty()) {
            return finalPathsQueue; // No path found
        }

        PriorityQueueItem actualPriorityQueueItem = (PriorityQueueItem) priorityQueue.peek();
        MultiNode lastNode = actualPriorityQueueItem.getLastNode();
        closedList.add(lastNode);

        if ((lastNode.equals(endNode))&& (allNodesVisited(closedList, graph))){
            finalPathsQueue.add(actualPriorityQueueItem);
            return finalPathsQueue; //actualPriorityQueueItem;
        }
            int dist = actualPriorityQueueItem.getDistance();
            List<MultiNode> adjacentNodeList = NodeUtils.getAdjacentNodes(lastNode);
            adjacentNodeList.stream().filter(x ->!closedList.contains(x)).forEach(x ->{
                Edge edge = lastNode.getEdgeBetween(x);
                int weight = Integer.parseInt(edge.getAttribute(ATTRIBUTE_WEIGHT).toString());
                int newDist = dist + weight;
                PriorityQueueItem existingNodeItem = findInPriorityQueue(priorityQueue, x);
                if (existingNodeItem == null) {
                    PriorityQueueItem newItem = new PriorityQueueItem();
                    newItem.setDistance(newDist);
                    newItem.setNodes(new ArrayList<>(actualPriorityQueueItem.getNodes()));  // Копировать текущий путь
                    newItem.addNode(x);
                    priorityQueue.add(newItem);
                    if (x.equals(endNode)){
                        finalPathsQueue.add(newItem);
                    }
                } else if (existingNodeItem.getDistance() > newDist) {
                    priorityQueue.remove(existingNodeItem);
                    existingNodeItem.setDistance(newDist);
                    existingNodeItem.setNodes(new ArrayList<>(actualPriorityQueueItem.getNodes()));  // Копировать текущий путь
                    existingNodeItem.addNode(x);
                    priorityQueue.add(existingNodeItem);
                    priorityQueue.add(existingNodeItem);
                    if (x.equals(endNode)){
                        finalPathsQueue.add(existingNodeItem);
                    }
                }

                });

            priorityQueue.poll();

            return recursStep(finalPathsQueue, priorityQueue, endNode, closedList, graph);
        }


        private static PriorityQueueItem findInPriorityQueue(PriorityQueue<PriorityQueueItem> priorityQueue, MultiNode node) {
        for (PriorityQueueItem item : priorityQueue) {
        if (item.getLastNode().equals(node)) {
        return item;
        }
        }
        return null;
        }


    private static boolean allNodesVisited(Set<MultiNode> closedList, MultiGraph graph) {
        return closedList.size() == graph.getNodeCount();
    }

}




