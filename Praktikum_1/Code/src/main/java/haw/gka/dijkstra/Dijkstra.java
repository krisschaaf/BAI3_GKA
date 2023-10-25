package haw.gka.dijkstra;

import haw.gka.dijkstra.utils.PriorityQueueItemUtils;
import haw.gka.dijkstra.utils.PriorityQueueUtils;
import haw.gka.dijkstra.models.PriorityQueueItem;
import haw.gka.dijkstra.utils.NodeUtils;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.*;
import java.util.stream.Collectors;


public class Dijkstra
{

    public static PriorityQueueItem calculateFastestPath(MultiNode startNode, MultiNode endNode, MultiGraph graph) {

        // Schritt 1: Leere Closed List und leere Priority Queue initialisieren
        Set<MultiNode> closedList = new HashSet<>();
        PriorityQueue<PriorityQueueItem> priorityQueue = PriorityQueueUtils.initializePriorityQueue();

        // Schritt 2: PriorityQueueItem für den Startknoten initialisieren
        PriorityQueueItem startItem = PriorityQueueItemUtils.initializeStartingPriorityQueueItem(startNode);

        // Item der PriorityQueue ergänzen, damit nachfolgende for each Schleife funktioniert
        priorityQueue.add(startItem);
        PriorityQueueItem initialPriorityQueueItem = priorityQueue.peek();

        //Schritt 7
        while(!allNodesVisited(closedList, graph) && !endNodeInQueueWithShortestDistance(endNode, priorityQueue)) {

            // Schritt 3: Jeder adjazente Knoten des Ausgangsknoten wird betrachtet
            for (Node node: initialPriorityQueueItem.getLastNode().neighborNodes().collect(Collectors.toList())) {

                MultiNode adjacentNode = NodeUtils.castToAdjacentNode(node);

                // Schritt 3a/b: Ist der Knoten bereits in der closed List, wird dieser nicht weiter beachtet
                if(!closedList.contains(adjacentNode)) {

                    //Initialisierung eines PriorityQueueItems für den adjazenten Knoten
                    PriorityQueueItem adjacentPriorityQueueItem = PriorityQueueItemUtils.initializeAdjacentPriorityQueueItem(initialPriorityQueueItem, adjacentNode);

                    //Konkatenieren der beiden PriorityQueueItems
                    PriorityQueueItem concatenatedPriorityQueueItem = PriorityQueueItemUtils.concatenatePriorityQueueItems(initialPriorityQueueItem, adjacentPriorityQueueItem);

                    // Schritt 4: Die neu entstandenen PriorityQueueItems ersetzen nun das PriorityQueueItem des
                    // Ausgangsknotens in der PriorityQueue
                    PriorityQueueUtils.organizePriorityQueue(priorityQueue, concatenatedPriorityQueueItem);
                }
            }

            // Schritt 5: Entfernen des obersten Elements der PriorityQueue, da dessen Kanten alle untersucht wurden
            priorityQueue.remove(initialPriorityQueueItem);
            closedList.add(initialPriorityQueueItem.getLastNode());

            // Schritt 6: Das oberste Element der PriorityQueue wird bestimmt. Das letzte Element aus dessen nodes
            // Liste wird zum neuen Ausgangsknoten und Schritt 3 wird wiederholt.
            initialPriorityQueueItem = priorityQueue.peek();
        }

        return initialPriorityQueueItem;
    }

    private static boolean allNodesVisited(Set<MultiNode> closedList, MultiGraph graph) {
        return closedList.size() == graph.getEdgeCount();
    }

    private static boolean endNodeInQueueWithShortestDistance(MultiNode endNode, PriorityQueue<PriorityQueueItem> priorityQueue) {
        for(PriorityQueueItem priorityQueueItem: priorityQueue) {
            if(priorityQueueItem.getLastNode().equals(endNode)) {

                // Nur durchlaufen, wenn Zielknoten in PriorityQueue enthalten ist
                int shortestDistance = Integer.MAX_VALUE;

                for(PriorityQueueItem priorityQueueItemForDistance: priorityQueue) {
                    if(priorityQueueItemForDistance.getDistance() <= shortestDistance) {
                        shortestDistance = priorityQueueItemForDistance.getDistance();
                    }
                }

                if(priorityQueueItem.getDistance() <= shortestDistance) {
                    return true;
                }
            }
        }
        return false;
    }
}
