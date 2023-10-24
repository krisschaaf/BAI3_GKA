package kris.schaaf;

import java.util.*;

/**
 * Hello world!
 *
 */
public class Dijkstra
{
    public static void main( String[] args ) { }

    public static PriorityQueueItem initialize(Node startNode, Node endNode) {

        // Schritt 1: Leere Closed List und leere Priority Queue initialisieren
        Set<Node> closedList = new HashSet<>();
        PriorityQueue<PriorityQueueItem> priorityQueue = new PriorityQueue<>(Comparator.comparingInt((PriorityQueueItem priorityQueueItem) -> priorityQueueItem.getDistance()));

        // Schritt 2: PriorityQueueItem für den Startknoten initialisieren
        PriorityQueueItem startItem = new PriorityQueueItem();
        startItem.setDistance(0);
        startItem.addNode(startNode);

        // Item der PriorityQueue ergänzen, damit nachfolgende for each Schleife funktioniert
        priorityQueue.add(startItem);
        PriorityQueueItem initialPriorityQueueItem = priorityQueue.peek();

        int counter = 1;

        //Schritt 7
        while(!allNodesVisited(counter, closedList) && !endNodeInQueueWithShortestDistance(endNode, priorityQueue)) {

            // Schritt 3: Jeder adjazente Knoten des Ausgangsknoten wird betrachtet
            for (Node adjacentNode: initialPriorityQueueItem.getLastNode().getAdjacentNodes().keySet()) {

                // Schritt 3a/b: Ist der Knoten bereits in der closed List, wird dieser nicht weiter beachtet
                if(!closedList.contains(adjacentNode)) {

                    //Initialisierung eines PriorityQueueItems
                    PriorityQueueItem adjacentPriorityQueueItem = new PriorityQueueItem();
                    adjacentPriorityQueueItem.setDistance(initialPriorityQueueItem.getLastNode().getAdjacentNodes().get(adjacentNode));
                    adjacentPriorityQueueItem.addNode(adjacentNode);

                    PriorityQueueItem concatenatedPriorityQueueItem = new PriorityQueueItem();

                    // Die Distanz ist die Distanz zwischen Ausgangsknoten und dessen adjazentem Knoten
                    int concatenatedDistance = initialPriorityQueueItem.getDistance() + adjacentPriorityQueueItem.getDistance();
                    concatenatedPriorityQueueItem.setDistance(concatenatedDistance);

                    // Die Knoten sind die Knoten des Ausgangsitems konkateniert mit dem adjazenten Knoten
                    List<Node> concatenatedNodes = new LinkedList<>();
                    concatenatedNodes.addAll(initialPriorityQueueItem.getNodes());
                    concatenatedNodes.addAll(adjacentPriorityQueueItem.getNodes());
                    concatenatedPriorityQueueItem.setNodes(concatenatedNodes);

                    // Schritt 4: Die neu entstandenen PriorityQueueItems ersetzen nun das PriorityQueueItem des
                    // Ausgangsknotens in der PriorityQueue
                    List<PriorityQueueItem> itemsToRemove = new ArrayList<>();
                    List<PriorityQueueItem> itemsToAdd = new ArrayList<>();

                    for (PriorityQueueItem priorityQueueItem : priorityQueue) {

                        // Schritt 4a: Ist der letzte Knoten aus der nodes Liste einer dieser PriorityQueueItems bereits das
                        // letzte Element einer nodes Liste eines PriorityQueueItems in der PriorityQueue
                        if (priorityQueueItem.getLastNode().equals(concatenatedPriorityQueueItem.getLastNode())) {

                            // Welche Distanz der PriorityQueueItems ist kleiner: Nur dieses
                            // PriorityQueueItem wird behalten
                            if (priorityQueueItem.getDistance() >= concatenatedPriorityQueueItem.getDistance()) {
                                itemsToRemove.add(priorityQueueItem);
                                itemsToAdd.add(concatenatedPriorityQueueItem);
                            }

                        } else {
                            itemsToAdd.add(concatenatedPriorityQueueItem);
                        }
                    }

                    priorityQueue.removeAll(itemsToRemove);
                    priorityQueue.addAll(itemsToAdd);
                }
            }

            priorityQueue.remove(initialPriorityQueueItem);

            // Schritt 5: Entfernen des obersten Elements der PriorityQueue, da dessen Kanten alle untersucht wurden
            closedList.add(initialPriorityQueueItem.getLastNode());

            // Schritt 6: Das oberste Element der PriorityQueue wird bestimmt. Das letzte Element aus dessen nodes
            // Liste wird zum neuen Ausgangsknoten und Schritt 3 wird wiederholt.
            initialPriorityQueueItem = priorityQueue.peek();

            counter++;
        }

        return initialPriorityQueueItem;
    }

    private static boolean endNodeInQueueWithShortestDistance(Node endNode, PriorityQueue<PriorityQueueItem> priorityQueue) {
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

    private static boolean allNodesVisited(int counter, Set<Node> closedList) {
        return counter == closedList.size();
    }
}
