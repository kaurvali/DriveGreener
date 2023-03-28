package ee.ut.cs.drivegreener.vehicle.repository;

import ee.ut.cs.drivegreener.vehicle.model.Fillup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FillupRepository extends JpaRepository<Fillup, Integer> {

    Fillup getFillupById(Long id);

    List<Fillup> getFillupsByVehicleId(Long vehicleId);
}
