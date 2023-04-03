package ee.ut.cs.drivegreener.vehicle.service;

import ee.ut.cs.drivegreener.vehicle.dto.FillupStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.dto.VehicleStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.model.Fillup;
import ee.ut.cs.drivegreener.vehicle.repository.FillupRepository;
import ee.ut.cs.drivegreener.vehicle.repository.VehicleRepository;
import ee.ut.cs.drivegreener.vehicle.type.FillupType;
import ee.ut.cs.drivegreener.vehicle.type.PriceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FillupServiceImpl {

    @Autowired
    private FillupRepository fillupRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    Logger logger = LoggerFactory.getLogger(FillupServiceImpl.class);

    public VehicleStatisticsDTO getBasicStats(long id) {
        VehicleStatisticsDTO vehicleStatisticsDTO = new VehicleStatisticsDTO();

        vehicleStatisticsDTO.setLastOdometer(getPreviousFillupOdometer(id));
        vehicleStatisticsDTO.setLastFilling(getPreviousFillupDate(id));
        vehicleStatisticsDTO.setFirstDone(isFirstDone(id));

        return vehicleStatisticsDTO;
    }

    public List<FillupStatisticsDTO> getFillupsStatistics(long vehicleID){
        List<FillupStatisticsDTO> fillupStatisticsDTOList = new ArrayList<>();
        
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        
        for (Fillup fillup:fillups) {
            fillupStatisticsDTOList.add(getFillupStatistic(fillup, vehicleID));
        }

        return fillupStatisticsDTOList;
    }

    public FillupStatisticsDTO getFillupStatistic(Fillup fillup, long vehicleID){
        FillupStatisticsDTO fillupStatisticsDTO = new FillupStatisticsDTO();

        // basics
        fillupStatisticsDTO.setId(fillup.getId());
        fillupStatisticsDTO.setType(fillup.getFillupType());
        fillupStatisticsDTO.setTime(fillup.getTime());
        int odometer = fillup.getOdometer();
        fillupStatisticsDTO.setOdometer(odometer);

        // Remove P from enum
        String fuelType = fillup.getFuelType().toString();
        fuelType = fuelType.startsWith("P") ? fuelType.substring(1) : fuelType;
        fillupStatisticsDTO.setFuelType(fuelType);

        // fuel amount
        double fuel = fillup.getFuelAmount();
        fillupStatisticsDTO.setFuelConsumed(fuel);

        // fuel price
        if (fillup.getPriceType().equals(PriceType.UNIT))
            fillupStatisticsDTO.setTotalCost(fillup.getPrice() * fillup.getFuelAmount());
        else
            fillupStatisticsDTO.setTotalCost(fillup.getPrice());

        // Trip and fuel consumption
        // first fillup
        if (fillup.getFillupType().equals(FillupType.FIRST)) {
            fillupStatisticsDTO.setFuelConsumption(0);
            fillupStatisticsDTO.setTrip(0);
        }
        // partial fillup
        else if (fillup.getFillupType().equals(FillupType.PARTIAL)){
            fillupStatisticsDTO.setFuelConsumption(0);
            // calculate trip
            int trip = odometer - getPreviousFillupOdometer(vehicleID, FillupType.PARTIAL);
            fillupStatisticsDTO.setTrip(trip);
        }
        // full fillup
        else {
            // check whether there were any prev partial fillups...
            double partialFuel = getLastPartialFillupsFuel(vehicleID);
            if (partialFuel > 0){
                fuel += partialFuel;
            }
            int trip;
            // last fillup was first fillup
            if (isPreviousFirst(vehicleID, odometer)){
                trip = odometer - getPreviousFillupOdometer(vehicleID, FillupType.FIRST);
            }
            // previous was full or partial
            else {
                trip = odometer - getPreviousFillupOdometer(vehicleID, FillupType.FULL);
            }
            fillupStatisticsDTO.setTrip(trip);
            fillupStatisticsDTO.setFuelConsumption((double) Math.round((fuel / trip * 100) * 100) / 100);
        }

        return fillupStatisticsDTO;
    }

    public int getPreviousFillupOdometer(long vehicleID, FillupType type){
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = -1;
        for (Fillup fillup:fillups) {
            if(fillup.getFillupType().equals(type) && fillup.getOdometer() > maxOdo){
                maxOdo = fillup.getOdometer();
            }
        }
        return maxOdo;
    }

    public double getLastPartialFillupsFuel(long vehicleID){
        int previousFull = getPreviousFillupOdometer(vehicleID, FillupType.FULL);
        // if we found no previous full, the last one has to be the first one
        if (previousFull == -1)
            previousFull = getPreviousFillupOdometer(vehicleID, FillupType.FIRST);
        double fuel = -1;

        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        for (Fillup fillup:fillups) {
            if(fillup.getFillupType().equals(FillupType.PARTIAL) && fillup.getOdometer() > previousFull){
                fuel += fillup.getFuelAmount();
            }
        }
        return fuel;
    }

    public boolean isPreviousFirst(long vehicleID, int odometer){
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        for (Fillup fillup:fillups) {
            if(fillup.getFillupType().equals(FillupType.FIRST) && fillup.getOdometer() < odometer){
                return true;
            }
        }
        return false;
    }

    public int getPreviousFillupOdometer(long vehicleID){
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = 0;
        for (Fillup fillup:fillups) {
            if(fillup.getOdometer() > maxOdo){
                maxOdo = fillup.getOdometer();
            }
        }
        return maxOdo;
    }

    public Date getPreviousFillupDate(long vehicleID){
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = 0;
        Date date = null;
        for (Fillup fillup:fillups) {
            if(fillup.getOdometer() > maxOdo){
                maxOdo = fillup.getOdometer();
                date = fillup.getTime();
            }
        }
        return date;
    }

    public long getPreviousFillupId(long vehicleID){
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        int maxOdo = -1;
        long id = -1;
        for (Fillup fillup:fillups) {
            if(fillup.getOdometer() > maxOdo){
                maxOdo = fillup.getOdometer();
                id = fillup.getId();
            }
        }
        return id;
    }

    public boolean isFirstDone(long vehicleID){
        List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(vehicleID);
        return !fillups.isEmpty();
    }
}
