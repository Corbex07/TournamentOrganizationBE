package nl.fontys.tournamentorganization.Api.Exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {

        super("User with ID " + userId + " not found");;
    }
}
