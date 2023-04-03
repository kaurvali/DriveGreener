package ee.ut.cs.drivegreener.vehicle.dto;

import ee.ut.cs.drivegreener.vehicle.type.FillupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FillupStatisticsDTO {
    private long id;
    private FillupType type;
    private Date time;
    private int odometer;
    private int trip;
    private String fuelType;
    private double fuelConsumed;
    private double fuelConsumption;
    private double totalCost;
}
