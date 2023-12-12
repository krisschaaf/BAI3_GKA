package haw.gka;

import haw.gka.io.GraphFileWriter;
import haw.gka.kruskal.Kruskal;
import haw.gka.kruskal.KruskalResult;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {

		MultiGraph graph = GraphGenerator.generateGraph(200,2000,100, false);
		KruskalResult result = Kruskal.createMinimalSpanningForrest(graph);

		String filename = "src/main/resources/graphs/lastCreatedGraph";
		GraphFileWriter.writeFile(result.getGraph(), filename);
		System.out.printf("You can find the generated graph at: '%s'.%n%n", filename);

		System.out.printf("The sum of all edge weights of the minimal spanning forrest: %s.%n", result.getTreeWeight());

		System.setProperty("org.graphstream.ui", "swing");
		result.getGraph().display();
	}
}
