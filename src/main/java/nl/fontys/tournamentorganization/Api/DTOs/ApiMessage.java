package nl.fontys.tournamentorganization.Api.DTOs;

public record ApiMessage(
        int status,
        String message,
        Object body
) {
}
