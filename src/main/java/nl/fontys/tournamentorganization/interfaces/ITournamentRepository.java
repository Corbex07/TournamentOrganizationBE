package nl.fontys.tournamentorganization.interfaces;

import nl.fontys.tournamentorganization.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ITournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByRegistrationDeadlineAfter(LocalDateTime dateTime);
}