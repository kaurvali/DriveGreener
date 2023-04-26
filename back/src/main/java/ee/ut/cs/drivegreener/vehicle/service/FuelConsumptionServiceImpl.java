package ee.ut.cs.drivegreener.vehicle.service;

import ee.ut.cs.drivegreener.vehicle.dto.UserStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.dto.VehicleStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.model.Fillup;
import ee.ut.cs.drivegreener.vehicle.model.Vehicle;
import ee.ut.cs.drivegreener.vehicle.repository.FillupRepository;
import ee.ut.cs.drivegreener.vehicle.repository.VehicleRepository;
import ee.ut.cs.drivegreener.vehicle.type.FillupType;
import ee.ut.cs.drivegreener.vehicle.type.PriceType;
import ee.ut.cs.drivegreener.vehicle.type.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelConsumptionServiceImpl {

    @Autowired
    private FillupRepository fillupRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public UserStatisticsDTO calculateMultipleVehicleStatistics(long userId){
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByUserId(userId);

        double fuelConsumed = 0.0;
        double energyConsumed = 0.0;
        double totalCost = 0.0;
        double co = 0.0;
        double elecDistance = 0.0;
        double iceDistance = 0.0;
        int distance = 0;

        for (Vehicle vehicle : vehicles){
            VehicleStatisticsDTO vehicleStatisticsDTO = calculateTotalVehicleParameters(vehicle.getId());
            distance += vehicleStatisticsDTO.getDistance();
            totalCost += vehicleStatisticsDTO.getTotalCost();
            co += vehicleStatisticsDTO.getCo();
            if (vehicle.getVehicleType().equals(VehicleType.ELECTRIC)){
                energyConsumed += vehicleStatisticsDTO.getFuelConsumed();
                elecDistance += vehicleStatisticsDTO.getDistance();
            }
            else {
                fuelConsumed += vehicleStatisticsDTO.getFuelConsumed();
                iceDistance += vehicleStatisticsDTO.getDistance();
            }
        }
        double fuelConsumption = 0.0;
        if (iceDistance != 0)
            fuelConsumption = Math.round((fuelConsumed * 100) / iceDistance * 100.0) / 100.0;

        double energyConsumption = 0.0;
        if (elecDistance != 0)
            energyConsumption = Math.round((energyConsumed * 100) / elecDistance * 100.0) / 100.0;
        // calculate price per 100km
        double hundredCost = 0.0;
        if (distance!=0)
            hundredCost = Math.round((totalCost * 100) / distance * 100.0) / 100.0;

        return new UserStatisticsDTO(distance, fuelConsumed, energyConsumed, fuelConsumption, energyConsumption, totalCost, hundredCost, co);
    }

    public VehicleStatisticsDTO calculateTotalVehicleParameters(long vehicleID) {
        double fuelConsumed, fuelConsumption, totalCost, co;
        int distance;

        List<Fillup> fillups = fillupRepository.getFillupsByVehicleIdOrderByOdometer(vehicleID);
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleID);

        if (fillups.size() < 2) {
            return new VehicleStatisticsDTO(0, 0, 0, 0, 0, 0, null, false, vehicle.getVehicleType());
        } else {
            int start = fillups.get(0).getOdometer();
            int end = fillups.get(fillups.size() - 1).getOdometer();

            distance = end - start;
            fuelConsumed = calculateTotalFuel(fillups);
            fuelConsumption = Math.round(calculateFuelConsumption(start, end, fuelConsumed) * 100.0) / 100.0;
            totalCost = Math.round(calculateTotalCost(fillups)* 100.0) / 100.0;
            co = calculateTotalCO(fillups, vehicle.getVehicleType());

            return new VehicleStatisticsDTO(fuelConsumed, distance, fuelConsumption, totalCost, co, 0, null, false, vehicle.getVehicleType());
        }
    }

    private double calculateTotalCost(List<Fillup> fillups) {
        double sum = 0;
        for (Fillup fillup : fillups) {
            if (fillup.getPriceType().equals(PriceType.UNIT))
                sum += fillup.getPrice() * fillup.getFuelAmount();
            else
                sum += fillup.getPrice();
        }
        return sum;
    }

    public double calculateFuelConsumption(int start, int end, double fuel) {
        return (fuel * 100) / (end - start);
    }

    public double calculateAverageCityDriving(List<Fillup> fillups) {
        double sum = 0;
        for (Fillup fillup : fillups) {
            sum += fillup.getCityDriving();
        }
        return Math.round(sum / fillups.size());
    }

    public double calculateTotalCO(List<Fillup> fillups, VehicleType type) {
        double energy;
        double sum = 0;
        if (type.equals(VehicleType.ELECTRIC))
            return 0.0;
        else if (type.equals(VehicleType.DIESEL))
            energy = 2.62;
        else
            energy = 2.39;
        for (Fillup fillup : fillups)
            sum += fillup.getFuelAmount() * energy;
        return Math.round(sum * 100.0) / 100.0;
    }

    public double calculateTotalFuel(List<Fillup> fillups) {
        double sum = 0;
        for (Fillup fillup : fillups) {
            if (!fillup.getFillupType().equals(FillupType.FIRST))
                sum += fillup.getFuelAmount();
        }
        return sum;
    }
}
