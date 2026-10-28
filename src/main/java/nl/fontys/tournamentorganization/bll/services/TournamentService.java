package nl.fontys.tournamentorganization.bll.services;

import nl.fontys.tournamentorganization.Api.DTOs.Tournament.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.TournamentResponseDTO;
import nl.fontys.tournamentorganization.bll.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.Api.Mappers.TournamentDtoMapper;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.TournamentRepository;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TournamentService {


    private final TournamentRepository tournamentRepository;

    private final UserRepository userRepository;

    private final TournamentDtoMapper tournamentDtoMapper;


    public TournamentService(UserRepository userRepository,
                             TournamentRepository tournamentRepository,
                             TournamentDtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.tournamentDtoMapper = dtoMapper;
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
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tournament not found"
                        )
                );
    }


    public TournamentResponseDTO getTournamentResponseById(Long id) {
        return tournamentDtoMapper.toResponseDto(
                getTournamentById(id)
        );
    }

    public void createTournament(CreateTournamentRequestDTO dto) {
        validateTournamentDates(
                dto.registrationDeadline(),
                dto.startDate());

        // done manually due to not having implemented users yet.
        UserDomain organiser = userRepository.findById(1L)
                .orElseThrow(() ->
                        new IllegalArgumentException("Organiser not found")
                );


        TournamentDomain tournament =
                tournamentDtoMapper.fromCreateDto(dto, organiser);

        tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
            if (tournamentRepository.findById(id).isEmpty()) {
                throw new IllegalArgumentException("Tournament does not exist");
            }
            tournamentRepository.deleteById(id);
        }

    public UpdateTournamentRequestDTO updateTournament(Long id, UpdateTournamentRequestDTO dto) {
        validateTournamentDates(
                dto.registrationDeadline(),
                dto.startDate());

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

    private void validateTournamentDates(
            LocalDateTime registrationDeadline,
            LocalDateTime startDate
    )
    {
        if (!registrationDeadline.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "Registration deadline must be before tournament start"
            );
        }
    }
}

