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


public class DjekstraRecurs {
    //  private static final String ATTRIBUTE_WEIGHT = "weight";

    public static PriorityQueueItem calculateFastestPathRecurs(MultiNode startNode, MultiNode endNode, MultiGraph graph) throws NodeNotFoundException {

        //Überprüfung, ob der Graph den Startknoten und den Zielknoten enthält
        if (!NodeUtils.graphContainsNode(startNode, graph) || !NodeUtils.graphContainsNode(endNode, graph)) {
            throw new NodeNotFoundException("Could not find start or end node in graph");
        }

        //Initialisierung einer Prioritätswarteschlange zur Sortierung der Knoten:
        PriorityQueue<PriorityQueueItem> priorityQueue = PriorityQueueUtils.initializePriorityQueue();
        //Initialisierung einer Prioritätswarteschlange zur Speicherung der Pfade, die den Endknoten enthalten:
        PriorityQueue<PriorityQueueItem> finalPathsQueue = PriorityQueueUtils.initializePriorityQueue();

        //Erstellung eines PriorityQueueItem aus dem Startknoten und Hinzufügen dessen zur Prioritätswarteschlange
        PriorityQueueItem startItem = PriorityQueueItemUtils.initializeStartingPriorityQueueItem(startNode);
        priorityQueue.add(startItem);
        //Initialisierung einer Liste für bereits besuchte Knoten
        Set<MultiNode> closedList = new HashSet<>();

        //Start der Rekursion
        finalPathsQueue = recursStep(finalPathsQueue, priorityQueue, endNode, closedList, graph);

        // Den Startknoten zurückgeben, wenn er gleich der Endknoten ist
        if (startNode.equals(endNode)) {
            return startItem;
        }

        //Ein leeres Ergebnis zurückgeben, wenn kein Weg gefunden wurde.
        if (finalPathsQueue.isEmpty()) {
            PriorityQueueItem emptyResult = new PriorityQueueItem();
            emptyResult.setDistance(0);
            emptyResult.setNodes(Collections.emptyList());
            return emptyResult;
        }

        //Den optimalsten Pfad aus der Prioritätswarteschlange mit vollständigen Pfaden entnehmen und zurückgeben
        else return (PriorityQueueItem) finalPathsQueue.poll();
    }

    public static PriorityQueue recursStep(PriorityQueue finalPathsQueue, PriorityQueue priorityQueue, MultiNode endNode, Set<MultiNode> closedList, MultiGraph graph) {
        //Wenn die sortierende Prioritätswarteschlange leer ist, die Prioritätswarteschlange mit vollständigen Pfaden zurückgeben
        if (priorityQueue.isEmpty()) {
            return finalPathsQueue;
        }

        //Das oberste Element aus der sortierenden Prioritätswarteschlange nehmen,
        // dessen Knoten extrahieren und ihn zur Liste der verarbeiteten Knoten hinzufügen.
        PriorityQueueItem actualPriorityQueueItem = (PriorityQueueItem) priorityQueue.peek();
        MultiNode lastNode = actualPriorityQueueItem.getLastNode();
        closedList.add(lastNode);

        //Bedingungen für den Rekursionsabbruch: Wenn der aktuelle Knoten der ZielKnote ist und alle Knoten verarbeitet wurden.
        if ((lastNode.equals(endNode)) && (allNodesVisited(closedList, graph))) {
            //Den resultierenden Pfad zur Prioritätswarteschlange der vollständigen Pfade hinzufügen und diese dann zurückgeben
            finalPathsQueue.add(actualPriorityQueueItem);
            return finalPathsQueue;
        }

        //Die Distanz vom Startknoten zum aktuellen Knoten  und die Liste aller benachbarten Knoten abrufen
        int dist = actualPriorityQueueItem.getDistance();
        List<MultiNode> adjacentNodeList = NodeUtils.getAdjacentNodes(lastNode);

        //Iteration über benachbarte Knoten
        adjacentNodeList.stream().filter(x -> !closedList.contains(x)).forEach(x -> { //Ausschluss der Knoten aus der Liste der bereits verarbeiteten Knoten

            // Bestimmung der Kante zwischen dem aktuellen Knoten und dem verarbeiteten Nachbarn,
            // Berechnung des neuen Gewichts dieser Kante
            Edge edge = lastNode.getEdgeBetween(x);
            int weight = Integer.parseInt(edge.getAttribute("weight").toString());
            int newDist = dist + weight;

            //Überprüfung, ob ein Pfad mit diesem Knoten bereits in der sortierenden Prioritätswarteschlange existiert
            PriorityQueueItem existingNodeItem = findInPriorityQueue(priorityQueue, x);
            if (existingNodeItem == null) {  //Wenn dieser dem aktuellen Knoten benachbarte Knoten nicht in PriorityQue ist
                //Erstellung eines PriorityQueueItem aus dem betrachteten benachbarten Knoten
                //und dessen Hinzufügung zur Sortierwarteschlange
                PriorityQueueItem newItem = new PriorityQueueItem();
                newItem.setDistance(newDist);
                newItem.setNodes(new ArrayList<>(actualPriorityQueueItem.getNodes()));
                newItem.addNode(x);
                priorityQueue.add(newItem);
                //Wenn der betrachtete benachbarte Knoten der Endknoten ist, wird dieser Pfad zur Warteschlange mit den vollständigen Pfaden hinzugefügt
                if (x.equals(endNode)) {
                    finalPathsQueue.add(newItem);
                }
                //Wenn der benachbarte Knoten bereits in der Sortierwarteschlange vorhanden ist und die neue Distanz günstiger als die bestehende ist
            } else if (existingNodeItem.getDistance() > newDist) {
                //Entfernen des alten Elements und Ersetzen desselben durch ein neues Element der Sortierwarteschlange mit dem
                // benachbarten Knoten und seiner aktualisierten Distanz
                priorityQueue.remove(existingNodeItem);
                existingNodeItem.setDistance(newDist);
                existingNodeItem.setNodes(new ArrayList<>(actualPriorityQueueItem.getNodes()));
                existingNodeItem.addNode(x);
                priorityQueue.add(existingNodeItem);
                //Wenn der betrachtete benachbarte Knoten der Endknoten ist, wird dieser Pfad zur Warteschlange mit den vollständigen Pfaden hinzugefügt
                if (x.equals(endNode)) {
                    finalPathsQueue.add(existingNodeItem);
                }
            }
        });
        //Entfernung des aktuellen Elements aus der Sortierwarteschlange
        priorityQueue.remove(actualPriorityQueueItem);  //priorityQueue.poll();

        //Aufruf des nächsten rekursiven Schrittes
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




