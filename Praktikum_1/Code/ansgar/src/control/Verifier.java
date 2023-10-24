package control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier {
	static boolean isValidLine(String line) {
		String umlaute = "\\u00fc";
		String allCharactersRegex="[a-zA-Z0-9"+umlaute+"]";
		Matcher m;

		Pattern nodePattern = Pattern.compile(allCharactersRegex+"+(:[0-9]+)?");
		m = nodePattern.matcher(line);
		long results = m.results().count();
		if (results  == 1) {
			line = line.replaceAll(allCharactersRegex+"+(:[0-9]+)?", "");
			return line.equals(";");
		} else if (results > 1) {
			line = line.replaceAll(allCharactersRegex+"+(:[0-9]+)?-", "-");
		}
		nodePattern = Pattern.compile("-"+allCharactersRegex+"+(:[0-9]+)?");
		m = nodePattern.matcher(line);
		if (m.results().count()  == 1) {
			line = line.replaceAll("-"+allCharactersRegex+"+(:[0-9]+)?", "");
		}
		Pattern edgeNamePattern = Pattern.compile("\\"+ allCharactersRegex+"\\)");
		m = edgeNamePattern.matcher(line);
		if (m.results().count() == 1) {
			line = line.replaceAll("\\"+ allCharactersRegex+"\\)", "");
		}
		Pattern weightPattern = Pattern.compile("::[0-9]+");
		m = weightPattern.matcher(line);
		if (m.results().count() == 1) {
			line = line.replaceAll("::[0-9]+", "");
		}

		return line.equals(";");
		
	}
	
	static boolean isValidFirstLine(String firstLine) {
		Pattern filename = Pattern.compile("#(directed|undirected):[a-zA-Z0-9]+;(\\n)?", Pattern.CASE_INSENSITIVE);
		Matcher m = filename.matcher(firstLine);
		return m.find();
	}
	
	static boolean isValidFilename(String path) {
		Pattern filename = Pattern.compile("(.)+\\.grph", Pattern.CASE_INSENSITIVE);
		Matcher m = filename.matcher(path);
		return m.find();
	}
	
	
	static boolean isDirected(String firstLine) throws Exception{
		String graphDirection = firstLine.split(":")[0];
		switch (graphDirection) {
			case "#directed":
				return true;
			case "#undirected":
				return false;	
			default:
				throw new Exception("Invalid File Header: " + firstLine);
		}	
		
	}
}
