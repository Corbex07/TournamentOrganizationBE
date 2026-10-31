package nl.fontys.tournamentorganization.Api.DTOs.Tournament;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record UpdateTournamentRequestDTO(
        @NotBlank(message = "Tournament name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @NotNull(message = "Maximum capacity is required")
        @Min(value = 2, message = "Capacity must be at least 2")
        Integer maxCapacity,

        @NotNull(message = "Registration deadline is required")
        LocalDateTime registrationDeadline,

        @NotNull(message = "Start date is required")
        LocalDateTime startDate,

        @NotNull(message = "Round duration is required")
        @Positive(message = "Round duration must be greater than 0")
        Integer roundDurationHours) {
}
