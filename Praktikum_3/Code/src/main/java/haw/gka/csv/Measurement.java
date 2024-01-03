package haw.gka.csv;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Measurement {
    private String teamName;
    private GraphType graphType;
    private int edgeCount;
    private String hardware;
    private String measurementTool;
    private String[][] data;

    public String[][] buildMetadata() {
        return new String[][]{
                {"TeamName", this.getTeamName()},
                {"GraphType", this.getGraphType().getValue()},
                {"EdgeCount", String.valueOf(this.getEdgeCount())},
                {"Hardware", this.getHardware()},
                {"MeasurementTool", this.getMeasurementTool()}
        };
    }
}
