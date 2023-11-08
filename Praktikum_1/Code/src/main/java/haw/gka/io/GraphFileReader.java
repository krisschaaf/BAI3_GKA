package haw.gka.io;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphFileReader {

	private boolean isDirected;

	public Graph getGraphFromFile(String path) throws Exception{
		Graph newGraph;

		// Überprüfe den Dateinamen
		if(!Verifier.isValidFilename(path)) {
			throw new IOException("Invalid File: " + path);
		}
		// Lade den Lese Buffer
		StringBuffer fileContent = new StringBuffer();
		try {

			File graphFile = new File(path);
			FileInputStream fis = new FileInputStream(graphFile);
			// Korrektes encoding für Umlaute
			InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
			BufferedReader reader = new BufferedReader(isr);

			String line;
			// Lese jede Zeile von der Datei
			while((line = reader.readLine())!=null) {
				fileContent.append(line.replace(" ", ""));
				fileContent.append("\n");
			}
			// Schließe den Lesebuffer
			reader.close();
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		// Zerlegung des Dateiinhaltes in seine Zeilen
		String[] lines = fileContent.toString().split("\n");
		String firstLine = lines[0];

		// Prüfe erste Zeile auf Korrektheit
		if(!Verifier.isValidFirstLine(firstLine)) {
			throw new Exception("Invalid File Header: " + firstLine);
		}
		// Erkenne ob der Graph gerichtet ist
		this.isDirected = Verifier.isDirected(firstLine);

		// Instanziiere den neuen Graph
		newGraph = new MultiGraph(firstLine.split(":")[1].replace(";", ""));

		// Iteriere über die Zeilen
		for(int i = 1; i< lines.length; i++) {
			String currentLine = lines[i];
			// Prüfe jede Zeile auf Korrektheit
			if(!Verifier.isValidLine(currentLine)) {
				throw new Exception("Invalid Line: " + currentLine);
			}
			// Füge die Eigenschaften der aktuellen Zeile dem neuen Graphen hinzu
			newGraph = addProperties(newGraph, currentLine);
		}
		// Gebe den neuen Graphen zurück
		return newGraph;
	}


	private Graph addProperties(Graph graph, String line) {

		// Lösche das Semikolon aus Zeile, da semantisch irrelevant
		line = line.replace(";", "");

		// Erstelle Pattern für das Kantengewicht
		Pattern weightPattern = Pattern.compile("::[0-9]+");
		Matcher m = weightPattern.matcher(line);
		String weight = "";
		if (m.find()) {
			// Setze "weight" wenn ein Gewicht gefunden wurde
			weight = m.group().replaceAll("::", "");
		}
		// Lösche Kantengewicht aus der Zeile
		line = line.replaceAll("::[0-9]+", "");

		// Erstelle Pattern für den Kantennamen
		Pattern edgeNamePattern = Pattern.compile("\\(([a-zA-Z0-9]+)");
		m = edgeNamePattern.matcher(line);
		String edgeName = "";
		if (m.find()) {
			// Setze "edgeName" falls ein expliziter Kantenname gefunden wurde
			edgeName = (m.group()).replaceAll("[\\(\\)]", "");
		}
		// Lösche expliziten Kantennamen aus der Zeile
		line = line.replaceAll("\\([a-zA-Z0-9]+\\)", "");

		// Teile übrig gebliebene Knotenbezeichnungen am Bindestrich
		String[] parts = line.split("-");
		String firstNode = parts[0];

		// Teile ersten Knoten am Doppelpunkt für das Knotenattribut
		String firstNodeName = firstNode.split(":")[0];
		if (graph.getNode(firstNodeName)==null) {
			// Wenn Knoten noch nicht vorhanden, füge ihn dem Graph hinzu
			graph.addNode(firstNodeName).setAttribute("ui.label",firstNodeName);
		}

		if (firstNode.split(":").length>1) {
			// Wenn Attribut zum ersten Knoten angegeben, füge es dem Knoten hinzu
			graph.getNode(firstNodeName).setAttribute("attr", firstNode.split(":")[1]);
		}

		// Wenn zweiter Knoten angegeben:
		if(parts.length>1) {
			String secondNode = parts[1];
			// Teile zweiten Knoten am Doppelpunkt für das Knotenattribut
			String secondNodeName = secondNode.split(":")[0];
			if (graph.getNode(secondNodeName)==null) {
				// Wenn Knoten noch nicht vorhanden, füge ihn dem Graph hinzu
				graph.addNode(secondNodeName).setAttribute("ui.label",secondNodeName);
			}
			if (secondNode.split(":").length>1) {
				// Wenn Attribut zum zweiten Knoten angegeben, füge es dem Knoten hinzu
				graph.getNode(secondNodeName).setAttribute("attr", secondNode.split(":")[1]);
			}
			if(edgeName.isEmpty()) {
				// Wenn kein expliziter Kantenname angegeben, erstelle Id anhand der Quell- und Zielknote
				edgeName = firstNodeName+"-"+secondNodeName;
				int counter = 1;
				while (graph.getEdge(edgeName) != null){
					// Wenn unexpliziter Kantenname schon vorhanden, füge Autoinkrement der Id hinzu
					edgeName = edgeName + "_" + String.valueOf(counter);
					counter++;
				}
			}
			// Füge Kante hinzu
			graph.addEdge(edgeName, firstNodeName, secondNodeName, this.isDirected).setAttribute("ui.label", edgeName);
			if (!weight.isEmpty()) {
				// Wenn Gewicht angegeben, füge es als Attribut der Kante hinzu
				graph.getEdge(edgeName).setAttribute("weight", weight);
				graph.getEdge(edgeName).setAttribute("ui.label", edgeName +"::"+weight);
			} else {
				// Ansonsten setze an jeder Kante das gleiche Gewicht
				graph.getEdge(edgeName).setAttribute("weight", 1);
			}
		}
		return graph;	}
}

