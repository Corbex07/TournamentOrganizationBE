package nl.fontys.tournamentorganization.Api.DTOs.Tournament;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateTournamentRequestDTO(
        @NotBlank(message = "Tournament name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @NotNull(message = "Maximum capacity is required")
        @Min(value = 2, message = "Capacity must be at least 2")
        Integer maxCapacity,

        @NotNull(message = "Registration deadline is required")
        @Future(message = "Registration deadline must be in the future")
        LocalDateTime registrationDeadline,

        @NotNull(message = "Start date is required")
        @Future(message = "Start date must be in the future")
        LocalDateTime startDate,

        @NotNull(message = "Round duration is required")
        @Positive(message = "Round duration must be positive")
        Integer roundDurationHours
) { }
