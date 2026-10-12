package nl.fontys.tournamentorganization.services;

import nl.fontys.tournamentorganization.DTOs.Tournament.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.DTOs.Tournament.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.DTOs.Tournament.TournamentResponseDTO;
import nl.fontys.tournamentorganization.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.Domains.UserDomain;
import nl.fontys.tournamentorganization.Mappers.TournamentDtoMapper;
import nl.fontys.tournamentorganization.Mappers.TournamentMapper;
import nl.fontys.tournamentorganization.Mappers.UserMapper;
import nl.fontys.tournamentorganization.Repositories.Interfaces.TournamentRepository;
import nl.fontys.tournamentorganization.Repositories.Jpa.JpaTournamentRepository;
import nl.fontys.tournamentorganization.Repositories.Jpa.JpaUserRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.models.Users;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TournamentService {

    private final JpaTournamentRepository jpaTournamentRepository;

    private final TournamentRepository tournamentRepository;

    private final JpaUserRepository userRepository;

    private final TournamentDtoMapper tournamentDtoMapper;

    private final UserMapper userMapper;


    public TournamentService(JpaTournamentRepository jpaTournamentRepository,
                             JpaUserRepository userRepository,
                             TournamentRepository tournamentRepository,
                             TournamentDtoMapper dtoMapper,
                             UserMapper userMapper) {
        this.jpaTournamentRepository = jpaTournamentRepository;
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.tournamentDtoMapper = dtoMapper;
        this.userMapper = userMapper;
    }

    public List<TournamentResponseDTO> getAllTournaments() {
        return tournamentRepository.findAll()
                .stream()
                .map(tournamentDtoMapper::toResponseDto)
                .toList();
    }

    public TournamentDomain getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tournament not found")
                );
    }


    public TournamentResponseDTO getTournamentResponseById(Long id) {
        return tournamentDtoMapper.toResponseDto(
                getTournamentById(id)
        );
    }

    public void createTournament(CreateTournamentRequestDTO dto) {
        validateTournament(
                dto.name(),
                dto.maxCapacity(),
                dto.registrationDeadline(),
                dto.startDate(),
                dto.roundDurationHours());

        // done manually due to not having implemented users yet.
        Users organiserEntity = userRepository.findById(1L)
                .orElseThrow(() ->
                        new IllegalArgumentException("Organiser not found")
                );

        UserDomain organiser = userMapper.toDomain(organiserEntity);


        TournamentDomain tournament =
                tournamentDtoMapper.fromCreateDto(dto, organiser);

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

        TournamentDomain tournament = tournamentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tournament not found"
                        )
                );
        tournamentDtoMapper.updateDomainFromDto(tournament, dto);

        TournamentDomain updatedTournament =
                tournamentRepository.save(tournament);

        return tournamentDtoMapper.toUpdateDto(updatedTournament);
    }

    public List<TournamentResponseDTO> getAllOpenRegistrationTournaments() {
        return tournamentRepository.findByRegistrationDeadlineAfter(LocalDateTime.now())
                .stream()
                .map(tournamentDtoMapper::toResponseDto)
                .toList();
    }
}

