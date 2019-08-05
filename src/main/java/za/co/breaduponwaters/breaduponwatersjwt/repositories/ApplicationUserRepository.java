package za.co.breaduponwaters.breaduponwatersjwt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.breaduponwaters.breaduponwatersjwt.models.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
