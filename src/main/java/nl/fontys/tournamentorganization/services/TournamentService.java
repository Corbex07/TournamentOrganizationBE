package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
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
            if (tournamentDTO.name == null || tournamentDTO.name.isBlank()) {
                throw new IllegalArgumentException("Tournament name cannot be empty");
            }

            if (tournamentDTO.maxCapacity == null || tournamentDTO.maxCapacity <= 0) {
                throw new IllegalArgumentException("Tournament capacity must be greater than 0");
            }
            Tournament tournament = new Tournament();
            tournament.name = tournamentDTO.name;
            tournament.maxCapacity = tournamentDTO.maxCapacity;
            tournamentRepository.save(tournament);
        }

        public void deleteTournament(Long id) {
            if (tournamentRepository.findById(id).isEmpty()) {
                throw new IllegalArgumentException("Tournament does not exist");
            }
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

