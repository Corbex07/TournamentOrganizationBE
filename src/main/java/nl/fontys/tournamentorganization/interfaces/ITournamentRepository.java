package nl.fontys.tournamentorganization.interfaces;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import java.util.List;
import java.util.Optional;

public interface ITournamentRepository {

    List<TournamentDTO> getAllTournaments();

    Optional<TournamentDTO> getTournamentById(Long id);

    TournamentDTO saveTournament(TournamentDTO tournament);

    void deleteTournament(Long id);
}
