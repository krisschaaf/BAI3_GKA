package haw.gka.kruskal;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Kruskal {

    public static KruskalResult createMinimalSpanningForrest(MultiGraph graph) {
        System.setProperty("org.graphstream.ui", "swing");

        // Durch die Verwendung von Priority Queue werden Edges sortiert
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt((Edge edge) ->
                        (int) edge.getAttribute("weight"))
        );

        HashSet<Node> nodesWithoutEdges = collectNodesWithoutEdges(graph);

        // Alle Edges des Graphes in PriorityQueue hinzufügen
        priorityQueue.addAll(graph.edges().collect(Collectors.toList()));

        // Der Minimale Spannwald wird als HashSet von Edges gespeichert:
        HashSet<Edge> minimalSpanningForrest = new HashSet<>();

        // Initialisierung der disjunkten Teilmengen. Jeder Knoten bekommt eine eigene Teilmenge.
        HashSet<HashSet<Node>> disjointSet = DisjointSet.makeSet(graph);

        while (!priorityQueue.isEmpty()) { // TODO: weitere Abbruchbedingung (Größe mst == Anzahl Knoten des Ausgangsgraphen - 1)
            // Knoten der kleinsten Edge finden
            Edge actualEdge = priorityQueue.poll();
            Node source = actualEdge.getSourceNode();
            Node target = actualEdge.getTargetNode();

            // Sets finden, welchem beide Knoten angehören
            HashSet<Node> sourceSet = DisjointSet.findSet(disjointSet, source);
            HashSet<Node> targetSet = DisjointSet.findSet(disjointSet, target);

            // Wenn die Knoten zu unterschiedlichen Sets gehören,
            // werden diese in einem gemeinsamen Hashset verbunden und die Kante wird zum MST hinzugefügt
            if (!sourceSet.equals(targetSet)){
                DisjointSet.union(sourceSet, targetSet, disjointSet);
                minimalSpanningForrest.add(actualEdge);
            }
        }

        // Erstelle den Output Graph
        return new KruskalResult(
                createSpanningForrestGraph(graph, minimalSpanningForrest, nodesWithoutEdges),
                calculateWeightSum(minimalSpanningForrest)
        );
    }

    private static HashSet<Node> collectNodesWithoutEdges(MultiGraph graph) {
        return graph.nodes()
                .filter(node -> !node.edges().findAny().isPresent())
                .collect(Collectors.toCollection(HashSet::new));
    }

    private static MultiGraph createSpanningForrestGraph(MultiGraph graph, HashSet<Edge> minimalSpanningForrest, HashSet<Node> nodesWithoutEdges) {
        MultiGraph newOutputGraph = new MultiGraph(graph.getId());
        newOutputGraph.setAutoCreate(true);

        minimalSpanningForrest.forEach((edge) -> {
            if (newOutputGraph.getNode(edge.getSourceNode().getId()) == null) {
                newOutputGraph.addNode(edge.getSourceNode().getId());
            }
            if (newOutputGraph.getNode(edge.getTargetNode().getId()) == null) {
                newOutputGraph.addNode(edge.getTargetNode().getId());
            }
            newOutputGraph.addEdge(edge.getId(), edge.getSourceNode().getId(), edge.getTargetNode().getId());
            newOutputGraph.getEdge(edge.getId()).setAttribute("weight", edge.getAttribute("weight"));
        });

        for (int i = 0; i < newOutputGraph.getNodeCount(); i++){
            Node node = newOutputGraph.getNode(i);
            if (newOutputGraph.getNode(node.getId()) == null){
               newOutputGraph.addNode(node.getId());
            }
        }

        nodesWithoutEdges.forEach((node) -> {
            if(newOutputGraph.getNode(node.getId()) == null) {
                newOutputGraph.addNode(node.getId());
            }
        });

        return newOutputGraph;
    }

    private static Integer calculateWeightSum(HashSet<Edge> minimalSpanningForrest) {
        return minimalSpanningForrest.stream()
                .map(edge ->  edge.getAttribute("weight", Integer.class))
                .reduce(Integer::sum)
                .orElse(0);
    }
}
