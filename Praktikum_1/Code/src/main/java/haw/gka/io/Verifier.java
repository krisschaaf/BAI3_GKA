package haw.gka.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier {

	private static final String UMLAUTE = "ÜüÄäÖöß";
	private static final String ALL_CHARS_REGEX = "[a-zA-Z0-9" + UMLAUTE + "]";

	public static boolean isValidLine(String line) {

		int countOfPatternInLine = getCountOfPatternInLine(line, ALL_CHARS_REGEX+"+(:[0-9]+)?");

		if (countOfPatternInLine == 1) {
			line = line.replaceAll(ALL_CHARS_REGEX+"+(:[0-9]+)?", "");
			// Wenn nur ein Knoten angegeben, darf nach dessen Löschung nur ein Semikolon übrig bleiben
			return line.equals(";");
		} else if (countOfPatternInLine > 1) {
			// Lösche ersten Knoten aus der Zeile
			line = line.replaceAll(ALL_CHARS_REGEX+"+(:[0-9]+)?-", "-");
		}

		if (getCountOfPatternInLine(line, "-"+ALL_CHARS_REGEX+"+(:[0-9]+)?") == 1) {
			// Wenn nur ein zweiter Knoten angegeben, lösche diesen aus der Zeile
			line = line.replaceAll("-"+ALL_CHARS_REGEX+"+(:[0-9]+)?", "");
		}

		if (getCountOfPatternInLine(line, "\\("+ ALL_CHARS_REGEX+"\\)") == 1) {
			// Lösche den Kantennamen aus der Zeile
			line = line.replaceAll("\\("+ ALL_CHARS_REGEX+"\\)", "");
		}

		if (getCountOfPatternInLine(line, "::[0-9]+") == 1) {
			// Lösche das Kantengewicht aus der Zeile
			line = line.replaceAll("::[0-9]+", "");
		}
		// Es darf nur das Semikolon übrig bleiben (Mehrfach angebene Kantennamen,-gewichte und mehr als zwei Knoten sind illegal)
		return line.equals(";");
		
	}

	// Zählt die Treffer des übergebenen Regexausdruckes in der Zeile
	private static int getCountOfPatternInLine(String line, String regex) {
		Matcher m;
		Pattern nodePattern = Pattern.compile(regex);
		m = nodePattern.matcher(line);
		int results = 0;
		while (m.find()) {
			results++;
		}
		return results;
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
