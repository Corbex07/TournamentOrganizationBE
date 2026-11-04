package nl.fontys.tournamentorganization.Api.Exceptions;

public class TournamentNotFoundException extends RuntimeException {

    public TournamentNotFoundException(Long id) {
        super("Tournament with ID " + id + " not found");
    }
}