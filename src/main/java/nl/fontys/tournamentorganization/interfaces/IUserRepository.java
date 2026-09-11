package nl.fontys.tournamentorganization.interfaces;

import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<Users, Long> {
}
