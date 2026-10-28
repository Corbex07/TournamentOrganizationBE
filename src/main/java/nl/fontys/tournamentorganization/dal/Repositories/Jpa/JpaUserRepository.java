package nl.fontys.tournamentorganization.dal.Repositories.Jpa;

import nl.fontys.tournamentorganization.dal.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Users> findByUsername(String username);
}
