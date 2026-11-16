package nl.fontys.tournamentorganization.ServiceTest;

import nl.fontys.tournamentorganization.Api.DTOs.User.AuthResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserLoginRequestDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserRegistrationRequestDTO;
import nl.fontys.tournamentorganization.bll.Domains.TournamentDomain;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.bll.services.JwtService;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import nl.fontys.tournamentorganization.bll.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;


    // Authenticate method test --------------------------------------------------------------------------------------

    @Test
    void authenticate_shouldReturnUser_whenCredentialsAreCorrect() {

        // Arrange
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO("User@example.com", "Password123!");

        UserDomain user = new UserDomain(
                "User",
                "User@example.com",
                "hashedPassword"
        );

        when(userRepository.findByEmail("User@example.com"))
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
    void authenticate_shouldThrowUnauthorized_whenEmailDoesNotExist() {

        // Arrange
        UserLoginRequestDTO requestDTO =
                new UserLoginRequestDTO("UnknownEmail", "Password123!");

        when(userRepository.findByEmail("UnknownEmail"))
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

    @Test
    void register_shouldSaveUser_whenCredentialsAreCorrect() {

        // Arrange
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO("user", "email", "password");

        when(userRepository.existsByEmail("email"))
                .thenReturn(Boolean.FALSE);

        when(userRepository.existsByUsername("user"))
                .thenReturn(Boolean.FALSE);

        when(passwordEncoder.encode("password"))
                .thenReturn("encodedPassword");
        // Act

        authService.register(requestDTO);

        // Assert
        ArgumentCaptor<UserDomain> userCaptor =
                ArgumentCaptor.forClass(UserDomain.class);

        verify(userRepository).save(userCaptor.capture());

        UserDomain savedUser = userCaptor.getValue();

        assertEquals("user", savedUser.getUsername());
        assertEquals("email", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPasswordHash());
        verify(passwordEncoder).encode("password");
    }

    @Test
    void register_shouldThrowConflict_whenNameIsUsed() {

        // Arrange
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO("existingName", "email", "password");

        when(userRepository.existsByUsername("existingName"))
                .thenReturn(Boolean.TRUE);

        // Act

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.register(requestDTO)
        );

        assertEquals(
                HttpStatus.CONFLICT,
                exception.getStatusCode()
        );

        // Assert

        verify(userRepository, never())
                .save(any(UserDomain.class));
    }

    @Test
    void register_shouldThrowConflict_whenEmailIsUsed() {

        // Arrange
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO("user", "existingEmail", "password");

        when(userRepository.existsByEmail("existingEmail"))
                .thenReturn(Boolean.TRUE);

        // Act

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.register(requestDTO)
        );

        assertEquals(
                HttpStatus.CONFLICT,
                exception.getStatusCode()
        );

        // Assert

        verify(userRepository, never())
                .save(any(UserDomain.class));
    }

    @Test
    void login_WithValidCredentials_ReturnsAuthResponse() {
        // Arrange
        UserLoginRequestDTO request = new UserLoginRequestDTO(
                "test@example.com",
                "password123"
        );

        UserDomain user = new UserDomain(
                "testuser",
                "test@example.com",
                "hashedPassword"
        );

        String expectedToken = "mock-jwt-token";

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )).thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn(expectedToken);

        // Act
        AuthResponseDTO response = authService.login(request);

        // Assert
        assertEquals(expectedToken, response.accessToken());
        assertEquals("Bearer", response.tokenType());

        verify(jwtService).generateToken(user);
    }




}
