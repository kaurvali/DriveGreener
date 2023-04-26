package ee.ut.cs.drivegreener.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticsDTO  {
    private int distance;
    private double fuelConsumed;
    private double energyConsumed;
    private double fuelConsumption;
    private double energyConsumption;
    private double totalCost;
    private double hundredCost;
    private double co;
}
