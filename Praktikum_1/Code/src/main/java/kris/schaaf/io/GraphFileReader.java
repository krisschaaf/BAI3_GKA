package kris.schaaf.io;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class GraphFileReader {
	
	private boolean isDirected;
	 //refactor functions
	public Graph getGraphFromFile(String path) throws IOException{
		Graph newGraph;
		
		if(!Verifier.isValidFilename(path)) {
			throw new IOException("Invalid File: " + path);
		}
		
		try {
			File graphFile = new File(path);
			FileInputStream fis = new FileInputStream(graphFile);	
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(isr);
			StringBuffer fileContent = new StringBuffer();
			String line;
			while((line = reader.readLine())!=null) {
				fileContent.append(line.replace(" ", ""));
				fileContent.append("\n");
			}
			/*try {
				List<String> allLines = Files.readAllLines(Paths.get("sample.txt"));

				for (String line : allLines) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			reader.close();
			fis.close();
			String[] lines = fileContent.toString().split("\n");
			String firstLine = lines[0];			
			
			if(!Verifier.isValidFirstLine(firstLine)) {
				throw new Exception("Invalid File Header: " + firstLine);
			}
			this.isDirected = Verifier.isDirected(firstLine);
			
			newGraph = new MultiGraph(firstLine.split(":")[1].replace(";", ""));
			
			for(int i = 1; i< lines.length; i++) {
				String currentLine = lines[i];
				if(!Verifier.isValidLine(currentLine)) {
					throw new Exception("Invalid Line: " + currentLine);
				}
				newGraph = AddProperties(newGraph, currentLine);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		return newGraph;		
	}

	
	private Graph AddProperties(Graph graph, String line) {	
		line = line.replace(";", "");
		Pattern weightPattern = Pattern.compile("::[0-9]+");
		Matcher m = weightPattern.matcher(line);
		String weight = "";
		if (m.find()) {
			weight = m.group().replaceAll("::", "");
		}
		line = line.replaceAll("::[0-9]+", "");
		
		Pattern edgeNamePattern = Pattern.compile("\\(([a-zA-Z0-9]+)");
		m = edgeNamePattern.matcher(line);
		String edgeName = "";
		if (m.find()) {
			edgeName = (m.group()).replaceAll("[\\(\\)]", "");
		}
		line = line.replaceAll("\\([a-zA-Z0-9]+\\)", "");

		String[] parts = line.split("-");
		String firstNode = parts[0];
		String firstNodeName = firstNode.split(":")[0];
		if (graph.getNode(firstNodeName)==null) {
			graph.addNode(firstNodeName).setAttribute("ui.label",firstNodeName);
		}
		
		if (firstNode.split(":").length>1) {
			graph.getNode(firstNodeName).setAttribute("attr", firstNode.split(":")[1]);
		}
		
		if(parts.length>1) {
			String secondNode = parts[1];
			String secondNodeName = secondNode.split(":")[0];
			if (graph.getNode(secondNodeName)==null) {
				graph.addNode(secondNodeName).setAttribute("ui.label",secondNodeName);
			}
			if (secondNode.split(":").length>1) {
				graph.getNode(secondNodeName).setAttribute("attr", secondNode.split(":")[1]);
			}
			if(edgeName.equals("") ) {
				edgeName = firstNodeName+"-"+secondNodeName;
			}
			graph.addEdge(edgeName, firstNodeName, secondNodeName, this.isDirected).setAttribute("ui.label", edgeName);
			if (weight != "") {
				graph.getEdge(edgeName).setAttribute("weight", weight);
				graph.getEdge(edgeName).setAttribute("ui.label", edgeName +"::"+weight);
			}			
		}		
		return graph;	}
	
	
}
