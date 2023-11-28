package haw.gka;

import haw.gka.io.GraphGenerator;
import haw.gka.kruskal.KruskalImpl;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;


public class Main {
	public static void main(String[] args) {

		GraphGenerator graphGenerator = new GraphGenerator();
		KruskalImpl kruskal = new KruskalImpl();
		MultiGraph graph = graphGenerator.generateGraph(10,20,10, false);
		kruskal.createMinimalSpanningForrest(graph);
//		 for (int i = 0; i < 1000; i++) {
//			 graphGenerator.generateGraph(40,70,10, false);
//			 System.out.println(i);
//		 }


		System.setProperty("org.graphstream.ui", "swing");
	}
}
