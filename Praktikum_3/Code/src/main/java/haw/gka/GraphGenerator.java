package haw.gka;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class GraphGenerator {

    // TODO
    // TODO important! set id for graph
    public static Graph createEulerGraph(int nodeCount, int edgeCount, String id) {
        return new MultiGraph(id);
    }
}
