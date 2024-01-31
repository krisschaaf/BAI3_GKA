package haw.gka;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HierholzerAufg1BTest {

//    @Test
//    public void graphGeneratorThrowsExceptionOnLoop() {
//        assertThrows(IllegalArgumentException.class, () -> GraphGenerator.createEulerGraph(1, 1, "foo"));
//    }

    @Test
    public void graphGeneratorThrowsExceptionWhenLessEdgesThanNodes() {
        assertThrows(IllegalArgumentException.class, () -> GraphGenerator.createEulerGraph(2, 1, "foo"));
    }

    @Test
    public void graphGeneratorThrowsExceptionWhenSingleNodeCreated() {
        assertThrows(IllegalArgumentException.class, () -> GraphGenerator.createEulerGraph(1,0, "foo"));
    }

//    @Test
//    public void graphGenerator() {
//        Graph graph = GraphGenerator.createEulerGraph(100,10000, "foo");
//        System.out.println("Edgecount: " + graph.edges().count());
//
//        List<Graph> eulerianCircles = Hierholzer.findEulerGraphs(graph);
//
//        System.out.println("EulerianCirclesCount: " + eulerianCircles.size());
//    }
}
