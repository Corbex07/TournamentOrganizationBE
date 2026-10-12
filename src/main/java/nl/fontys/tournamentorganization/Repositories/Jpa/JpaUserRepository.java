package nl.fontys.tournamentorganization.Repositories.Jpa;

import nl.fontys.tournamentorganization.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
