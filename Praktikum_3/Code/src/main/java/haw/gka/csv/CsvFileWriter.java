package haw.gka.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CsvFileWriter {

    public static void writeMeasurementFile(Measurement measurement, String graphNumber) {
        String fileName = measurement.getTeamName() + "_G" + graphNumber + ".csv";
        String filePath = "src/main/resources/csv-files/" + fileName;

        String[] headers = {"Run", "Time(ms)", "Energy(J)"};

        writeCSV(filePath, measurement.buildMetadata(), headers, measurement.getData());
    }

    private static void writeCSV(String filePath, String[][] metadata, String[] headers, String[][] data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // write metadata
            for (String[] row : metadata) {
                for (String value : row) {
                    writer.append(value);
                    writer.append(",");
                }
                writer.append("\n");
            }
            writer.append("\n");

            // write headers
            for (String header : headers) {
                writer.append(header);
                writer.append(",");
            }
            writer.append("\n");

            // write data
            for (String[] row : data) {
                for (String value : row) {
                    writer.append(value);
                    writer.append(",");
                }
                writer.append("\n");
            }

            System.out.println("CSV-Datei erfolgreich erstellt: " +
                    Arrays.stream(filePath.split("/"))
                            .reduce((first, second) -> second)
                            .orElse(null));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}