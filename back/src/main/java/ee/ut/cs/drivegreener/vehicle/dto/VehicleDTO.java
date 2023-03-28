package ee.ut.cs.drivegreener.vehicle.dto;

import ee.ut.cs.drivegreener.vehicle.type.DrivetrainType;
import ee.ut.cs.drivegreener.vehicle.type.TransmissionType;
import ee.ut.cs.drivegreener.vehicle.type.VehicleType;
import lombok.Data;

@Data
public class VehicleDTO {
    private String make;
    private String model;
    private String trim;
    private Integer year;
    private String engine;
    private int power;
    private TransmissionType transmission;
    private DrivetrainType drivetrain;
    private VehicleType vehicleType;
    private Boolean isPublic;
    private long userId;
}