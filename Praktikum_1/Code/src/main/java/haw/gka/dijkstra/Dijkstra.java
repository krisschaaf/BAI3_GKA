package haw.gka.dijkstra;

import haw.gka.dijkstra.models.PriorityQueueItem;
import haw.gka.dijkstra.utils.NodeUtils;
import haw.gka.dijkstra.utils.PriorityQueueItemUtils;
import haw.gka.dijkstra.utils.PriorityQueueUtils;
import haw.gka.exceptions.MultiEdgeWithSameDirectionException;
import haw.gka.exceptions.NodeNotFoundException;
import haw.gka.exceptions.UnoperableGraphException;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;


public class Dijkstra {
    public static PriorityQueueItem calculateFastestPath(MultiNode startNode, MultiNode endNode, MultiGraph graph) throws NodeNotFoundException, MultiEdgeWithSameDirectionException ,UnoperableGraphException{

        if (!NodeUtils.graphContainsNode(startNode, graph) || !NodeUtils.graphContainsNode(endNode, graph)) {
            throw new NodeNotFoundException("Could not find start or end node in graph");
        }
        // Ungewichtete Graphen können nicht berechnet werden
        if (graph.getAttribute("isWeighted") != null && !((boolean)graph.getAttribute("isWeighted"))){
            throw new UnoperableGraphException("Graph is unweighted!");
        }

        Set<MultiNode> closedList = new HashSet<>();
        PriorityQueue<PriorityQueueItem> priorityQueue = PriorityQueueUtils.initializePriorityQueue();

        PriorityQueueItem startItem = PriorityQueueItemUtils.initializeStartingPriorityQueueItem(startNode);

        priorityQueue.add(startItem);
        PriorityQueueItem initialPriorityQueueItem = priorityQueue.peek();

        while(!allNodesVisited(closedList, graph) && !endNodeInQueueWithShortestDistance(endNode, priorityQueue)) {

            // Schritt 3: Jeder adjazente Knoten des Ausgangsknoten wird betrachtet
            for (MultiNode adjacentNode: NodeUtils.getAdjacentNodes(initialPriorityQueueItem.getLastNode())) {
                // Schritt 3a/b: Ist der Knoten bereits in der closed List, wird dieser nicht weiter beachtet
                if(!closedList.contains(adjacentNode)) {
                    PriorityQueueItem adjacentPriorityQueueItem;
                    try{
                        adjacentPriorityQueueItem = PriorityQueueItemUtils.initializeAdjacentPriorityQueueItem(initialPriorityQueueItem, adjacentNode);
                    } catch (NullPointerException e){
                        throw new UnoperableGraphException("No valid neighbours of start node!");
                    }
                    PriorityQueueItem concatenatedPriorityQueueItem = PriorityQueueItemUtils.concatenatePriorityQueueItems(initialPriorityQueueItem, adjacentPriorityQueueItem);

                    // Schritt 4: Die neu entstandenen PriorityQueueItems ersetzen nun das PriorityQueueItem des Ausgangsknotens in der PriorityQueue
                    PriorityQueueUtils.organizePriorityQueue(priorityQueue, concatenatedPriorityQueueItem);
                }
            }

            // Schritt 5: Entfernen des obersten Elements der PriorityQueue, da dessen Kanten alle untersucht wurden
            priorityQueue.remove(initialPriorityQueueItem);
            closedList.add(initialPriorityQueueItem.getLastNode());

            // Wenn kein Pfad gefunden werden kann wird eine leeres PriorityQueueItem zurückgegeben
            if (priorityQueue.size() == 0) {
                return PriorityQueueItemUtils.initializeEmptyPriorityQueueItem();
            }

            // Schritt 6: Das oberste Element der PriorityQueue wird bestimmt. Das letzte Element aus dessen nodes
            // Liste wird zum neuen Ausgangsknoten und Schritt 3 wird wiederholt.
            initialPriorityQueueItem = priorityQueue.peek();
        }

        if(initialPriorityQueueItem.getLastNode().equals(endNode)) {
            return initialPriorityQueueItem;
        } else {
           return PriorityQueueItemUtils.initializeEmptyPriorityQueueItem();
        }
    }

    private static boolean allNodesVisited(Set<MultiNode> closedList, MultiGraph graph) {
        return closedList.size() == graph.getEdgeCount();
    }

    private static boolean endNodeInQueueWithShortestDistance(MultiNode endNode, PriorityQueue<PriorityQueueItem> priorityQueue) {
        return priorityQueue.peek().getLastNode().equals(endNode);
    }
}
