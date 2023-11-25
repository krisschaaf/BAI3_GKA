package haw.gka;

import haw.gka.io.GraphGenerator;
import org.graphstream.graph.Graph;


public class Main {
	public static void main(String[] args) {

		 GraphGenerator graphGenerator = new GraphGenerator();
		 for (int i = 0; i < 1000; i++) {
			 graphGenerator.generateGraph(40,70,10, false);
			 System.out.println(i);
		 }


		System.setProperty("org.graphstream.ui", "swing");
	}
}
