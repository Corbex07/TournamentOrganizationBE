package nl.fontys.tournamentorganization.ServiceTest.User;

import nl.fontys.tournamentorganization.Api.DTOs.User.UserPrivateResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserResponseDTO;
import nl.fontys.tournamentorganization.Api.DTOs.User.UserUpdateRequestDTO;
import nl.fontys.tournamentorganization.Api.Exceptions.UserNotFoundException;
import nl.fontys.tournamentorganization.Api.Mappers.UserDtoMapper;
import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.bll.services.UserService;
import nl.fontys.tournamentorganization.dal.Repositories.Interfaces.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void updateUser_whenGivenCorrectData_shouldUpdateUser() {
        // Arrange
        Long userId = 1L;

        UserUpdateRequestDTO request =
                new UserUpdateRequestDTO("UpdatedUsername");

        UserDomain user = mock(UserDomain.class);

        UserPrivateResponseDTO expectedResponse =
                new UserPrivateResponseDTO(
                        userId,
                        "UpdatedUsername",
                        "test@example.com"
                );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(user);

        when(userDtoMapper.toPrivateResponseDto(user))
                .thenReturn(expectedResponse);

        // Act
        UserPrivateResponseDTO result =
                userService.updateUser(userId, request);

        // Assert
        assertEquals("UpdatedUsername", result.username());
        assertEquals(userId, result.id());

        verify(user).setUsername("UpdatedUsername");
        verify(userRepository).save(user);
        verify(userDtoMapper).toPrivateResponseDto(user);
    }

    @Test
    void updateUser_whenGivenWrongId_shouldThrowUserNotFound() {
        // Arrange
        Long userId = 99L;

        UserUpdateRequestDTO request =
                new UserUpdateRequestDTO("UpdatedUsername");

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUser(userId, request)
        );

        verify(userRepository, never())
                .save(any(UserDomain.class));

        verifyNoInteractions(userDtoMapper);
    }

    @Test
    void updateUser_whenUsernameIsNull_shouldThrowIllegalArgumentException() {
        // Arrange
        Long userId = 1L;

        UserUpdateRequestDTO request =
                new UserUpdateRequestDTO(null);

        UserDomain user = mock(UserDomain.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        // Act + Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.updateUser(userId, request)
        );

        assertEquals(
                "Username cannot be empty",
                exception.getMessage()
        );

        verify(user, never()).setUsername(anyString());

        verify(userRepository, never())
                .save(any(UserDomain.class));

        verifyNoInteractions(userDtoMapper);
    }

    @Test
    void updateUser_whenUsernameIsBlank_shouldThrowIllegalArgumentException() {
        // Arrange
        Long userId = 1L;

        UserUpdateRequestDTO request =
                new UserUpdateRequestDTO("");

        UserDomain user = mock(UserDomain.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        // Act + Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.updateUser(userId, request)
        );

        assertEquals(
                "Username cannot be empty",
                exception.getMessage()
        );

        verify(user, never()).setUsername(anyString());

        verify(userRepository, never())
                .save(any(UserDomain.class));

        verifyNoInteractions(userDtoMapper);
    }

    @Test
    void getMe_whenGivenCorrectId_shouldReturnUser() {
        // Arrange
        Long userId = 1L;

        UserDomain user = mock(UserDomain.class);

        UserPrivateResponseDTO expectedResponse =
                new UserPrivateResponseDTO(1L, "Ziggy", "test@gmail.com");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userDtoMapper.toPrivateResponseDto(user))
                .thenReturn(expectedResponse);

        // Act
        UserPrivateResponseDTO result = userService.getMe(userId);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("Ziggy", result.username());
        assertEquals("test@gmail.com", result.email());

        verify(userRepository).findById(userId);
        verify(userDtoMapper).toPrivateResponseDto(user);
    }

    @Test
    void getMe_whenGivenIncorrectId_shouldReturnUserNotFound() {
        // Arrange
        Long userId = 99L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                UserNotFoundException.class,
                () -> userService.getMe(userId)
        );

        verify(userDtoMapper, never())
                .toPrivateResponseDto(any(UserDomain.class));
    }

    @Test
    void getUserById_whenGivenCorrectId_shouldReturnUser() {
        // Arrange
        Long userId = 1L;

        UserDomain user = mock(UserDomain.class);

        UserResponseDTO expectedResponse =
                new UserResponseDTO(1L, "Ziggy");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userDtoMapper.toResponseDto(user))
                .thenReturn(expectedResponse);

        // Act
        UserResponseDTO result = userService.getUserById(userId);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("Ziggy", result.username());

        verify(userRepository).findById(userId);
        verify(userDtoMapper).toResponseDto(user);
    }

    @Test
    void getUserById_whenGivenIncorrectId_shouldReturnUserNotFound() {
        // Arrange
        Long userId = 99L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(userId)
        );

        verify(userDtoMapper, never())
                .toResponseDto(any(UserDomain.class));
    }
}
