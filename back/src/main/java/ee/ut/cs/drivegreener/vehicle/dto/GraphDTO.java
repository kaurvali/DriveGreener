package ee.ut.cs.drivegreener.vehicle.dto;

import ee.ut.cs.drivegreener.vehicle.type.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraphDTO {
    private long vehicleID;
    private VehicleType vehicleType;
    private String description;
    private Date time;
    private int odometer;
    private double valueDouble;
    private int valueInt;
}
