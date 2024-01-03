package haw.gka;

import haw.gka.csv.CsvFileWriter;
import haw.gka.csv.GraphType;
import haw.gka.csv.Measurement;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Arrays;
import java.util.List;

public class Main {
	private static final String TEAM_NAME = "TeamC";
	private static final String HARDWARE = "Hardware"; // TODO
	private static final String TOOL = "Tool"; // TODO

	
	public static void main(String[] args) {
		List<Graph> graphs = getGraphList();

		for (Graph graph: graphs) {
			String[][] data = new String[10][3];

			for (int i = 1; i <= 13; i++) {
				long start = System.currentTimeMillis();

				Hierholzer.findEulerGraphs(graph);

				long finish = System.currentTimeMillis();
				long timeElapsed = finish - start;

				if (i >= 4) {
					data[i-4][0] = String.valueOf(i-3);
					data[i-4][1] = String.valueOf(timeElapsed);
					data[i-4][2] = ""; // TODO
				}
			}

			Measurement measurement = new Measurement(
					TEAM_NAME,
					getGraphType(graph),
					graph.getEdgeCount(),
					HARDWARE,
					TOOL,
					data
			);

			CsvFileWriter.writeMeasurementFile(measurement, graph.getId());
		}
	}

	private static List<Graph> getGraphList() {
		Graph eulerGraph1 = GraphGenerator.createEulerGraph(100, 3000, "01");
		Graph eulerGraph2 = GraphGenerator.createEulerGraph(1000, 400000, "02");
		Graph eulerGraph3 = GraphGenerator.createEulerGraph(10000, 1000000, "03");

        return Arrays.asList(eulerGraph1, eulerGraph2, eulerGraph3);
	}

	private static GraphType getGraphType(Graph graph) {
		GraphType graphType;

		if (graph instanceof SingleGraph) {
			graphType = GraphType.SINGLE_GRAPH;
		} else if (graph instanceof MultiGraph) {
			graphType = GraphType.MULTI_GRAPH;
		} else {
			throw new RuntimeException("Illegal Graph Type");
		}
		return graphType;
	}
}
