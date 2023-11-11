package haw.gka;

import haw.gka.dijkstra.Dijkstra;
import haw.gka.dijkstra.DijkstraRecursive;
import haw.gka.dijkstra.models.PriorityQueueItem;
import haw.gka.exceptions.*;
import haw.gka.io.GraphFileReader;
import haw.gka.io.GraphFileWriter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui", "swing");
		String inputFile;
		String outputFile;
		String dijkstraAlg;
		String startNode;
		String endNode;
		try {
			checkArgs(args);
			inputFile = args[0];
			outputFile = args[1];
			dijkstraAlg = args[2];
			startNode = args[3];
			endNode = args[4];
		} catch (Exception e) {
			e.printStackTrace();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter path/input file:");
			inputFile = scanner.nextLine();
			System.out.println("Enter path/output file:");
			outputFile = scanner.nextLine();
			System.out.println("Enter dijkstra [iterative, recursive]:");
			dijkstraAlg = scanner.nextLine();
			System.out.println("Enter startNode:");
			startNode = scanner.nextLine();
			System.out.println("Enter endNode:");
			endNode = scanner.nextLine();
		}

		try {
			GraphFileReader reader = new GraphFileReader();

			Graph graph = reader.getGraphFromFile(inputFile);
			graph.display(true);

			PriorityQueueItem result = null;

			switch (dijkstraAlg) {
				case "iterative":
					result = Dijkstra.calculateFastestPath((MultiNode) graph.getNode(startNode), (MultiNode) graph.getNode(endNode), (MultiGraph) graph);
					break;
				case "recursive":
					result = DijkstraRecursive.calculateFastestPathRecurs((MultiNode) graph.getNode(startNode), (MultiNode) graph.getNode(endNode), (MultiGraph) graph);
			}

			if(!result.getNodes().isEmpty()) {
				System.out.println(result);
				result.markUp();
			} else {
				throw new NoResultFoundException("Could not find any result for given parameters.");
			}

			GraphFileWriter writer = new GraphFileWriter();
			writer.writeFile(graph, outputFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void checkArgs(String[] args) throws FileArgMissingException, DijkstraAlgArgMissingException, NodeArgMissingException, ArgsMissingException {
		if(args.length != 5) {
			throw new ArgsMissingException("There have to be five parameters in following order provided: inputFile, outputFile, dijkstraAlg (iterative/recursive), startNode, endNode.");
		}
		if(args[0].isEmpty()) {
			throw new FileArgMissingException("Please provide input file first parameters.");
		}
		if(args[1].isEmpty()) {
			throw new FileArgMissingException("Please provide output file as second parameters.");
		}
		if(args[2].isEmpty()) {
			throw new DijkstraAlgArgMissingException("Please provide either 'iterative' or 'recursive' as third parameter.");
		}
		// TODO: fix
//		if(args[2].toString() != "iterative" && args[2].toString() != "recursive") {
//			throw new DijkstraAlgArgMissingException("Please provide either 'iterative' or 'recursive' as third parameter.");
//		}
		if(args[3].isEmpty()) {
			throw new NodeArgMissingException("Please provide start node as fourth parameter.");
		}
		if(args[4].isEmpty()) {
			throw new NodeArgMissingException("Please provide end node as fifth parameter.");
		}
	}
}
