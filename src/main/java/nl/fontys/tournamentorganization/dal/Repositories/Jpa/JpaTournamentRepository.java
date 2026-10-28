package nl.fontys.tournamentorganization.dal.Repositories.Jpa;

import nl.fontys.tournamentorganization.dal.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaTournamentRepository extends JpaRepository<Tournament, Long> {
    List<Tournament> findByRegistrationDeadlineAfter(LocalDateTime dateTime);
}