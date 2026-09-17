package nl.fontys.tournamentorganization.ServiceTest;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
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
import org.springframework.stereotype.Service;
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

    private TournamentDTO createValidTournamentDTO() {
        TournamentDTO dto = new TournamentDTO();

        dto.name = "Fontys Valorant Cup";
        dto.maxCapacity = 8;
        dto.registrationDeadline =
                LocalDateTime.of(2027, 9, 20, 18, 0);
        dto.startDate =
                LocalDateTime.of(2027, 9, 21, 18, 0);
        dto.roundDurationHours = 48;

        return dto;
    }

// saveTournament ----------------------------------------------------------------------------------------------------

    @Test
    void saveTournament_shouldSaveTournamentWithCorrectValues() {
        TournamentDTO tournamentDTO = createValidTournamentDTO();

        Users organiser = mock(Users.class);

        when(userRepository.findById(5L))
                .thenReturn(Optional.of(organiser));

        tournamentService.saveTournament(tournamentDTO);

        ArgumentCaptor<Tournament> captor =
                ArgumentCaptor.forClass(Tournament.class);

        verify(tournamentRepository).save(captor.capture());

        Tournament savedTournament = captor.getValue();

        assertEquals("Fontys Valorant Cup", savedTournament.getName());
        assertEquals(8, savedTournament.getMaxCapacity());
        assertEquals(
                tournamentDTO.registrationDeadline,
                savedTournament.getRegistrationDeadline()
        );
        assertEquals(
                tournamentDTO.startDate,
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
        TournamentDTO tournamentDTO = createValidTournamentDTO();

        tournamentDTO.maxCapacity = -5;

        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.saveTournament(tournamentDTO)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }
    @Test
    void saveTournament_shouldThrowException_whenNameIsNull() {
        TournamentDTO tournamentDTO = createValidTournamentDTO();

        tournamentDTO.name = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.saveTournament(tournamentDTO)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }

    @Test
    void saveTournament_shouldThrowException_whenStartDateIsNull() {
        TournamentDTO dto = createValidTournamentDTO();

        dto.startDate = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.saveTournament(dto)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }

    @Test
    void saveTournament_shouldThrowException_whenRegistrationDeadlineIsAfterStartDate() {
        TournamentDTO dto = createValidTournamentDTO();

        dto.registrationDeadline =
                LocalDateTime.of(2027, 9, 22, 18, 0);

        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.saveTournament(dto)
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
        );;
        Tournament tournament2 = new Tournament(
                "Tournament 2",
                null,
                8,
                LocalDateTime.of(2027, 9, 19, 18, 0),
                LocalDateTime.of(2027, 9, 20, 18, 0),
                48
        );;

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
        );;

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
        );;

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

        TournamentDTO dto = createValidTournamentDTO();

        dto.name = "Updated Tournament";
        dto.maxCapacity = 16;

        when(tournamentRepository.findById(tournamentId))
                .thenReturn(Optional.of(existingTournament));

        when(tournamentRepository.save(existingTournament))
                .thenReturn(existingTournament);

        TournamentDTO result =
                tournamentService.updateTournament(tournamentId, dto);

        assertEquals("Updated Tournament", result.name);
        assertEquals(16, result.maxCapacity);
        assertEquals(dto.registrationDeadline, result.registrationDeadline);
        assertEquals(dto.startDate, result.startDate);
        assertEquals(48, result.roundDurationHours);

        verify(tournamentRepository).save(existingTournament);
    }

}

