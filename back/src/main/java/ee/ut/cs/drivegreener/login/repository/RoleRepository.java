package ee.ut.cs.drivegreener.login.repository;

import ee.ut.cs.drivegreener.login.model.ERole;
import ee.ut.cs.drivegreener.login.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// based off https://www.bezkoder.com/angular-15-spring-boot-jwt-auth/

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}