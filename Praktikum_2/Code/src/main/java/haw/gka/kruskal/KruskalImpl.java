package haw.gka.kruskal;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;
import java.util.stream.Collectors;

public class KruskalImpl {

    private MultiGraph outputGraph;

    public HashSet<Edge> createMinimalSpanningForrest(MultiGraph graph) {
        System.setProperty("org.graphstream.ui", "swing");

        // Durch die Verwendung von Priority Queue werden Edges sortiert
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt((Edge edge) -> (int) edge.getAttribute("weight")));

        // Alle Edges des Graphes in PriorityQueue hinzufügen
        priorityQueue.addAll(graph.edges().collect(Collectors.toList()));

        // Der Minimale Spannbaum wird als HashSet von Edges gespeichert:
        HashSet<Edge> mst = new HashSet<>();

        // Jeder Knoten wird in einem leeren HashSet gespeichert.
        // Alle HashSets mit Knoten werden wieder in einem Hashset gespeichert
        HashSet<HashSet<Node>> disjointSet = DisjointSet.makeSet(null);

        graph.nodes().forEach((node) -> {
            HashSet<Node> mstDisjointSet = new HashSet<>();
            mstDisjointSet.add(node);
            disjointSet.add(mstDisjointSet);
        });

        while (!priorityQueue.isEmpty()) {
            // Knoten der kleinsten Edge finden
            Edge actualEdge = priorityQueue.poll();
            Node source = actualEdge.getSourceNode();
            Node target = actualEdge.getTargetNode();

            // Sets finden, welchem beide Knoten angehören
            HashSet<Node> sourceSet = DisjointSet.findSet(disjointSet, source);
            HashSet<Node> targetSet = DisjointSet.findSet(disjointSet, target);

            // Wenn die Knoten zu unterschiedlichen Sets gehören,
            // verbinden wir sie in einem gemeinsamen Hashset und hinzufügen die Kante zum MST
            if (!sourceSet.equals(targetSet)){
                DisjointSet.union(sourceSet, targetSet, disjointSet);
                mst.add(actualEdge);
            }
        }

        // Edges die zum Spannbaum gehören, werden farblich markiert
        mst.stream().forEach(x -> x.setAttribute("ui.style", "size: 5px; fill-color: red;"));

        //Erstelle den Output Graph
        this.outputGraph = createOutputGraph(graph, mst);

        return mst;
    }

    private static MultiGraph createOutputGraph(MultiGraph graph, HashSet<Edge> mst) {
        graph = new MultiGraph(graph.getId());
        graph.setAutoCreate(true);

        for (Edge edge: mst) {
            if (graph.getNode(edge.getSourceNode().getId()) == null) {
                graph.addNode(edge.getSourceNode().getId());
            }
            if (graph.getNode(edge.getTargetNode().getId()) == null) {
                graph.addNode(edge.getTargetNode().getId());
            }
            graph.addEdge(edge.getId(), edge.getSourceNode().getId(), edge.getTargetNode().getId());
            graph.getEdge(edge.getId()).setAttribute("weight", edge.getAttribute("weight"));
        }

        for (int i = 0; i < graph.getNodeCount(); i++){
            Node d = graph.getNode(i);
            if (graph.getNode(d.getId())== null){
               graph.addNode(d.getId());
            }
        }

        return graph;
    }

    public MultiGraph getOutputGraph() {
        return this.outputGraph;
    }
}
