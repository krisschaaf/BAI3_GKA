package kris.schaaf.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class GraphFileWriter {
	
	public void writeFile(Graph graph, String filename) throws IOException {
		
		String output = "";
		boolean isDirected = false;
		
		for(int i = 0; i<graph.getEdgeCount();i++) {
			Edge edge = graph.getEdge(i);
			Node source = edge.getNode0();
			Node target= edge.getNode1();
			if (edge.isDirected()) {
				isDirected = true;
			}
			
			if(source.getAttribute("attr")!=null) {
				output += source.getId()+":"+source.getAttribute("attr")+"-";
			}
			else {
				output += source.getId()+"-";
			}
			if (target.getAttribute("attr")!=null) {
				output += target.getId() + ":" + target.getAttribute("attr");
			}
			else {
				output += target.getId();
			}
			if (!edge.getId().contains("-")) {
				output += "("+edge.getId()+")";
			} if (edge.getAttribute("weight")!=null) {
				output += "::" + edge.getAttribute("weight");
			}
			output += ";\n";
		
		}
		if(isDirected) {
			output = "#directed:"+graph.getId()+";\n" +output;
		} else {
			output = "#undirected:"+graph.getId()+";\n" +output;
		}
		
		
		if (!Verifier.isValidFilename(filename)) {
			filename += ".grph";
		}
		
	    try {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(output);
			writer.close();
		} catch (IOException e) {
			throw new IOException(e);
		}	
	}


}
