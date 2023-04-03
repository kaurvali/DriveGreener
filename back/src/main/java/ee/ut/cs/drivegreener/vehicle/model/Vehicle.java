package ee.ut.cs.drivegreener.vehicle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.ut.cs.drivegreener.login.model.Role;
import ee.ut.cs.drivegreener.login.model.User;
import ee.ut.cs.drivegreener.vehicle.dto.FillupDTO;
import ee.ut.cs.drivegreener.vehicle.dto.VehicleDTO;
import ee.ut.cs.drivegreener.vehicle.type.DrivetrainType;
import ee.ut.cs.drivegreener.vehicle.type.TransmissionType;
import ee.ut.cs.drivegreener.vehicle.type.VehicleType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "make")
    @Size(max = 50)
    private String make;

    @NotBlank
    @Column(name = "model")
    @Size(max = 50)
    private String model;

    @Column(name = "trim")
    @Size(max = 50)
    private String trim;

    @Column(name = "year")
    private int year;

    @Column(name = "engine")
    @Size(max = 50)
    private String engine;

    @Column(name = "power")
    private int power;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transmission")
    private TransmissionType transmission;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "drivetrain")
    private DrivetrainType drivetrain;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fuel")
    private VehicleType vehicleType;

    @Column(name = "isPublic")
    private Boolean isPublic;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "id")
    private Set<Fillup> fillups = new HashSet<>();

    public Vehicle() {

    }

    public Vehicle(VehicleDTO vehicleDTO) {
        this.make = vehicleDTO.getMake();
        this.model = vehicleDTO.getModel();
        this.trim = vehicleDTO.getTrim();
        this.year = vehicleDTO.getYear();
        this.engine = vehicleDTO.getEngine();
        this.power = vehicleDTO.getPower();
        this.transmission = vehicleDTO.getTransmission();
        this.drivetrain = vehicleDTO.getDrivetrain();
        this.vehicleType = vehicleDTO.getVehicleType();
        this.isPublic = vehicleDTO.getIsPublic();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    public DrivetrainType getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(DrivetrainType drivetrain) {
        this.drivetrain = drivetrain;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", trim='" + trim + '\'' +
                ", engine='" + engine + '\'' +
                ", power=" + power +
                ", transmission=" + transmission +
                ", drivetrain=" + drivetrain +
                ", vehicleType=" + vehicleType +
                ", isPublic=" + isPublic +
                '}';
    }
}