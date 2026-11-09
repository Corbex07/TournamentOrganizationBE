package nl.fontys.tournamentorganization.Api.DTOs.Tournament;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record UpdateTournamentRequestDTO(
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @Min(value = 2, message = "Capacity must be at least 2")
        Integer maxCapacity,

        LocalDateTime registrationDeadline,

        LocalDateTime startDate,

        @Positive(message = "Round duration must be greater than 0")
        Integer roundDurationHours) {
}
