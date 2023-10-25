package kris.schaaf;

import kris.schaaf.io.GraphFileReader;
import kris.schaaf.io.GraphFileWriter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.IOException;

;

public class Main {

	public static void main(String[] args) {
		
		Graph graph_0 = new MultiGraph("0");
		graph_0.addNode("N1");
		
		graph_0.addNode("N2");		

		Graph graph = null;
		System.setProperty("org.graphstream.ui", "swing");
		
		GraphFileReader reader = new GraphFileReader();
		try {
			graph = reader.getGraphFromFile(args[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graph.display(true);
		GraphFileWriter writer = new GraphFileWriter();
		try {
			writer.writeFile(graph, "test2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
