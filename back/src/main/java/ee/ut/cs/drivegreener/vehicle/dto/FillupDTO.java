package ee.ut.cs.drivegreener.vehicle.dto;

import ee.ut.cs.drivegreener.vehicle.type.*;
import lombok.Data;

import java.util.Date;

@Data
public class FillupDTO {
    private Date time;
    private int trip;
    private int odometer;
    private int fuelAmount;
    private FillupType fillupType;
    private FuelType fuelType;
    private double price;
    private PriceType priceType;
    private String notes;
    private TireType tires;
    private DrivingStyleType drivingStyle;
    private LoadType load;
    private int cityDriving;
    private long vehicleId;
}
