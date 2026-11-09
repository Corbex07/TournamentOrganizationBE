package nl.fontys.tournamentorganization.bll.services;

import nl.fontys.tournamentorganization.Api.DTOs.Tournament.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.Tournament.TournamentResponseDTO;
import nl.fontys.tournamentorganization.Api.Exceptions.TournamentNotFoundException;
import nl.fontys.tournamentorganization.Api.Mappers.TournamentPatchMapper;
import nl.fontys.tournamentorganization.bll.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.Api.Mappers.TournamentCreateDtoMapper;
import nl.fontys.tournamentorganization.bll.enums.TournamentStatus;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.TournamentRepository;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class TournamentService {

    private final TournamentPatchMapper tournamentPatchMapper;

    private final TournamentRepository tournamentRepository;

    private final UserRepository userRepository;

    private final TournamentCreateDtoMapper tournamentCreateDtoMapper;


    public TournamentService(UserRepository userRepository,
                             TournamentRepository tournamentRepository,
                             TournamentCreateDtoMapper dtoMapper,
                             TournamentPatchMapper tournamentPatchMapper) {
        this.tournamentPatchMapper = tournamentPatchMapper;
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.tournamentCreateDtoMapper = dtoMapper;
    }

    public List<TournamentResponseDTO> getAllTournaments() {
        return tournamentRepository.findAll()
                .stream()
                .map(tournamentCreateDtoMapper::toResponseDto)
                .toList();
    }

    public TournamentDomain getTournamentById(Long id) {

        return tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
    }


    public TournamentResponseDTO getTournamentResponseById(Long id) {

        return tournamentCreateDtoMapper.toResponseDto(getTournamentById(id));

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
                tournamentCreateDtoMapper.fromCreateDto(dto, organiser);

        tournament.setTournamentStatus(TournamentStatus.Open);

        tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {

        tournamentRepository.deleteById(id);
    }

    public UpdateTournamentRequestDTO updateTournament(Long id, UpdateTournamentRequestDTO dto) {

        TournamentDomain tournament = getTournamentById(id);

        LocalDateTime registrationDeadline =
                dto.registrationDeadline() != null
                        ? dto.registrationDeadline()
                        : tournament.getRegistrationDeadline();

        LocalDateTime startDate =
                dto.startDate() != null
                        ? dto.startDate()
                        : tournament.getStartDate();

        validateTournamentDates(registrationDeadline, startDate);

        tournamentPatchMapper.updateTournament(dto, tournament);

        TournamentDomain updatedTournament =
                tournamentRepository.save(tournament);

        return tournamentCreateDtoMapper.toUpdateDto(updatedTournament);
    }

    public List<TournamentResponseDTO> getAllOpenRegistrationTournaments() {
        return tournamentRepository.findByRegistrationDeadlineAfter(LocalDateTime.now())
                .stream()
                .map(tournamentCreateDtoMapper::toResponseDto)
                .toList();
    }

    private void validateTournamentDates(
            LocalDateTime registrationDeadline,
            LocalDateTime startDate
    )
    {
        if (registrationDeadline == null) {
            throw new IllegalArgumentException(
                    "Registration deadline cannot be null"
            );
        }

        if (startDate == null) {
            throw new IllegalArgumentException(
                    "Start date cannot be null"
            );
        }
        if (!registrationDeadline.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "Registration deadline must be before tournament start"
            );
        }
    }
}

