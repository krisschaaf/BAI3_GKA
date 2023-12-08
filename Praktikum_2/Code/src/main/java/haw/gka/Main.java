package haw.gka;

import haw.gka.io.GraphGenerator;
import haw.gka.kruskal.Kruskal;
import org.graphstream.graph.implementations.MultiGraph;


public class Main {
	public static void main(String[] args) {

		MultiGraph graph = GraphGenerator.generateGraph(10,20,10, false);
		Kruskal.createMinimalSpanningForrest(graph);
//		 for (int i = 0; i < 1000; i++) {
//			 graphGenerator.generateGraph(40,70,10, false);
//			 System.out.println(i);
//		 }


		System.setProperty("org.graphstream.ui", "swing");
	}
}
