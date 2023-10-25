package haw.gka.dijkstra.utils;

import haw.gka.dijkstra.models.PriorityQueueItem;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class PriorityQueueUtils {

    public static PriorityQueue<PriorityQueueItem> initializePriorityQueue() {
        return new PriorityQueue<>(
                Comparator.comparingInt((PriorityQueueItem priorityQueueItem) -> priorityQueueItem.getDistance())
        );
    }

    public static void organizePriorityQueue(PriorityQueue<PriorityQueueItem> priorityQueue, PriorityQueueItem concatenatedPriorityQueueItem) {
        Set<PriorityQueueItem> itemsToRemove = new HashSet<>();
        Set<PriorityQueueItem> itemsToAdd = new HashSet<>();

        for (PriorityQueueItem priorityQueueItem : priorityQueue) {

            // Schritt 4a: Ist der letzte Knoten aus der nodes Liste einer dieser PriorityQueueItems bereits das
            // letzte Element einer nodes Liste eines PriorityQueueItems in der PriorityQueue
            if (priorityQueueItem.getLastNode().equals(concatenatedPriorityQueueItem.getLastNode())) {

                // Welche Distanz der PriorityQueueItems ist kleiner: Nur dieses PriorityQueueItem wird behalten
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
