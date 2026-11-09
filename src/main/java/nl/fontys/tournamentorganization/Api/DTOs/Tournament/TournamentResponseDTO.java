package nl.fontys.tournamentorganization.Api.DTOs.Tournament;

import jakarta.annotation.Nullable;
import nl.fontys.tournamentorganization.bll.enums.TournamentStatus;

import java.time.LocalDateTime;

public record TournamentResponseDTO(
        Long id,
        String name,
        String description,
        Integer maxCapacity,
        LocalDateTime registrationDeadline,
        LocalDateTime startDate,
        Integer roundDurationHours,
        String organiserName,
        Long organiserId,
        TournamentStatus tournamentStatus
) { }