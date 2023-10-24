package control;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;;

public class Main {

	public static void main(String[] args) {
		
		Graph graph_0 = new MultiGraph("0");
		graph_0.addNode("N1");
		
		graph_0.addNode("N2");		

		Graph graph = null;
		System.setProperty("org.graphstream.ui", "swing");
		
		GraphFileReader reader = new GraphFileReader();
		try {
			graph = reader.getGraphFromFile("G:\\Code\\Studium\\HAW\\AI3_GKA\\Uebung_01\\Dijstra_Uebung\\graph03.grph");
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
