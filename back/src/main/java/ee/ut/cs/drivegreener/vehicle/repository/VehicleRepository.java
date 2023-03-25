package ee.ut.cs.drivegreener.vehicle.repository;

import ee.ut.cs.drivegreener.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle getVehicleById(long id);


    List<Vehicle> getVehiclesByMake(@NotBlank @Size(max = 50) String make);

    List<Vehicle> getVehiclesByModel(@NotBlank @Size(max = 50) String model);

    List<Vehicle> getVehiclesByMakeAndModel(@NotBlank @Size(max = 50) String make, @NotBlank @Size(max = 50) String model);

    List<Vehicle> getVehiclesByMakeAndModelAndTrim(@NotBlank @Size(max = 50) String make, @NotBlank @Size(max = 50) String model, @NotBlank @Size(max = 50) String trim);

}