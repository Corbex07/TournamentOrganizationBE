package nl.fontys.tournamentorganization.DTOs.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDTO(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 30,
                message = "Username must be between 3 and 30 characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128,
                message = "Password must be between 8 and 128 characters")
        String password

) {
}
