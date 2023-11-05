package haw.gka;

import haw.gka.dijkstra.Dijkstra;
import haw.gka.dijkstra.models.PriorityQueueItem;
import haw.gka.io.GraphFileReader;
import haw.gka.io.GraphFileWriter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graph.display(true);
		try{
			PriorityQueueItem result = Dijkstra.calculateFastestPath((MultiNode) graph.getNode("Oldenburg"), (MultiNode) graph.getNode("Hamburg"), (MultiGraph) graph);
			result.markUp();
		} catch (Exception e){
			e.printStackTrace();
		}

		GraphFileWriter writer = new GraphFileWriter();
		try {
			writer.writeFile(graph, "test2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
