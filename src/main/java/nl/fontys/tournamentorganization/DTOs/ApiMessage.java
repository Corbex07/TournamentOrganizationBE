package nl.fontys.tournamentorganization.DTOs;

public record ApiMessage(
        int status,
        String message,
        Object body
) {
}
