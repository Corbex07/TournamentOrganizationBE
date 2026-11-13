package nl.fontys.tournamentorganization.Api.DTOs.User;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDTO(
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
