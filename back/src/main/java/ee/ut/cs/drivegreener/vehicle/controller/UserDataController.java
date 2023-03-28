package ee.ut.cs.drivegreener.vehicle.controller;

import ee.ut.cs.drivegreener.login.repository.UserRepository;
import ee.ut.cs.drivegreener.vehicle.model.Vehicle;
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
@RequestMapping("/api/user")
public class UserDataController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserDataController.class);

    @GetMapping("/{id}/vehicles")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Vehicle>> getVehicles(@PathVariable("id") long id) {
        try {
            List<Vehicle> vehicles = vehicleRepository.getVehiclesByUserId(id);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
