package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.DTOs.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.interfaces.IUserRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.models.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

    public void createTournament(CreateTournamentRequestDTO dto) {
        validateTournament(
                dto.name(),
                dto.maxCapacity(),
                dto.registrationDeadline(),
                dto.startDate(),
                dto.roundDurationHours());

        Users organiser = userRepository.findById(5L)
                .orElseThrow(() ->
                        new IllegalArgumentException("Organiser not found")
                );

        Tournament tournament = new Tournament(
                dto.name(),
                organiser,
                dto.maxCapacity(),
                dto.registrationDeadline(),
                dto.startDate(),
                dto.roundDurationHours()
        );

        tournamentRepository.save(tournament);
    }

    private void validateTournament(
            String name,
            Integer maxCapacity,
            LocalDateTime registrationDeadline,
            LocalDateTime startDate,
            Integer roundDurationHours
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tournament name cannot be empty");
        }

        if (maxCapacity == null || maxCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Tournament capacity must be greater than 0"
            );
        }

        if (startDate == null) {
            throw new IllegalArgumentException(
                    "Tournament start date is required"
            );
        }

        if (registrationDeadline == null) {
            throw new IllegalArgumentException(
                    "Registration deadline is required"
            );
        }

        if (registrationDeadline.isAfter(startDate)) {
            throw new IllegalArgumentException(
                    "Registration deadline must be before tournament start"
            );
        }

        if (roundDurationHours == null || roundDurationHours <= 0) {
            throw new IllegalArgumentException(
                    "Round duration must be greater than 0"
            );
        }
    }

        public void deleteTournament(Long id) {
            if (tournamentRepository.findById(id).isEmpty()) {
                throw new IllegalArgumentException("Tournament does not exist");
            }
            tournamentRepository.deleteById(id);
        }

    public UpdateTournamentRequestDTO updateTournament(Long id, UpdateTournamentRequestDTO dto) {
        validateTournament(
                dto.name(),
                dto.maxCapacity(),
                dto.registrationDeadline(),
                dto.startDate(),
                dto.roundDurationHours());

        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tournament not found"
                        )
                );

        tournament.setName(dto.name());
        tournament.setMaxCapacity(dto.maxCapacity());
        tournament.setRegistrationDeadline(dto.registrationDeadline());
        tournament.setStartDate(dto.startDate());
        tournament.setRoundDurationHours(dto.roundDurationHours());

        Tournament updatedTournament = tournamentRepository.save(tournament);

        return new UpdateTournamentRequestDTO(
                updatedTournament.getName(),
                updatedTournament.getMaxCapacity(),
                updatedTournament.getRegistrationDeadline(),
                updatedTournament.getStartDate(),
                updatedTournament.getRoundDurationHours()
        );
    }

    public List<Tournament> getAllOpenRegistrationTournaments() {
        return tournamentRepository.findByRegistrationDeadlineAfter(
                LocalDateTime.now()
        );
    }
}

