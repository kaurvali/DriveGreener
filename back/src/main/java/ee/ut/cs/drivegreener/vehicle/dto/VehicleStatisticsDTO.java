package ee.ut.cs.drivegreener.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleStatisticsDTO {
    private double fuelConsumed;
    private int distance;
    private double fuelConsumption;
    private double totalCost;
    private double co;
    private int lastOdometer;
    private Date lastFilling;
    private boolean firstDone;
}
