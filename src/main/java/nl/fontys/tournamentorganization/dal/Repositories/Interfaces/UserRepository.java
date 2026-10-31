package nl.fontys.tournamentorganization.dal.Repositories.Interfaces;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;

import java.util.Optional;

public interface UserRepository {

    Optional<UserDomain> findById(Long id);

    Optional<UserDomain> findByUsername(String username);

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email);

    boolean existsByUsername(String username);

    UserDomain save(UserDomain user);

}
