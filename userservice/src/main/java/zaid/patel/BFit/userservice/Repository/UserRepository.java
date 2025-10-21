package zaid.patel.BFit.userservice.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zaid.patel.BFit.userservice.Entity.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid Formate") String email);

    Boolean existsByKeycloakId(String userId);

    User findByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid Formate") String email);
}
