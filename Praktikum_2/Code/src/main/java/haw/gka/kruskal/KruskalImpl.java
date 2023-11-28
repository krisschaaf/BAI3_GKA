package haw.gka.kruskal;

import org.graphstream.algorithm.util.DisjointSets;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;
import java.util.stream.Collectors;

public class KruskalImpl implements Kruskal {

    @Override
    public HashSet<Edge>  createMinimalSpanningForrest(MultiGraph graph) {
        System.setProperty("org.graphstream.ui", "swing");
        //Durch die Verwendung von Priority Queue werden Edges sortiert
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt((Edge edge) -> (int) edge.getAttribute("weight")));
        //Alle Egdes des Graphes in PriorityQueue hinzufügen
        priorityQueue.addAll(graph.edges().collect(Collectors.toList()));
        //Der Minimale Spannbaum wird als HashSet von Edges gespeichert:
        HashSet<Edge> mst = new  HashSet<>();
        //Jeder Knoten wird im einen leeren HashSet gespeichert.
        //Alle HashSets mit Knoten werden wieder in Hashset gespeichert
        HashSet<HashSet<Node>> disjointMengenSet = new HashSet<>();
        graph.nodes().forEach(node ->{
            HashSet<Node> mstDisjointSet = new HashSet<>();
            mstDisjointSet.add(node);
            disjointMengenSet.add(mstDisjointSet);
        });

        while (!priorityQueue.isEmpty()) {
            //Wir nehmen oberste Kante aus Priority Queue
            Edge actualEdge = priorityQueue.poll();
            //Wir nehmen Knoten aus der Kante
            Node source = actualEdge.getSourceNode();
            Node target = actualEdge.getTargetNode();
            //Wir suchne Hashsets zu welchem beiden Knoten gehören
            HashSet<Node> sourceMenge = findSet(disjointMengenSet, source);
            HashSet<Node> targetMenge = findSet(disjointMengenSet, target);
            //Wenn die Knoten zum Unterschiedlichen Sets gehören,
            //verbinden wir sie in einem gemeinsamen Hashset und hinzufügen die Kante zum MST
            if (!sourceMenge.equals(targetMenge)){
                HashSet<Node> unionSet = new HashSet<>();
                unionSet.addAll(sourceMenge);
                unionSet.addAll(targetMenge);
                disjointMengenSet.add(unionSet);
                disjointMengenSet.remove(targetMenge);
                disjointMengenSet.remove(sourceMenge);
                mst.add(actualEdge);
            }
        }

        //Edges die zum Spannbaum gehören, werden farblich markiert
        mst.stream().forEach(x -> x.setAttribute("ui.style", "size: 5px; fill-color: red;"));
        graph.display(true);
        return mst;
    }
    HashSet<Node> findSet(  HashSet<HashSet<Node>> disjointMengeSet , Node node){
        HashSet<Node> result = null;
        for (HashSet<Node>  menge : disjointMengeSet){
            if (menge.contains(node)){
                result = menge;
            }
        }
    return  result;
    }

}
