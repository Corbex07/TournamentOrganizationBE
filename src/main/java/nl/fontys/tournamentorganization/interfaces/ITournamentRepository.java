package nl.fontys.tournamentorganization.interfaces;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ITournamentRepository extends JpaRepository<Tournament, Long> {

}