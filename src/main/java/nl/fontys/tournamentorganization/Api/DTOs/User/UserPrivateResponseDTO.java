package nl.fontys.tournamentorganization.Api.DTOs.User;

public record UserPrivateResponseDTO(
        Long id,
        String username,
        String email
) {}
