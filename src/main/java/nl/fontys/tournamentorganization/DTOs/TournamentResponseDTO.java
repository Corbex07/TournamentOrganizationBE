package nl.fontys.tournamentorganization.DTOs;

import java.time.LocalDateTime;

public record TournamentResponseDTO(
        Long id,
        String name,
        Integer maxCapacity,
        LocalDateTime registrationDeadline,
        LocalDateTime startDate,
        Integer roundDurationHours,
        String organiserName
) {}