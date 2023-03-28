package ee.ut.cs.drivegreener.vehicle.controller;

import ee.ut.cs.drivegreener.vehicle.dto.FillupDTO;
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

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/fillup")
public class FillupDataController {

    @Autowired
    private FillupRepository fillupRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    Logger logger = LoggerFactory.getLogger(FillupDataController.class);

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Fillup> getFillup(@PathVariable("id") long id) {
        try {
            Fillup fillup = fillupRepository.getFillupById(id);
            return new ResponseEntity<>(fillup, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Vehicle> addFillup(@RequestBody FillupDTO fillupDTO) {
        try {
            Fillup fillup = new Fillup(fillupDTO);
            Vehicle vehicle = vehicleRepository.getVehicleById(fillupDTO.getVehicleId());
            fillup.setVehicle(vehicle);
            fillupRepository.save(fillup);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
