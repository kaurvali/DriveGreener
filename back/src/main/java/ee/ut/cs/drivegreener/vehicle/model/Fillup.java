package ee.ut.cs.drivegreener.vehicle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.ut.cs.drivegreener.vehicle.dto.FillupDTO;
import ee.ut.cs.drivegreener.vehicle.type.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fillups")
public class Fillup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "time")
    private Date time;

    @Column(name = "odometer")
    private int odometer;

    @Column(name = "fuelAmount")
    private double fuelAmount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fillupType")
    private FillupType fillupType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fuelType")
    private FuelType fuelType;

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priceType")
    private PriceType priceType;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tires")
    private TireType tires;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "drivingStyle")
    private DrivingStyleType drivingStyle;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "load")
    private LoadType load;

    @Column(name = "city")
    private int cityDriving;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    public Fillup(FillupDTO fillupDTO) {
        this.time = fillupDTO.getTime();
        this.odometer = fillupDTO.getOdometer();
        this.fuelAmount = fillupDTO.getFuelAmount();
        this.fillupType = fillupDTO.getFillupType();
        this.fuelType = fillupDTO.getFuelType();
        this.price = fillupDTO.getPrice();
        this.priceType = fillupDTO.getPriceType();
        this.notes = fillupDTO.getNotes();
        this.tires = fillupDTO.getTires();
        this.drivingStyle = fillupDTO.getDrivingStyle();
        this.load = fillupDTO.getLoad();
        this.cityDriving = fillupDTO.getCityDriving();
    }

    public Fillup() {

    }

    public Long getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public FillupType getFillupType() {
        return fillupType;
    }

    public void setFillupType(FillupType fillupType) {
        this.fillupType = fillupType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public TireType getTires() {
        return tires;
    }

    public void setTires(TireType tires) {
        this.tires = tires;
    }

    public DrivingStyleType getDrivingStyle() {
        return drivingStyle;
    }

    public void setDrivingStyle(DrivingStyleType drivingStyle) {
        this.drivingStyle = drivingStyle;
    }

    public LoadType getLoad() {
        return load;
    }

    public void setLoad(LoadType load) {
        this.load = load;
    }

    public int getCityDriving() {
        return cityDriving;
    }

    public void setCityDriving(int cityDriving) {
        this.cityDriving = cityDriving;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Fillup{" +
                "id=" + id +
                ", time=" + time +
                ", odometer=" + odometer +
                ", fuelAmount=" + fuelAmount +
                ", fillupType=" + fillupType +
                ", fuelType=" + fuelType +
                ", price=" + price +
                ", priceType=" + priceType +
                ", notes='" + notes + '\'' +
                ", tires=" + tires +
                ", drivingStyle=" + drivingStyle +
                ", load=" + load +
                ", cityDriving=" + cityDriving +
                ", vehicle=" + vehicle +
                '}';
    }
}
