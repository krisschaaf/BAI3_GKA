package haw.gka.kruskal;

import org.graphstream.graph.implementations.MultiGraph;

public interface Kruskal {
    public DisjointSet createMinimalSpanningForrest(MultiGraph graph);
}
