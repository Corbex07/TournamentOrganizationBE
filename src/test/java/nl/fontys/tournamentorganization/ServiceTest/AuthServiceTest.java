package nl.fontys.tournamentorganization.ServiceTest;

import nl.fontys.tournamentorganization.Api.DTOs.User.UserLoginRequestDTO;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import nl.fontys.tournamentorganization.bll.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;


    // Authenticate method test --------------------------------------------------------------------------------------

    @Test
    void authenticate_shouldReturnUser_whenCredentialsAreCorrect() {

        // Arrange
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO("User", "Password123!");

        UserDomain user = new UserDomain(
                "User",
                "User@example.com",
                "hashedPassword"
        );

        when(userRepository.findByUsername("User"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "Password123!", "hashedPassword"))
                .thenReturn(true);

        // Act
        UserDomain result = authService.authenticate(requestDTO);

        // Assert
        assertSame(user, result);
    }

    @Test
    void authenticate_shouldThrowError_whenPasswordIsIncorrect() {

        // Arrange
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO("User", "WrongPassword");

        UserDomain user = new UserDomain(
                "User",
                "User@example.com",
                "hashedPassword"
        );

        when(userRepository.findByUsername("User"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "WrongPassword", "hashedPassword"))
                .thenReturn(false);

        // Act
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.authenticate(requestDTO)
        );

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void authenticate_shouldThrowUnauthorized_whenUsernameDoesNotExist() {

        // Arrange
        UserLoginRequestDTO requestDTO =
                new UserLoginRequestDTO("UnknownUser", "Password123!");

        when(userRepository.findByUsername("UnknownUser"))
                .thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.authenticate(requestDTO)
        );

        assertEquals(
                HttpStatus.UNAUTHORIZED,
                exception.getStatusCode()
        );

        verifyNoInteractions(passwordEncoder);
    }
}
