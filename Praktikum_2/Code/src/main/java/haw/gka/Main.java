package haw.gka;

import haw.gka.io.GraphFileWriter;
import haw.gka.kruskal.Kruskal;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {

		MultiGraph graph = GraphGenerator.generateGraph(200,2000,10, false);
		MultiGraph result = Kruskal.createMinimalSpanningForrest(graph);
		GraphFileWriter.writeFile(result, "src/main/resources/graphs/lastCreatedGraph");

		Integer weightSum = calculateWeightSum(result);
		System.out.printf("The sum of all edge weights of the minimal spanning forrest: %s.", weightSum);

		System.setProperty("org.graphstream.ui", "swing");
		result.display();
	}

	private static Integer calculateWeightSum(MultiGraph graph) {
		return graph.edges()
				.map(edge ->  edge.getAttribute("weight", Integer.class))
				.reduce((a,b) -> a + b)
				.orElseThrow(() -> new RuntimeException("Edge weight accumulation failed!"));
	}
}
