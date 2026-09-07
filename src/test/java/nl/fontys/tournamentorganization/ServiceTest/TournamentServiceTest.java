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
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

    @Mock
    private ITournamentRepository tournamentRepository;

    @InjectMocks
    private TournamentService tournamentService;

    @Test
    void saveTournament_shouldSaveTournamentWithCorrectValues() {
        // Arrange
        TournamentDTO tournamentDTO = new TournamentDTO();
        tournamentDTO.name = "Valorant Cup";
        tournamentDTO.maxCapacity = 16;

        // Act
        tournamentService.saveTournament(tournamentDTO);

        // Assert
        ArgumentCaptor<Tournament> captor =
                ArgumentCaptor.forClass(Tournament.class);

        verify(tournamentRepository).save(captor.capture());

        Tournament savedTournament = captor.getValue();

        assertEquals("Valorant Cup", savedTournament.name);
        assertEquals(16, savedTournament.maxCapacity);
    }

}