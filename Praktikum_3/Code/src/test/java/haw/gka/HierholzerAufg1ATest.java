package haw.gka;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class HierholzerAufg1ATest {

    @Test
    public void throwExceptionWhenGraphIsDirected() {
        SingleGraph graph = new SingleGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("AB", "A", "B", false);
        graph.addEdge("AC", "A", "C", true);
        graph.addEdge("BC", "B", "C", true);

        assertThrows(Exception.class, () -> Hierholzer.findEulerGraphs(graph));
    }

    @Test
    public void throwExceptionWhenGraphHasNodesWithUnevenDegrees() {
        SingleGraph graph = new SingleGraph("foo");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("BD", "B", "D");
        graph.addEdge("BE", "B", "E");
        graph.addEdge("CE", "C", "E");
        graph.addEdge("DC", "D", "C");
        graph.addEdge("DE", "D", "E");

        assertThrows(Exception.class, () -> Hierholzer.findEulerGraphs(graph));
    }

    @Test
    public void throwExceptionWhenGraphIsNotCoherent() {
        SingleGraph graph = new SingleGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("DE", "D", "E");
        graph.addEdge("DF", "D", "F");
        graph.addEdge("EF", "E", "F");

        assertThrows(Exception.class, () -> Hierholzer.findEulerGraphs(graph));
    }

    @Test
    public void throwExceptionWhenGraphHasMoreNodesThanEdges() {
        SingleGraph graph = new SingleGraph("foo");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");

        assertThrows(Exception.class, () -> Hierholzer.findEulerGraphs(graph));
    }

    @Test
    public void succeedWhenGraphIsSingleNode() {
        SingleGraph graph = new SingleGraph("foo");

        graph.addNode("A");

        List<Graph> result = Hierholzer.findEulerGraphs(graph);
        assertEquals(0, result.size());
    }

    @Test
    public void succeedWhenGraphHasFewerNodesThanEdges() {
        SingleGraph graph = new SingleGraph("foo");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CD", "C", "D");
        graph.addEdge("CE", "C", "E");
        graph.addEdge("DE", "D", "E");

        SingleGraph eulerGraph1 = new SingleGraph("euler1");
        eulerGraph1.addNode("A");
        eulerGraph1.addNode("B");
        eulerGraph1.addNode("C");
        eulerGraph1.addEdge("AB", "A", "B");
        eulerGraph1.addEdge("AC", "A", "C");
        eulerGraph1.addEdge("BC", "B", "C");

        SingleGraph eulerGraph2 = new SingleGraph("euler1");
        eulerGraph2.addNode("C");
        eulerGraph2.addNode("D");
        eulerGraph2.addNode("E");
        eulerGraph2.addEdge("CD", "C", "D");
        eulerGraph2.addEdge("CE", "C", "E");
        eulerGraph2.addEdge("DE", "D", "E");

        List<Graph> shouldResult = Arrays.asList(eulerGraph1, eulerGraph2);
        List<Graph> actualResult = Hierholzer.findEulerGraphs(graph);

        assertTrue(graphsAreEqual(shouldResult, actualResult));
    }

    @Test
    public void succeedWhenGraphHasLoops() {
        SingleGraph graph = new SingleGraph("foo");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AA", "A", "A");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("BC", "B", "C");

        SingleGraph eulerGraph1 = new SingleGraph("euler1");
        eulerGraph1.addNode("A");
        eulerGraph1.addEdge("AA", "A", "A");

        SingleGraph eulerGraph2 = new SingleGraph("euler1");
        eulerGraph2.addNode("A");
        eulerGraph2.addNode("B");
        eulerGraph2.addNode("C");
        eulerGraph2.addEdge("AB", "A", "B");
        eulerGraph2.addEdge("AC", "A", "C");
        eulerGraph2.addEdge("BC", "B", "C");

        List<Graph> shouldResult = Arrays.asList(eulerGraph1, eulerGraph2);
        List<Graph> actualResult = Hierholzer.findEulerGraphs(graph);

        assertTrue(graphsAreEqual(shouldResult, actualResult));
    }

    @Test
    public void succeedWithEmptyGraph() {
        MultiGraph graph = new MultiGraph("foo");

        assertTrue(Hierholzer.findEulerGraphs(graph).isEmpty());
    }

    @Test
    public void succeedWithMultigraphWhenGraphHasTwoNodesWithMultiedge() {
        MultiGraph graph = new MultiGraph("foo");
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("AB1", "A", "B");
        graph.addEdge("AB2", "A", "B");

        List<Graph> shouldResult = new ArrayList<>();

        MultiGraph eulerGraph = new MultiGraph("euler1");
        eulerGraph.addNode("A");
        eulerGraph.addNode("B");
        eulerGraph.addEdge("AB1", "A", "B");
        eulerGraph.addEdge("AB2", "A", "B");

        shouldResult.add(eulerGraph);

        List<Graph> actualResult = Hierholzer.findEulerGraphs(graph);

        assertTrue(graphsAreEqual(shouldResult, actualResult));
    }

    private static boolean graphsAreEqual(List<Graph> shouldResult, List<Graph> actualResult) {
        if(shouldResult.size() != actualResult.size()) {
            return false;
        }

        List<Edge> allShouldEdges = new ArrayList<>();
        List<Edge> allActualEdges = new ArrayList<>();

        shouldResult.forEach(graph ->
                allShouldEdges.addAll(graph.edges().collect(Collectors.toList()))
        );

        actualResult.forEach(graph ->
                allActualEdges.addAll(graph.edges().collect(Collectors.toList()))
        );

        // no duplicated edges
        if(allShouldEdges.size() != new HashSet<>(allShouldEdges).size()) {
            return false;
        }
        if(allActualEdges.size() != new HashSet<>(allActualEdges).size()) {
            return false;
        }

        return checkIfContainingSameEdges(allShouldEdges, allActualEdges);
    }

    private static boolean checkIfContainingSameEdges(List<Edge> shouldEdges, List<Edge> actualEdges) {
        List<String> shouldEdgeIds = shouldEdges.stream()
                .map(Element::getId)
                .collect(Collectors.toList());
        List<String> actualEdgeIds = actualEdges.stream()
                .map(Element::getId)
                .collect(Collectors.toList());

        return shouldEdgeIds.containsAll(actualEdgeIds) && actualEdgeIds.containsAll(shouldEdgeIds);
    }

}
