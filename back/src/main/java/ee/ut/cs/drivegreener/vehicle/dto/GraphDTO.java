package ee.ut.cs.drivegreener.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphDTO {
    private long vehicleID;
    private List<Date> time;
    private List<Integer> odometer;
    private List<String> category;
    private List<Double> valueDouble;
    private String description;
    private String xUnit;
    private String yUnit;
    private String xLabel;
    private String yLabel;
}
