package nl.fontys.tournamentorganization.ServiceTest;

import nl.fontys.tournamentorganization.DTOs.CreateTournamentRequestDTO;
import nl.fontys.tournamentorganization.DTOs.UpdateTournamentRequestDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.interfaces.IUserRepository;
import nl.fontys.tournamentorganization.models.Tournament;
import nl.fontys.tournamentorganization.models.Users;
import nl.fontys.tournamentorganization.services.TournamentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

    @Mock
    private ITournamentRepository tournamentRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private TournamentService tournamentService;

    private CreateTournamentRequestDTO createValidCreateTournamentRequestDTO() {
        return new CreateTournamentRequestDTO(
                "Fontys Valorant Cup",
                8,
                LocalDateTime.of(2027, 9, 20, 18, 0),
                LocalDateTime.of(2027, 9, 21, 18, 0),
                48
        );
    }
    private UpdateTournamentRequestDTO createValidUpdateTournamentRequestDTO() {
        return new UpdateTournamentRequestDTO(
                "Fontys Valorant Cup",
                8,
                LocalDateTime.of(2027, 9, 20, 18, 0),
                LocalDateTime.of(2027, 9, 21, 18, 0),
                48
        );
    }

// saveTournament ----------------------------------------------------------------------------------------------------

    @Test
    void saveTournament_shouldSaveTournamentWithCorrectValues() {
        CreateTournamentRequestDTO createTournamentRequestDTO = createValidCreateTournamentRequestDTO();

        Users organiser = mock(Users.class);

        when(userRepository.findById(5L))
                .thenReturn(Optional.of(organiser));

        tournamentService.createTournament(createTournamentRequestDTO);

        ArgumentCaptor<Tournament> captor =
                ArgumentCaptor.forClass(Tournament.class);

        verify(tournamentRepository).save(captor.capture());

        Tournament savedTournament = captor.getValue();

        assertEquals("Fontys Valorant Cup", savedTournament.getName());
        assertEquals(8, savedTournament.getMaxCapacity());
        assertEquals(
                createTournamentRequestDTO.registrationDeadline(),
                savedTournament.getRegistrationDeadline()
        );
        assertEquals(
                createTournamentRequestDTO.startDate(),
                savedTournament.getStartDate()
        );
        assertEquals(
                48,
                savedTournament.getRoundDurationHours()
        );
        assertEquals(
                organiser,
                savedTournament.getOrganiser()
        );
    }
    @Test
    void saveTournament_shouldThrowException_whenCapacityIsNegative() {
        CreateTournamentRequestDTO validDTO = createValidCreateTournamentRequestDTO();

        CreateTournamentRequestDTO createTournamentRequestDTO = new CreateTournamentRequestDTO(
                validDTO.name(),
                -5,
                validDTO.registrationDeadline(),
                validDTO.startDate(),
                validDTO.roundDurationHours()
        );


        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.createTournament(createTournamentRequestDTO)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }
    @Test
    void saveTournament_shouldThrowException_whenNameIsNull() {
        CreateTournamentRequestDTO validDTO = createValidCreateTournamentRequestDTO();

        CreateTournamentRequestDTO createTournamentRequestDTO = new CreateTournamentRequestDTO(
                null,
                validDTO.maxCapacity(),
                validDTO.registrationDeadline(),
                validDTO.startDate(),
                validDTO.roundDurationHours()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.createTournament(createTournamentRequestDTO)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }

    @Test
    void saveTournament_shouldThrowException_whenStartDateIsNull() {
        CreateTournamentRequestDTO validDTO = createValidCreateTournamentRequestDTO();

        CreateTournamentRequestDTO createTournamentRequestDTO = new CreateTournamentRequestDTO(
                validDTO.name(),
                validDTO.maxCapacity(),
                validDTO.registrationDeadline(),
                null,
                validDTO.roundDurationHours()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.createTournament(createTournamentRequestDTO)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }

    @Test
    void saveTournament_shouldThrowException_whenRegistrationDeadlineIsAfterStartDate() {
        CreateTournamentRequestDTO validDTO = createValidCreateTournamentRequestDTO();

        CreateTournamentRequestDTO createTournamentRequestDTO = new CreateTournamentRequestDTO(
                validDTO.name(),
                validDTO.maxCapacity(),
                LocalDateTime.of(2027, 9, 22, 18, 0),
                validDTO.startDate(),
                validDTO.roundDurationHours()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.createTournament(createTournamentRequestDTO)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }

    // getAllTournament  ----------------------------------------------------------------------------------------------------

    @Test
    void getAllTournaments_shouldReturnAllTournaments() {
        // arrange
        Tournament tournament1 = new Tournament(
                "Tournament 1",
                null,
                8,
                LocalDateTime.of(2027, 9, 19, 18, 0),
                LocalDateTime.of(2027, 9, 20, 18, 0),
                48
        );
        Tournament tournament2 = new Tournament(
                "Tournament 2",
                null,
                8,
                LocalDateTime.of(2027, 9, 19, 18, 0),
                LocalDateTime.of(2027, 9, 20, 18, 0),
                48
        );

        List<Tournament> expected = List.of(tournament1, tournament2);

        when(tournamentRepository.findAll()).thenReturn(expected);
        // acts
        List<Tournament> actual = tournamentService.getAllTournaments();
        // asserts
        assertEquals(expected, actual);
    }

    // getTournamentById ----------------------------------------------------------------------------------------------------
    @Test
    void getTournamentById_shouldReturnTournament_whenTournamentExists() {
        // arrange
        Tournament expected = new Tournament(
                "Tournament 1",
                null,
                8,
                LocalDateTime.of(2027, 9, 19, 18, 0),
                LocalDateTime.of(2027, 9, 20, 18, 0),
                48
        );

        when(tournamentRepository.findById(1L))
                .thenReturn(Optional.of(expected));
        // acts
        Tournament actual = tournamentService.getTournamentById(1L);
        // asserts
        assertEquals(expected, actual);
    }

    @Test
    void getTournamentById_shouldThrowNotFound_whenTournamentDoesNotExist() {
        // arrange
        when(tournamentRepository.findById(99L))
                .thenReturn(Optional.empty());
        // acts + asserts
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> tournamentService.getTournamentById(99L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    // deleteTournament ----------------------------------------------------------------------------------------------------

    @Test
    void deleteTournament_shouldDeleteCorrespondingTournament() {
        // arrange
        Long tournamentId = 1L;

        Tournament tournament = new Tournament(
                "Tournament 1",
                null,
                8,
                LocalDateTime.of(2027, 9, 19, 18, 0),
                LocalDateTime.of(2027, 9, 20, 18, 0),
                48
        );

        when(tournamentRepository.findById(tournamentId))
                .thenReturn(Optional.of(tournament));

        // act
        tournamentService.deleteTournament(tournamentId);

        // assert
        verify(tournamentRepository).deleteById(tournamentId);
    }

    @Test
    void deleteTournament_shouldThrowNotFound_whenTournamentDoesNotExist() {
        // arrange
        when(tournamentRepository.findById(1L))
                .thenReturn(Optional.empty());
        // act + asserts
        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.deleteTournament(1L)
        );

        verify(tournamentRepository, never()).deleteById(anyLong());
    }

    // updateTournament ----------------------------------------------------------------------------------------------------

    @Test
    void updateTournament_shouldUpdateAndReturnTournament() {
        Long tournamentId = 1L;

        Tournament existingTournament = new Tournament(
                "Tournament 1",
                null,
                8,
                LocalDateTime.of(2027, 9, 19, 18, 0),
                LocalDateTime.of(2027, 9, 20, 18, 0),
                48
        );

        UpdateTournamentRequestDTO validDTO = createValidUpdateTournamentRequestDTO();

        UpdateTournamentRequestDTO UpdateTournamentRequestDTO = new UpdateTournamentRequestDTO(
                "Updated Tournament",
                16,
                validDTO.registrationDeadline(),
                validDTO.startDate(),
                validDTO.roundDurationHours()
        );

        when(tournamentRepository.findById(tournamentId))
                .thenReturn(Optional.of(existingTournament));

        when(tournamentRepository.save(existingTournament))
                .thenReturn(existingTournament);

        UpdateTournamentRequestDTO result =
                tournamentService.updateTournament(tournamentId, UpdateTournamentRequestDTO);

        assertEquals("Updated Tournament", result.name());
        assertEquals(16, result.maxCapacity());
        assertEquals(validDTO.registrationDeadline(), result.registrationDeadline());
        assertEquals(validDTO.startDate(), result.startDate());
        assertEquals(48, result.roundDurationHours());

        verify(tournamentRepository).save(existingTournament);
    }

    // getAllOpenRegistrationTournaments -------------------------------------------------------------------------------
    
    @Test
    void getAllOpenRegistrationTournaments_shouldReturnOpenTournaments() {
        // Arrange
        Tournament tournament1 = new Tournament();
        Tournament tournament2 = new Tournament();

        List<Tournament> expected = List.of(tournament1, tournament2);

        when(tournamentRepository
                .findByRegistrationDeadlineAfter(any(LocalDateTime.class)))
                .thenReturn(expected);

        // Act
        List<Tournament> actual =
                tournamentService.getAllOpenRegistrationTournaments();

        // Assert
        assertEquals(expected, actual);
    }

}

