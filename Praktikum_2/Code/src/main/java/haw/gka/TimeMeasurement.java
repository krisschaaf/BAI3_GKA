package haw.gka;

import org.graphstream.algorithm.Prim;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;

public class TimeMeasurement {
    public static void main(String[] args) throws IOException {

        MultiGraph graph = GraphGenerator.generateGraph(100000,50000,100, false);
        long start = System.currentTimeMillis();
// ...

        //KruskalResult result = Kruskal.createMinimalSpanningForrest(graph);
        Prim graphStreamPrim = new Prim();
        graphStreamPrim.init(graph);
        graphStreamPrim.compute();
        graphStreamPrim.getTreeWeight();
        long finish = System.currentTimeMillis();
        float timeElapsed = ((float) (finish - start) /1000);
        System.out.printf("Time elapsed: " + Float.toString(timeElapsed/60));
        //50000, 50000, 100 -> 2,29 min
        //50000, 100000, 100 -> 3,53 min
        //100000, 50000, 100 -> 5,01 min
        //75000, 75000, 100 -> 4,84 min
        //75000, 75000, 100000 -> 5,01 min
        //50000, 200000, 100 -> 4,23 min
        //50000, 450000, 100 -> 4,88 min
    }
}
