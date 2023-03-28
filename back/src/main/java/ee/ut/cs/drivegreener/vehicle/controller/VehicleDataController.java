package ee.ut.cs.drivegreener.vehicle.controller;

import ee.ut.cs.drivegreener.login.model.User;
import ee.ut.cs.drivegreener.login.repository.UserRepository;
import ee.ut.cs.drivegreener.vehicle.dto.VehicleDTO;
import ee.ut.cs.drivegreener.vehicle.model.Fillup;
import ee.ut.cs.drivegreener.vehicle.model.Vehicle;
import ee.ut.cs.drivegreener.vehicle.repository.FillupRepository;
import ee.ut.cs.drivegreener.vehicle.repository.VehicleRepository;
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

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        logger.info("NEW ENTRY");
        try {
            Vehicle vehicle = new Vehicle(vehicleDTO);
            User user = userRepository.getReferenceById(vehicleDTO.getUserId());
            vehicle.setUser(user);
            vehicleRepository.save(vehicle);
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
    public ResponseEntity<List<Fillup>> getFillups(@PathVariable("id") long id) {
        try {
            List<Fillup> fillups = fillupRepository.getFillupsByVehicleId(id);
            return new ResponseEntity<>(fillups, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}