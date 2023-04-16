package ee.ut.cs.drivegreener.vehicle.service;

import ee.ut.cs.drivegreener.vehicle.dto.FillupStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.dto.GraphDTO;
import ee.ut.cs.drivegreener.vehicle.dto.VehicleStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.model.Fillup;
import ee.ut.cs.drivegreener.vehicle.model.Vehicle;
import ee.ut.cs.drivegreener.vehicle.repository.FillupRepository;
import ee.ut.cs.drivegreener.vehicle.repository.VehicleRepository;
import ee.ut.cs.drivegreener.vehicle.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class FillupServiceImpl {

    @Autowired
    private FillupRepository fillupRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleStatisticsDTO getBasicStats(long vehicleId) {
        VehicleStatisticsDTO vehicleStatisticsDTO = new VehicleStatisticsDTO();

        Fillup lastFillup = getLastFillup(vehicleId);

        vehicleStatisticsDTO.setLastOdometer(lastFillup.getOdometer());
        vehicleStatisticsDTO.setLastFilling(lastFillup.getTime());
        vehicleStatisticsDTO.setFirstDone(isFirstDone(vehicleId));

        VehicleType vehicleType = vehicleRepository.getVehicleById(vehicleId).getVehicleType();
        vehicleStatisticsDTO.setVehicleType(vehicleType);

        return vehicleStatisticsDTO;
    }

    private Fillup getLastFillup(long vehicleID) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = 0;
        Fillup out = fillups.get(0);
        for (Fillup fillup : fillups) {
            int current = fillup.getOdometer();
            if (current > maxOdo) {
                maxOdo = fillup.getOdometer();
                out = fillup;
            }
        }
        return out;
    }

    public List<FillupStatisticsDTO> getFillupsStatistics(long vehicleID) {
        List<FillupStatisticsDTO> fillupStatisticsDTOList = new ArrayList<>();

        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);

        for (Fillup fillup : fillups) {
            fillupStatisticsDTOList.add(getFillupStatistic(fillup, vehicleID));
        }

        Collections.reverse(fillupStatisticsDTOList);

        return fillupStatisticsDTOList;
    }

    public FillupStatisticsDTO getFillupStatistic(Fillup fillup, long vehicleID) {
        FillupStatisticsDTO fillupStatisticsDTO = new FillupStatisticsDTO();

        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleID);
        fillupStatisticsDTO.setVehicleType(vehicle.getVehicleType());

        // basics
        fillupStatisticsDTO.setId(fillup.getId());
        fillupStatisticsDTO.setType(fillup.getFillupType());
        fillupStatisticsDTO.setTime(fillup.getTime());
        int odometer = fillup.getOdometer();
        fillupStatisticsDTO.setOdometer(odometer);
        fillupStatisticsDTO.setFillupType(fillup.getFillupType());

        // Remove P from enum
        String fuelType = fillup.getFuelType().toString();
        fuelType = fuelType.startsWith("P") ? fuelType.substring(1) : fuelType;
        fillupStatisticsDTO.setFuelType(fuelType);

        // fuel amount
        double fuel = fillup.getFuelAmount();
        fillupStatisticsDTO.setFuelConsumed(fuel);

        // fuel price
        if (fillup.getPriceType().equals(PriceType.UNIT))
            fillupStatisticsDTO.setTotalCost(Math.round(fillup.getPrice() * fillup.getFuelAmount() * 100.0) / 100.0);
        else
            fillupStatisticsDTO.setTotalCost(Math.round(fillup.getPrice() * 100.0) / 100.0);

        // Trip and fuel consumption
        // first fillup
        if (fillup.getFillupType().equals(FillupType.FIRST)) {
            fillupStatisticsDTO.setFuelConsumption(0);
            fillupStatisticsDTO.setTrip(0);
        }
        // partial fillup
        else if (fillup.getFillupType().equals(FillupType.PARTIAL)) {
            fillupStatisticsDTO.setFuelConsumption(0);
            // calculate trip
            int trip = odometer - getPreviousFillupOdometer(vehicleID, odometer);
            fillupStatisticsDTO.setTrip(trip);
        }
        // full fillup
        else {
            // check whether there were any prev partial fillups...
            double partialFuel = getLastPartialFillupsFuel(vehicleID, odometer);
            if (partialFuel > 0) {
                fuel += partialFuel;
            }
            int trip;
            // last fillup was first fillup
            if (isPreviousFirst(vehicleID, odometer)) {
                trip = odometer - getPreviousFillupOdometer(vehicleID, FillupType.FIRST, odometer);
            }
            // previous was full or partial
            else {
                trip = odometer - getPreviousFillupOdometer(vehicleID, FillupType.FULL, odometer);
            }
            fillupStatisticsDTO.setTrip(trip);
            fillupStatisticsDTO.setFuelConsumption((double) Math.round(((fuel / trip) * 100) * 100) / 100);
        }

        return fillupStatisticsDTO;
    }

    public int getPreviousFillupOdometer(long vehicleID, FillupType type, int odometer) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = -1;
        for (Fillup fillup : fillups) {
            int currentOdo = fillup.getOdometer();
            if (fillup.getFillupType().equals(type) && currentOdo > maxOdo && currentOdo < odometer) {
                maxOdo = fillup.getOdometer();
            }
        }
        return maxOdo;
    }

    public double getLastPartialFillupsFuel(long vehicleID, int odometer) {
        int previousFull = getPreviousFillupOdometer(vehicleID, FillupType.FULL, odometer);
        // if we found no previous full, the last one has to be the first one
        if (previousFull == -1)
            previousFull = getPreviousFillupOdometer(vehicleID, FillupType.FIRST, odometer);
        double fuel = 0;

        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        for (Fillup fillup : fillups) {
            int currentOdo = fillup.getOdometer();
            if (fillup.getFillupType().equals(FillupType.PARTIAL) && currentOdo > previousFull && currentOdo < odometer) {
                fuel += fillup.getFuelAmount();
            }
        }
        return fuel;
    }

    public boolean isPreviousFirst(long vehicleID, int odometer) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);

        int minFullOdo = Integer.MAX_VALUE;

        for (Fillup fillup : fillups) {
            if (fillup.getFillupType().equals(FillupType.FULL) && fillup.getOdometer() < minFullOdo ) {
                minFullOdo = fillup.getOdometer();
            }
        }
        return minFullOdo == odometer;
    }

    public int getPreviousFillupOdometer(long vehicleID, int odometer) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = 0;
        for (Fillup fillup : fillups) {
            int current = fillup.getOdometer();
            if (current > maxOdo && current < odometer ) {
                maxOdo = fillup.getOdometer();
            }
        }
        return maxOdo;
    }

    public Date getPreviousFillupDate(long vehicleID) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = 0;
        Date date = null;
        for (Fillup fillup : fillups) {
            if (fillup.getOdometer() > maxOdo) {
                maxOdo = fillup.getOdometer();
                date = fillup.getTime();
            }
        }
        return date;
    }

    public long getPreviousFillupId(long vehicleID) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = -1;
        long id = -1;
        for (Fillup fillup : fillups) {
            if (fillup.getOdometer() > maxOdo) {
                maxOdo = fillup.getOdometer();
                id = fillup.getId();
            }
        }
        return id;
    }

    public boolean isFirstDone(long vehicleID) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        return !fillups.isEmpty();
    }

    public GraphDTO getPricePerFillingGraph(long vehicleID, int max) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        Collections.reverse(fillups);

        List<Integer> odometer = new ArrayList<>();
        List<Double> valueDouble = new ArrayList<>();

        int counter = 0;
        for (Fillup fillup : fillups) {
            if (counter == max) break;
            FillupStatisticsDTO fillupStatisticsDTO = getFillupStatistic(fillup, vehicleID);
            valueDouble.add(fillupStatisticsDTO.getTotalCost());
            odometer.add(fillupStatisticsDTO.getOdometer());
            counter++;
        }

        Collections.reverse(odometer);
        Collections.reverse(valueDouble);

        return new GraphDTO(vehicleID, new ArrayList<>(), odometer, new ArrayList<>(), valueDouble, "Price per Filling", "km","€","","Price");
    }

    public GraphDTO getConsumptionPerFillingGraph(long vehicleID, int max) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        Collections.reverse(fillups);

        List<Integer> odometer = new ArrayList<>();
        List<Double> valueDouble = new ArrayList<>();

        int counter = 0;
        for (Fillup fillup : fillups) {
            if (counter == max) break;
            FillupStatisticsDTO fillupStatisticsDTO = getFillupStatistic(fillup, vehicleID);
            if (fillupStatisticsDTO.getFuelConsumption() == 0) continue;
            valueDouble.add(fillupStatisticsDTO.getFuelConsumption());
            odometer.add(fillupStatisticsDTO.getOdometer());
            counter++;
        }

        Collections.reverse(odometer);
        Collections.reverse(valueDouble);

        return new GraphDTO(vehicleID, new ArrayList<>(), odometer, new ArrayList<>(), valueDouble, "Consumption per Filling", "km",getUnit(vehicleID)+"/100km","","Consumption");
    }

    public GraphDTO getUnitPriceGraph(long vehicleID, int max) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        Collections.reverse(fillups);

        List<Integer> odometer = new ArrayList<>();
        List<Double> valueDouble = new ArrayList<>();

        int counter = 0;
        for (Fillup fillup : fillups) {
            if (counter == max) break;
            if (fillup.getPrice() == 0 || fillup.getFuelAmount()==0) continue;;
            FillupStatisticsDTO fillupStatisticsDTO = getFillupStatistic(fillup, vehicleID);
            valueDouble.add((double) Math.round(fillupStatisticsDTO.getTotalCost() / fillupStatisticsDTO.getFuelConsumed() * 100) / 100);
            odometer.add(fillupStatisticsDTO.getOdometer());
            counter++;
        }

        Collections.reverse(odometer);
        Collections.reverse(valueDouble);

        return new GraphDTO(vehicleID, new ArrayList<>(), odometer, new ArrayList<>(), valueDouble, "Price per Unit", "km","€/"+getUnit(vehicleID),"","Price per unit");
    }

    public GraphDTO getDistancePerFillingGraph(long vehicleID, int max) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        Collections.reverse(fillups);

        List<Integer> odometer = new ArrayList<>();
        List<Double> valueDouble = new ArrayList<>();

        int counter = 0;
        for (Fillup fillup : fillups) {
            if (counter == max) break;
            FillupStatisticsDTO fillupStatisticsDTO = getFillupStatistic(fillup, vehicleID);
            if (fillupStatisticsDTO.getTrip() == 0) continue;
            valueDouble.add((double) fillupStatisticsDTO.getTrip());
            odometer.add(fillupStatisticsDTO.getOdometer());
            counter++;
        }

        Collections.reverse(odometer);
        Collections.reverse(valueDouble);

        return new GraphDTO(vehicleID, new ArrayList<>(), odometer, new ArrayList<>(), valueDouble, "Trip per Filling", "km","km","","Trip");
    }

    public GraphDTO getCostPerCityDriving(long vehicleID) {
        List<Integer> odometer = new ArrayList<>();
        List<Double> valueDouble = new ArrayList<>();

        for (int i = 0; i <= 100; i++) {
            List<Fillup> fillups = fillupRepository.getFillupsByVehicleIdAndCityDriving(vehicleID, i);
            double fuel = 0, distance = 0, consumption;
            for (Fillup fillup : fillups){
                FillupStatisticsDTO fillupStatisticsDTO = getFillupStatistic(fillup, vehicleID);
                if (fillupStatisticsDTO.getTrip() == 0) continue;
                fuel += fillupStatisticsDTO.getFuelConsumed();
                distance += fillupStatisticsDTO.getTrip();
            }
            if (distance == 0 || fuel == 0) continue;
            odometer.add(i);
            if (vehicleRepository.getVehicleById(vehicleID).getVehicleType().equals(VehicleType.ELECTRIC))
                consumption = (double) Math.round(fuel / distance * 100) / 100;
            else
                consumption = (double) Math.round(fuel / distance * 10000) / 100;
            valueDouble.add(consumption);
        }

        return new GraphDTO(vehicleID, new ArrayList<>(), odometer, new ArrayList<>(), valueDouble, "City driving Consumption", "%",getUnit(vehicleID)+"/100km","","Consumption");
    }

    public GraphDTO getConsumptionPerMonthGraph(long vehicleID, int max) {
        return new GraphDTO();
    }

    public GraphDTO getCostPerMonthGraph(long vehicleID, int max) {
        return new GraphDTO();
    }

    public GraphDTO getDrivinStyleGraph(long vehicleID) {
        List<String> category = new ArrayList<>();
        List<Double> valueDouble = new ArrayList<>();

        for (DrivingStyleType style: DrivingStyleType.values()){
            category.add(style.toString());
            valueDouble.add(getConsuptionForDrivingStyle(vehicleID, style));
        }
        return new GraphDTO(vehicleID, new ArrayList<>(), new ArrayList<>(), category, valueDouble, "Consumption per Driving Style", "",getUnit(vehicleID)+"/100km","","Consumption");
    }

    public double getConsuptionForDrivingStyle(long vehicleID, DrivingStyleType style) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        Collections.reverse(fillups);

        double fuelUsed = 0;
        double distance = 0;

        for (Fillup fillup : fillups) {
            if (fillup.getDrivingStyle().equals(style)){
                FillupStatisticsDTO fillupStatisticsDTO = getFillupStatistic(fillup, vehicleID);
                fuelUsed += fillupStatisticsDTO.getFuelConsumed();
                distance += fillupStatisticsDTO.getTrip();
            }
        }
        if (distance == 0) return 0.0;

        return (double) Math.round(fuelUsed / distance * 10000) / 100;
    }

    public GraphDTO getTireTypeGraph(long vehicleID) {
        List<String> category = new ArrayList<>();
        List<Double> valueDouble = new ArrayList<>();

        for (TireType tire: TireType.values()){
            category.add(tire.toString());
            valueDouble.add(getConsuptionForTireType(vehicleID, tire));
        }

        return new GraphDTO(vehicleID, new ArrayList<>(), new ArrayList<>(), category, valueDouble, "Consumption per Driving Style", "",getUnit(vehicleID)+"/100km","","Consumption");
    }

    public double getConsuptionForTireType(long vehicleID, TireType tire) {
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        Collections.reverse(fillups);

        double fuelUsed = 0;
        double distance = 0;

        for (Fillup fillup : fillups) {
            if (fillup.getTires().equals(tire)){
                FillupStatisticsDTO fillupStatisticsDTO = getFillupStatistic(fillup, vehicleID);
                fuelUsed += fillupStatisticsDTO.getFuelConsumed();
                distance += fillupStatisticsDTO.getTrip();
            }
        }
        if (distance == 0) return 0.0;

        return (double) Math.round(fuelUsed / distance * 10000) / 100;
    }

    public String getUnit(long vehicleID){
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleID);
        if (vehicle.getVehicleType().equals(VehicleType.ELECTRIC)){
            return "kWh";
        }
        return "l";
    }
}
