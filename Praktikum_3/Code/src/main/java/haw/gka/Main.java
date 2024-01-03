package haw.gka;

public class Main {
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui", "swing");

		String[][] data = {
				{"1", "100", "50"},
				{"2", "105", "52"},
				{"3", "98", "48"},
				{"4", "110", "55"},
				{"5", "112", "58"},
				{"6", "105", "52"},
				{"7", "108", "54"},
				{"8", "115", "60"},
				{"9", "120", "62"},
				{"10", "118", "59"}
		};

		Measurement measurement = new Measurement(
				"TeamC",
				GraphType.SINGLE_GRAPH,
				100,
				"Hardware",
				"Tool",
				data
		);

		CsvFileWriter.writeMeasurementFile(measurement, "2");
	}
}
