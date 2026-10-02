package nl.fontys.tournamentorganization.DTOs.Tournament;

import java.time.LocalDateTime;

public record CreateTournamentRequestDTO(
        String name,
        Integer maxCapacity,
        LocalDateTime registrationDeadline,
        LocalDateTime startDate,
        Integer roundDurationHours
) { }
