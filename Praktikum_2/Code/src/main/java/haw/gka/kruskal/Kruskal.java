package haw.gka.kruskal;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.HashSet;

public interface Kruskal {
    //public DisjointSet createMinimalSpanningForrest(MultiGraph graph);
    public HashSet<Edge> createMinimalSpanningForrest(MultiGraph graph);
}
