package haw.gka.kruskal;

import haw.gka.GraphGenerator;
import org.graphstream.algorithm.Prim;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KruskalTestRandomGraph {

    private static String convertEdgeToString(Edge edge) {
        return edge.getId() + "[" + edge.getSourceNode().getId() + "--" + edge.getTargetNode().getId() + "]";
    }

    @Test
    public void succeedWithRandomGraphKruskal() {
        MultiGraph graph = GraphGenerator.generateGraph(10000, 1000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph);

        HashSet<String> ourResultEdges = new HashSet<>(ourResult.edges()
                .map((edge -> convertEdgeToString(edge)))
                .collect(Collectors.toSet()));

        org.graphstream.algorithm.Kruskal kruskal = new org.graphstream.algorithm.Kruskal();
        kruskal.init(graph);
        kruskal.compute();

        HashSet<String> theirResultEdges = new HashSet<>(kruskal.getTreeEdgesStream()
                .map((edge -> convertEdgeToString(edge)))
                .collect(Collectors.toSet()));

        assertTrue(ourResultEdges.containsAll(theirResultEdges));
        assertTrue(theirResultEdges.containsAll(ourResultEdges));
    }

    @Test
    public void succeedWithRandomGraphPrim() {
        MultiGraph graph = GraphGenerator.generateGraph(100, 100, 10, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph);

		Prim prim = new Prim();
		prim.init(graph);
		prim.compute();


        // TODO add Assertion
    }
}
