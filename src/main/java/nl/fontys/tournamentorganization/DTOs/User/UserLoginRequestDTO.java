package nl.fontys.tournamentorganization.DTOs.User;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
