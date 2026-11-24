package nl.fontys.tournamentorganization.Api.DTOs.User;


public record AuthResponseDTO(
        String httpHeader,
        String cookie
) {
}
