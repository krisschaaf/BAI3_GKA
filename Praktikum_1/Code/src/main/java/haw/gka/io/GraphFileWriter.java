package haw.gka.io;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GraphFileWriter {

	public void writeFile(Graph graph, String filename) throws IOException {

		String output = "";
		boolean isDirected = false;

		// Iteriere über die Kanten des Graphs
		for(int i = 0; i<graph.getEdgeCount();i++) {
			Edge edge = graph.getEdge(i);
			Node source = edge.getNode0();
			Node target= edge.getNode1();

			if (edge.isDirected()) {
				// Graph ist gerichtet sobald eine gerichtete Kante existiert
				isDirected = true;
			}

			if(source.getAttribute("attr")!=null) {
				// Schreibe Quellknoten mit Attribut
				output += source.getId()+":"+source.getAttribute("attr")+"-";
			}
			else {
				// Schreibe Quellknoten ohne Attribut
				output += source.getId()+"-";
			}
			if (target.getAttribute("attr")!=null) {
				// Schreibe Zielknoten mit Attribut
				output += target.getId() + ":" + target.getAttribute("attr");
			}
			else {
				// Schreibe Zielknoten ohne Attribut
				output += target.getId();
			}
			if (!edge.getId().contains("-")) {
				// Schreibe Kantennamen
				output += "("+edge.getId()+")";
			} if (edge.getAttribute("weight")!=null) {
				// Schreibe Kantengewicht
				output += "::" + edge.getAttribute("weight");
			}
			output += ";\n";
		}
		// Iteriere über kantenlose Knoten
		for(int i = 0; i<graph.getNodeCount() && graph.getNode(i).getEdge(0) == null;i++) {
			Node actualNode = graph.getNode(i);
			if(actualNode.getAttribute("attr") != null){
				// Schreibe kantenlosen Knoten mit Attribut
				output += actualNode.getId() + ":" + actualNode.getAttribute("attr") + "\n";
			} else {
				// Schreibe kantenlosen Knoten ohne Attribut
				output += actualNode.getId() + "\n";
			}
		}
		// Schreibe Kopfzeile
		if(isDirected) {
			output = "#directed:"+graph.getId()+";\n" +output;
		} else {
			output = "#undirected:"+graph.getId()+";\n" +output;
		}
		if (!Verifier.isValidFilename(filename)) {
			// Füge Dateiendung hinzu falls nicht vorhanden
			filename += ".grph";
		}
		// Lade Schreibbuffer
		try {
			// Encodiere für Umlaute
			byte[] iso88591Data = output.getBytes("ISO-8859-1");
			File outputFile = new File(filename);
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(iso88591Data);
			outputStream.close();
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
}