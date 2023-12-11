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
    public void succeedKruskalFewerNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(1000, 10000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> ourResultEdges = ourResult.edges()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        org.graphstream.algorithm.Kruskal kruskal = new org.graphstream.algorithm.Kruskal();
        kruskal.init(graph);
        kruskal.compute();

        HashSet<String> theirResultEdges = kruskal.getTreeEdgesStream()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        assertTrue(ourResultEdges.containsAll(theirResultEdges));
        assertTrue(theirResultEdges.containsAll(ourResultEdges));
    }

    @Test
    public void succeedPrimFewerNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(1000, 10000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> ourResultEdges = ourResult.edges()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

		Prim prim = new Prim();
		prim.init(graph);
		prim.compute();

        HashSet<String> theirResultEdges = prim.getTreeEdgesStream()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        assertTrue(ourResultEdges.containsAll(theirResultEdges));
        assertTrue(theirResultEdges.containsAll(ourResultEdges));
    }

    @Test
    public void succeedKruskalSameNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(10000, 10000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> ourResultEdges = ourResult.edges()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        org.graphstream.algorithm.Kruskal kruskal = new org.graphstream.algorithm.Kruskal();
        kruskal.init(graph);
        kruskal.compute();

        HashSet<String> theirResultEdges = kruskal.getTreeEdgesStream()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        assertTrue(ourResultEdges.containsAll(theirResultEdges));
        assertTrue(theirResultEdges.containsAll(ourResultEdges));
    }

    @Test
    public void succeedPrimSameNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(10000, 10000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> ourResultEdges = ourResult.edges()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

		Prim prim = new Prim();
		prim.init(graph);
		prim.compute();

        HashSet<String> theirResultEdges = prim.getTreeEdgesStream()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        assertTrue(ourResultEdges.containsAll(theirResultEdges));
        assertTrue(theirResultEdges.containsAll(ourResultEdges));
    }

    @Test
    public void succeedKruskalMoreNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(50000, 5000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> ourResultEdges = ourResult.edges()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        org.graphstream.algorithm.Kruskal kruskal = new org.graphstream.algorithm.Kruskal();
        kruskal.init(graph);
        kruskal.compute();

        HashSet<String> theirResultEdges = kruskal.getTreeEdgesStream()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        assertTrue(ourResultEdges.containsAll(theirResultEdges));
        assertTrue(theirResultEdges.containsAll(ourResultEdges));
    }

    @Test
    public void succeedPrimMoreNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(50000, 5000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> ourResultEdges = ourResult.edges()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

		Prim prim = new Prim();
		prim.init(graph);
		prim.compute();

        HashSet<String> theirResultEdges = prim.getTreeEdgesStream()
                .map(KruskalTestRandomGraph::convertEdgeToString)
                .collect(Collectors.toCollection(HashSet::new));

        assertTrue(ourResultEdges.containsAll(theirResultEdges));
        assertTrue(theirResultEdges.containsAll(ourResultEdges));
    }
}
