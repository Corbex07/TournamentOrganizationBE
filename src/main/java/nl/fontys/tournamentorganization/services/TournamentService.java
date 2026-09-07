package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

    public class TournamentService {

        private final ITournamentRepository tournamentRepository;

        public TournamentService(ITournamentRepository tournamentRepository) {
            this.tournamentRepository = tournamentRepository;
        }

        public List<Tournament> getAllTournaments() {
            return tournamentRepository.findAll();
        }

        public Tournament getTournamentById(Long id) {
            return tournamentRepository.findById(id)
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Tournament not found"
                            )
                    );
        }

        public void saveTournament(TournamentDTO tournamentDTO) {
            Tournament tournament = new Tournament();
            tournament.name = tournamentDTO.name;
            tournament.maxCapacity = tournamentDTO.maxCapacity;
            tournamentRepository.save(tournament);
        }

        public void deleteTournament(Long id) {
            tournamentRepository.deleteById(id);
        }

        public TournamentDTO updateTournament(Long id, TournamentDTO dto) {
            Tournament tournament = tournamentRepository.findById(id)
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Tournament not found"
                            )
                    );

            tournament.name = dto.name;
            tournament.maxCapacity = dto.maxCapacity;

            Tournament updatedTournament = tournamentRepository.save(tournament);

            TournamentDTO result = new TournamentDTO();
            result.name = updatedTournament.name;
            result.maxCapacity = updatedTournament.maxCapacity;

            return result;
        }
    }

