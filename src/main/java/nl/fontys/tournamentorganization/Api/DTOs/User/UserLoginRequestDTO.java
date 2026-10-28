package nl.fontys.tournamentorganization.Api.DTOs.User;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
