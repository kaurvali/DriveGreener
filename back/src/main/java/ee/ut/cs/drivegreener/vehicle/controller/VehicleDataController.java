package ee.ut.cs.drivegreener.vehicle.controller;

import ee.ut.cs.drivegreener.login.model.User;
import ee.ut.cs.drivegreener.login.repository.UserRepository;
import ee.ut.cs.drivegreener.vehicle.dto.FillupStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.dto.VehicleDTO;
import ee.ut.cs.drivegreener.vehicle.dto.VehicleStatisticsDTO;
import ee.ut.cs.drivegreener.vehicle.model.Vehicle;
import ee.ut.cs.drivegreener.vehicle.repository.FillupRepository;
import ee.ut.cs.drivegreener.vehicle.repository.VehicleRepository;
import ee.ut.cs.drivegreener.vehicle.service.FillupServiceImpl;
import ee.ut.cs.drivegreener.vehicle.service.FuelConsumptionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/vehicle")
public class VehicleDataController {
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private FillupRepository fillupRepository;

    @Autowired
    private FuelConsumptionServiceImpl fuelConsumptionService;

    @Autowired
    private FillupServiceImpl fillupService;

    Logger logger = LoggerFactory.getLogger(VehicleDataController.class);

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable("id") long id) {
        try {
            Vehicle vehicle = vehicleRepository.getVehicleById(id);
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/statistics")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<VehicleStatisticsDTO> getVehicleStatistics(@PathVariable("id") long id) {
        try {
            VehicleStatisticsDTO vehicleStatisticsDTO = fuelConsumptionService.calculateTotalVehicleParameters(id);
            return new ResponseEntity<>(vehicleStatisticsDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            Vehicle vehicle = new Vehicle(vehicleDTO);
            User user = userRepository.getReferenceById(vehicleDTO.getUserId());
            vehicle.setUser(user);
            vehicleRepository.save(vehicle);
            logger.info("Adding a vehicle");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/{id}/fillups")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<FillupStatisticsDTO>> getFillupsStatistics(@PathVariable("id") long id) {
        try {
            List<FillupStatisticsDTO> fillupStatisticsDTOList = fillupService.getFillupsStatistics(id);
            logger.info(fillupStatisticsDTOList.toString());
            return new ResponseEntity<>(fillupStatisticsDTOList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/basic-stats")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<VehicleStatisticsDTO> getBasicStats(@PathVariable("id") long id) {
        try {
            VehicleStatisticsDTO vehicleStatisticsDTO = fillupService.getBasicStats(id);
            logger.info(vehicleStatisticsDTO.toString());
            return new ResponseEntity<>(vehicleStatisticsDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}