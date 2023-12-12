package haw.gka.kruskal;

import haw.gka.GraphGenerator;
import haw.gka.io.GraphFileWriter;
import org.graphstream.algorithm.Prim;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KruskalTestRandomGraph {

    @Test
    public void succeedKruskalFewerNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(1000, 10000, 100, false);

        KruskalResult ourKruskal = Kruskal.createMinimalSpanningForrest(graph);

        org.graphstream.algorithm.Kruskal graphStreamKruskal = new org.graphstream.algorithm.Kruskal();
        graphStreamKruskal.init(graph);
        graphStreamKruskal.compute();

        assertEquals((int) graphStreamKruskal.getTreeWeight(), ourKruskal.getTreeWeight());
    }

    @Test
    public void succeedPrimFewerNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(1000, 10000, 100, false);

        KruskalResult ourKruskal = Kruskal.createMinimalSpanningForrest(graph);

		Prim graphStreamPrim = new Prim();
		graphStreamPrim.init(graph);
		graphStreamPrim.compute();

        assertEquals((int) graphStreamPrim.getTreeWeight(), ourKruskal.getTreeWeight());
    }

    @Test
    public void succeedKruskalSameNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(10000, 10000, 100, false);

        KruskalResult ourKruskal = Kruskal.createMinimalSpanningForrest(graph);

        org.graphstream.algorithm.Kruskal graphStreamKruskal = new org.graphstream.algorithm.Kruskal();
        graphStreamKruskal.init(graph);
        graphStreamKruskal.compute();

        assertEquals((int) graphStreamKruskal.getTreeWeight(), ourKruskal.getTreeWeight());
    }

    @Test
    public void succeedPrimSameNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(10000, 10000, 100, false);

        KruskalResult ourKruskal = Kruskal.createMinimalSpanningForrest(graph);

        Prim graphStreamPrim = new Prim();
        graphStreamPrim.init(graph);
        graphStreamPrim.compute();

        assertEquals((int) graphStreamPrim.getTreeWeight(), ourKruskal.getTreeWeight());
    }

    @Test
    public void succeedKruskalMoreNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(50000, 5000, 100, false);

        KruskalResult ourKruskal = Kruskal.createMinimalSpanningForrest(graph);

        org.graphstream.algorithm.Kruskal graphStreamKruskal = new org.graphstream.algorithm.Kruskal();
        graphStreamKruskal.init(graph);
        graphStreamKruskal.compute();

        assertEquals((int) graphStreamKruskal.getTreeWeight(), ourKruskal.getTreeWeight());
    }

    @Test
    public void succeedPrimMoreNodesThanEdges() {
        MultiGraph graph = GraphGenerator.generateGraph(50000, 5000, 100, false);

        KruskalResult ourKruskal = Kruskal.createMinimalSpanningForrest(graph);

        Prim graphStreamPrim = new Prim();
        graphStreamPrim.init(graph);
        graphStreamPrim.compute();

        assertEquals((int) graphStreamPrim.getTreeWeight(), ourKruskal.getTreeWeight());
    }
}
