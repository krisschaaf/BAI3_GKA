package haw.gka.kruskal;

import haw.gka.GraphGenerator;
import org.graphstream.algorithm.Prim;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KruskalTestByDoc {
    public static final String ATTRIBUTE_WEIGHT = "weight";

    private boolean checkIfContainingSameEdges(MultiGraph result, HashSet<String> shouldEdges) {
        HashSet<String> actualEdges = (HashSet<String>) result.edges().
                map(edge -> edge.getId()).
                collect(Collectors.toSet());

        return actualEdges.containsAll(shouldEdges) && shouldEdges.containsAll(actualEdges);
    }

    private boolean checkIfContainingSameNodes(MultiGraph result, HashSet<String> shouldNodes) {
        HashSet<String> actualNodes = (HashSet<String>) result.nodes().
                map(node -> node.getId()).
                collect(Collectors.toSet());

        return actualNodes.containsAll(shouldNodes) && shouldNodes.containsAll(actualNodes);
    }

    @Test
    public void succeedWithSingleGraph() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");
        graph.addNode("H");

        graph.addEdge("AB", "A", "B", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("AC", "A", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BD", "B", "D", false).setAttribute(ATTRIBUTE_WEIGHT, 2);
        graph.addEdge("BE", "B", "E", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BF", "B", "F", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("CG", "C", "G", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("FH", "F", "H", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("GH", "G", "H", false).setAttribute(ATTRIBUTE_WEIGHT, 6);

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> shouldEdges = new HashSet<>(Arrays.asList(
                "AB", "AC", "BD", "BE", "BF", "CG", "FH"
        ));

        HashSet<String> shouldNodes = new HashSet<>(Arrays.asList(
                "A", "B", "C", "D", "E", "F", "G", "H"
        ));

        assertTrue(checkIfContainingSameEdges(result, shouldEdges));
        assertTrue(checkIfContainingSameNodes(result, shouldNodes));
    }

    @Test
    public void succeedWithTwoNotConnectedGraphs() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        graph.addEdge("AB", "A", "B", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("AC", "A", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BC", "B", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 4);

        graph.addEdge("DE", "D", "E", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("DF", "D", "F", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("EF", "E", "F", false).setAttribute(ATTRIBUTE_WEIGHT, 4);

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> shouldEdges = new HashSet<>(Arrays.asList(
                "AB", "BC", "DE", "EF"
        ));

        HashSet<String> shouldNodes = new HashSet<>(Arrays.asList(
                "A", "B", "C", "D", "E", "F"
        ));

        assertTrue(checkIfContainingSameEdges(result, shouldEdges));
        assertTrue(checkIfContainingSameNodes(result, shouldNodes));
    }

    @Test
    public void succeedWithSingleConnectedGraphWhenGraphHasOnlyOneNode() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> shouldEdges = new HashSet<>(Arrays.asList());

        HashSet<String> shouldNodes = new HashSet<>(Arrays.asList(
                "A"
        ));

        assertTrue(checkIfContainingSameEdges(result, shouldEdges));
        assertTrue(checkIfContainingSameNodes(result, shouldNodes));
    }

    @Test
    public void succeedWithNotConnectedGraphWhenOneGraphHasOnlyOneNode() {
        MultiGraph graph = new MultiGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        graph.addNode("E");

        graph.addEdge("AB", "A", "B", false).setAttribute(ATTRIBUTE_WEIGHT, 3);
        graph.addEdge("AC", "A", "C", false).setAttribute(ATTRIBUTE_WEIGHT, 6);
        graph.addEdge("BD", "B", "D", false).setAttribute(ATTRIBUTE_WEIGHT, 4);
        graph.addEdge("CD", "C", "D", false).setAttribute(ATTRIBUTE_WEIGHT, 5);

        MultiGraph result = Kruskal.createMinimalSpanningForrest(graph).getGraph();

        HashSet<String> shouldEdges = new HashSet<>(Arrays.asList(
                "AB", "BD", "CD"
        ));

        HashSet<String> shouldNodes = new HashSet<>(Arrays.asList(
                "A", "B", "C", "D", "E"
        ));

        assertTrue(checkIfContainingSameEdges(result, shouldEdges));
        assertTrue(checkIfContainingSameNodes(result, shouldNodes));

    }

    private static HashSet<String> convertEdgesToString(HashSet<Edge> ourResultEdges) {
        HashSet<String> ourResultEdgesString = new HashSet<>();

        ourResultEdges.forEach((edge) -> {
            String appender = edge.getId() + "[" + edge.getSourceNode().getId() + "--" + edge.getTargetNode().getId() + "]";
            ourResultEdgesString.add(appender);
        });

        return ourResultEdgesString;
    }

    @Test
    public void succeedWithRandomGraphKruskal() {
        MultiGraph graph = GraphGenerator.generateGraph(10000, 1000, 100, false);

        MultiGraph ourResult = Kruskal.createMinimalSpanningForrest(graph).getGraph();
        HashSet<Edge> ourResultEdges = new HashSet<>(ourResult.edges().collect(Collectors.toSet()));
        HashSet<String> ourResultEdgesString = new HashSet<>(convertEdgesToString(ourResultEdges));

        org.graphstream.algorithm.Kruskal kruskal = new org.graphstream.algorithm.Kruskal();
        kruskal.init(graph);
        kruskal.compute();

        HashSet<Edge> theirResultEdges = new HashSet<>(kruskal.getTreeEdgesStream().collect(Collectors.toSet()));
        HashSet<String> theirResultEdgesString = new HashSet<>(convertEdgesToString(theirResultEdges));

        assertTrue(ourResultEdgesString.containsAll(theirResultEdgesString));
        assertTrue(theirResultEdgesString.containsAll(ourResultEdgesString));
    }

    @Test
    public void succeedWithRandomGraphPrim() {
        MultiGraph graph = GraphGenerator.generateGraph(100, 10000, 10, false);

        KruskalResult ourResult = Kruskal.createMinimalSpanningForrest(graph);

		Prim prim = new Prim();
		prim.init(graph);
		prim.compute();

        assertEquals((int) prim.getTreeWeight(), ourResult.getTreeWeight());
    }
}
