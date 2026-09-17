package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.interfaces.IUserRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.models.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class TournamentService {

    private final ITournamentRepository tournamentRepository;
    private final IUserRepository userRepository;

    public TournamentService(ITournamentRepository tournamentRepository, IUserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
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
        validateTournament(tournamentDTO);

        Users organiser = userRepository.findById(5L)
                .orElseThrow(() ->
                        new IllegalArgumentException("Organiser not found")
                );

        Tournament tournament = new Tournament(
                tournamentDTO.name,
                organiser,
                tournamentDTO.maxCapacity,
                tournamentDTO.registrationDeadline,
                tournamentDTO.startDate,
                tournamentDTO.roundDurationHours
        );

        tournamentRepository.save(tournament);
    }

    private void validateTournament(TournamentDTO dto) {
        if (dto.name == null || dto.name.isBlank()) {
            throw new IllegalArgumentException("Tournament name cannot be empty");
        }

        if (dto.maxCapacity == null || dto.maxCapacity <= 0) {
            throw new IllegalArgumentException("Tournament capacity must be greater than 0");
        }

        if (dto.startDate == null) {
            throw new IllegalArgumentException("Tournament start date is required");
        }

        if (dto.registrationDeadline == null) {
            throw new IllegalArgumentException("Registration deadline is required");
        }

        if (dto.registrationDeadline.isAfter(dto.startDate)) {
            throw new IllegalArgumentException(
                    "Registration deadline must be before tournament start"
            );
        }

        if (dto.roundDurationHours == null || dto.roundDurationHours <= 0) {
            throw new IllegalArgumentException("Round duration must be greater than 0");
        }
    }

        public void deleteTournament(Long id) {
            if (tournamentRepository.findById(id).isEmpty()) {
                throw new IllegalArgumentException("Tournament does not exist");
            }
            tournamentRepository.deleteById(id);
        }

    public TournamentDTO updateTournament(Long id, TournamentDTO dto) {
        validateTournament(dto);

        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tournament not found"
                        )
                );

        tournament.setName(dto.name);
        tournament.setMaxCapacity(dto.maxCapacity);
        tournament.setRegistrationDeadline(dto.registrationDeadline);
        tournament.setStartDate(dto.startDate);
        tournament.setRoundDurationHours(dto.roundDurationHours);

        Tournament updatedTournament = tournamentRepository.save(tournament);

        TournamentDTO result = new TournamentDTO();
        result.name = updatedTournament.getName();
        result.maxCapacity = updatedTournament.getMaxCapacity();
        result.registrationDeadline = updatedTournament.getRegistrationDeadline();
        result.startDate = updatedTournament.getStartDate();
        result.roundDurationHours = updatedTournament.getRoundDurationHours();

        return result;
    }
}

