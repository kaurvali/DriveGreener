package ee.ut.cs.drivegreener.vehicle.controller;

import ee.ut.cs.drivegreener.vehicle.dto.FillupDTO;
import ee.ut.cs.drivegreener.vehicle.dto.GraphDTO;
import ee.ut.cs.drivegreener.vehicle.model.Fillup;
import ee.ut.cs.drivegreener.vehicle.model.Vehicle;
import ee.ut.cs.drivegreener.vehicle.repository.FillupRepository;
import ee.ut.cs.drivegreener.vehicle.repository.VehicleRepository;
import ee.ut.cs.drivegreener.vehicle.service.FillupServiceImpl;
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
@RequestMapping("/api/fillup")
public class FillupDataController {

    Logger logger = LoggerFactory.getLogger(FillupDataController.class);
    @Autowired
    private FillupRepository fillupRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private FillupServiceImpl fillupService;

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

    @GetMapping("/{id}/graphs/filling/consumption/{max}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getFillingConsumptionGraph(@PathVariable("id") long id, @PathVariable("max") int max) {
        try {
            return new ResponseEntity<>(fillupService.getConsumptionPerFillingGraph(id, max), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/distance/{max}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getFillingDistanceGraph(@PathVariable("id") long id, @PathVariable("max") int max) {
        try {
            return new ResponseEntity<>(fillupService.getDistancePerFillingGraph(id, max), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/price/{max}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getFillingPriceGraph(@PathVariable("id") long id, @PathVariable("max") int max) {
        try {
            return new ResponseEntity<>(fillupService.getPricePerFillingGraph(id, max), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/unitprice/{max}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getUnitpriceGraph(@PathVariable("id") long id, @PathVariable("max") int max) {
        try {
            return new ResponseEntity<>(fillupService.getUnitPriceGraph(id, max), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/citydriving/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getCityDrivingGraph(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(fillupService.getCostPerCityDriving(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/drivingstyle/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getDrivingStyleConsumptionGraph(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(fillupService.getDrivingStyleGraph(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/tiretype/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getTireTypeConsumptionGraph(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(fillupService.getTireTypeGraph(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/fueltype/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> geFuelTypeConsumptionGraph(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(fillupService.getFuelTypeGraph(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/loadtype/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getLoadTypeConsumptionGraph(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(fillupService.getLoadTypeGraph(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/monthlycost/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getMonthlyCost(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(fillupService.getCostPerMonthGraph(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/graphs/filling/monthlyconsumption/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GraphDTO> getMonthlyConsumption(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(fillupService.getConsumptionPerMonthGraph(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
