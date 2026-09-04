package nl.fontys.tournamentorganization.interfaces;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.models.Tournament;

import java.util.List;
import java.util.Optional;
public interface ITournamentRepository {

    List<Tournament> getAllTournaments();

    Optional<Tournament> getTournamentById(Long id);

    Tournament saveTournament(Tournament tournament);

    void deleteTournament(Long id);
}