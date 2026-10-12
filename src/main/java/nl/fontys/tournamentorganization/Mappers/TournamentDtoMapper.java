package nl.fontys.tournamentorganization.Mappers;

import nl.fontys.tournamentorganization.DTOs.Tournament.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.DTOs.Tournament.TournamentResponseDTO;
import nl.fontys.tournamentorganization.DTOs.Tournament.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.Domains.UserDomain;

public class TournamentDtoMapper {

    public TournamentResponseDTO toResponseDto(TournamentDomain tournament) {
        return new TournamentResponseDTO(
                tournament.getId(),
                tournament.getName(),
                tournament.getMaxCapacity(),
                tournament.getRegistrationDeadline(),
                tournament.getStartDate(),
                tournament.getRoundDurationHours(),
                tournament.getOrganiser().getUsername()
        );
    }

    public TournamentDomain fromCreateDto(
            CreateTournamentRequestDTO dto,
            UserDomain organiser
    ) {
        return new TournamentDomain(
                dto.name(),
                organiser,
                dto.maxCapacity(),
                dto.registrationDeadline(),
                dto.startDate(),
                dto.roundDurationHours()
        );
    }

    public void updateDomainFromDto(
            TournamentDomain tournament,
            UpdateTournamentRequestDTO dto
    ) {
        tournament.setName(dto.name());
        tournament.setMaxCapacity(dto.maxCapacity());
        tournament.setRegistrationDeadline(dto.registrationDeadline());
        tournament.setStartDate(dto.startDate());
        tournament.setRoundDurationHours(dto.roundDurationHours());
    }

    public UpdateTournamentRequestDTO toUpdateDto(
            TournamentDomain tournament
    ) {
        return new UpdateTournamentRequestDTO(
                tournament.getName(),
                tournament.getMaxCapacity(),
                tournament.getRegistrationDeadline(),
                tournament.getStartDate(),
                tournament.getRoundDurationHours()
        );
    }
}
