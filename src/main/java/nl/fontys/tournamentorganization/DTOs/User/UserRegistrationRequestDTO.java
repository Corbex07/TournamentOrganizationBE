package nl.fontys.tournamentorganization.DTOs.User;

public record UserRegistrationRequestDTO(
        String username,
        String email,
        String password
) {
}
