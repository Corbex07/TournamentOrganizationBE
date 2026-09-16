package nl.fontys.tournamentorganization.ServiceTest;

import nl.fontys.tournamentorganization.DTOs.TournamentDTO;
import nl.fontys.tournamentorganization.interfaces.ITournamentRepository;
import nl.fontys.tournamentorganization.models.Tournament;
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

    @InjectMocks
    private TournamentService tournamentService;

    @Test
    void saveTournament_shouldSaveTournamentWithCorrectValues() {
        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.name = "Valorant Cup";
        tournamentDTO.maxCapacity = 16;

        tournamentService.saveTournament(tournamentDTO);

        ArgumentCaptor<Tournament> captor =
                ArgumentCaptor.forClass(Tournament.class);

        verify(tournamentRepository).save(captor.capture());

        Tournament savedTournament = captor.getValue();

        assertEquals("Valorant Cup", savedTournament.name);
        assertEquals(16, savedTournament.maxCapacity);
    }
    @Test
    void saveTournament_shouldThrowException_whenCapacityIsNegative() {
        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.name = "Valorant Cup";
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
        // Arrange
        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.name = null;
        tournamentDTO.maxCapacity = 16;

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> tournamentService.saveTournament(tournamentDTO)
        );

        verify(tournamentRepository, never())
                .save(any(Tournament.class));
    }
    @Test
    void getAllTournaments_shouldReturnAllTournaments() {
        // arrange
        Tournament tournament1 = new Tournament("Tournament 1", null, 8);
        Tournament tournament2 = new Tournament("Tournament 2", null, 16);

        List<Tournament> expected = List.of(tournament1, tournament2);

        when(tournamentRepository.findAll()).thenReturn(expected);
        // acts
        List<Tournament> actual = tournamentService.getAllTournaments();
        // asserts
        assertEquals(expected, actual);
    }

    @Test
    void getTournamentById_shouldReturnTournament_whenTournamentExists() {
        // arrange
        Tournament expected = new Tournament(
                "Tournament 1",
                null,
                8
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

    @Test
    void deleteTournament_shouldDeleteCorrespondingTournament() {
        // arrange
        Long tournamentId = 1L;

        Tournament tournament = new Tournament(
                "Tournament 1",
                null,
                8
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

        verify(tournamentRepository, never()).delete(any());
    }

    @Test
    void updateTournament_shouldUpdateAndReturnTournament() {
        // arrange
        Long tournamentId = 1L;

        Tournament existingTournament = new Tournament(
                "Old Tournament",
                null,
                8
        );

        TournamentDTO dto = new TournamentDTO();
        dto.name = "Updated Tournament";
        dto.maxCapacity = 16;

        when(tournamentRepository.findById(tournamentId))
                .thenReturn(Optional.of(existingTournament));

        when(tournamentRepository.save(existingTournament))
                .thenReturn(existingTournament);

        // act
        TournamentDTO result =
                tournamentService.updateTournament(tournamentId, dto);

        // assert
        assertEquals("Updated Tournament", result.name);
        assertEquals(16, result.maxCapacity);

        verify(tournamentRepository).save(existingTournament);
    }

}

