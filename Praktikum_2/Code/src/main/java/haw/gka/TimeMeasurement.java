package haw.gka;

import haw.gka.kruskal.Kruskal;
import haw.gka.kruskal.KruskalResult;
import org.graphstream.algorithm.Prim;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;

public class TimeMeasurement {
    public static void main(String[] args) throws IOException {

        MultiGraph graph = GraphGenerator.generateGraph(100000,50000,100, false);
        long start = System.currentTimeMillis();
        KruskalResult result = Kruskal.createMinimalSpanningForrest(graph);
        long finish = System.currentTimeMillis();
        float timeElapsed = ((float) (finish - start) /1000);

        System.out.println("Time elapsed with kruskal: " + timeElapsed);

        Prim graphStreamPrim = new Prim();
        start = System.currentTimeMillis();
        graphStreamPrim.init(graph);
        graphStreamPrim.compute();
        graphStreamPrim.getTreeWeight();
        finish = System.currentTimeMillis();
        timeElapsed = ((float) (finish - start) /1000);

        System.out.println("Time elapsed with prim: " + timeElapsed);
    }
}
